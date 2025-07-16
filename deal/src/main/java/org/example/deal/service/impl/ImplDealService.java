package org.example.deal.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.deal.dto.*;
import org.example.deal.dto.enums.ApplicationStatus;
import org.example.deal.dto.enums.ChangeType;
import org.example.deal.entity.Client;
import org.example.deal.entity.Credit;
import org.example.deal.entity.Statement;
import org.example.deal.repository.ClientRepository;
import org.example.deal.repository.CreditRepository;
import org.example.deal.repository.StatementRepository;
import org.example.deal.service.DealService;
import org.example.deal.service.KafkaProducerService;
import org.example.deal.service.utils.CalcClient;
import org.example.deal.service.utils.MapperData;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImplDealService implements DealService {

    private final StatementRepository statementRepository;
    private final ClientRepository clientRepository;
    private final CreditRepository creditRepository;
    private final CalcClient calcClient;
    private final HttpServletRequest httpServletRequest;
    private final MapperData mapperData;
    private final KafkaProducerService kafkaProducerService;


    @Override
    public List<LoanOfferDto> createStatement(LoanStatementRequestDto loanStatementRequestDto) {
        Client client = mapperData.toClient(loanStatementRequestDto);
        clientRepository.save(client);
        log.info("Client saved: id={}, email={}", client.getId(), client.getEmail());

        Statement statement = Statement.builder()
                .client(client)
                .creationDate(LocalDateTime.now())
                .status(ApplicationStatus.PREAPPROVAL)
                .statusHistory(List.of(new StatementStatusHistoryDto(ApplicationStatus.PREAPPROVAL, LocalDateTime.now(), ChangeType.AUTOMATIC))).build();
        statementRepository.save(statement);
        log.info("Statement saved: id={}, clientId={}", statement.getId(), client.getId());

        List<LoanOfferDto> offers = calcClient.getOfferFromTheRequest(loanStatementRequestDto);
        log.info("Received {} LoanOfferDto from calculator", offers.size());
        offers.forEach(offer -> offer.setStatementId(statement.getId()));
        return offers;
    }

    @Override
    public void selectOffer(LoanOfferDto loanOfferDto) {
        Statement statement = statementRepository.findById(loanOfferDto.getStatementId())
                .orElseThrow(() -> {
                    throw new EntityNotFoundException("Statement not found");
                });

        statement.setStatus(ApplicationStatus.APPROVED);
        StatementStatusHistoryDto history = StatementStatusHistoryDto.builder()
                .status(ApplicationStatus.APPROVED)
                .time(LocalDateTime.now())
                .changeType(ChangeType.AUTOMATIC)
                .build();

        List<StatementStatusHistoryDto> historyList = statement.getStatusHistory();
        historyList.add(history);
        statement.setStatusHistory(historyList);
        statement.setLoanOffer(loanOfferDto);
        statement.setSignData(LocalDateTime.now());
        log.info("Statement updated to APPROVED: id={}, offerAmount={}", statement.getId(), loanOfferDto.getRequestedAmount());

        statementRepository.save(statement);

        kafkaProducerService.finishRegistration(statement.getId());
    }

    @Override
    public void calculate(FinishRegistrationRequestDto finishDto, Long statementId) {
        Statement statement = statementRepository.findById(statementId)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException("Statement not found");
                });

        Client client = mapperData.updateClient(finishDto, statement.getClient());
        clientRepository.save(client);
        log.info("Client updated and saved: id={}, email={}", client.getId(), client.getEmail());

        ScoringDataDto scoringData = mapperData.toScoringDataDto(statement, client);
        CreditDto creditDto = calcClient.getCreditFromTheRequest(scoringData);
        log.debug("Received CreditDto from calculator: {}", creditDto);

        Credit credit = mapperData.toCredit(creditDto);
        creditRepository.save(credit);
        log.info("Credit saved: id={}, amount={}", credit.getId(), credit.getAmount());

        statement.setCredit(credit);
        statement.setClient(client);
        statement.setSignData(LocalDateTime.now());
        statement.setStatus(ApplicationStatus.CC_APPROVED);
        statement.getStatusHistory().add(
                StatementStatusHistoryDto.builder()
                        .status(ApplicationStatus.CC_APPROVED)
                        .time(LocalDateTime.now())
                        .changeType(ChangeType.AUTOMATIC)
                        .build());
        statementRepository.save(statement);
        log.info("Statement updated to CC_APPROVED and saved: id={}", statement.getId());
    }
}
