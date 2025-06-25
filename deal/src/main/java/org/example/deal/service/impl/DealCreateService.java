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
import org.example.deal.service.utils.CalcClient;
import org.example.deal.service.utils.CreatorUpdaterData;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DealCreateService implements DealService {

    private final StatementRepository statementRepository;
    private final ClientRepository clientRepository;
    private final CreditRepository creditRepository;
    private final CalcClient calcClient;
    private final CreatorUpdaterData creatorUpdaterData;
    private final HttpServletRequest httpServletRequest;


    @Override
    public List<LoanOfferDto> createStatement(LoanStatementRequestDto loanStatementRequestDto) {
        Client client = creatorUpdaterData.createClientFromDto(loanStatementRequestDto);
        clientRepository.save(client);

        log.info("Create and save Client in date base: {}", client.getId());

        Statement statement = Statement.builder()
                .client(client)
                .creationDate(LocalDateTime.now())
                .status(ApplicationStatus.PREAPPROVAL)
                .sesCode(httpServletRequest.getSession().getId())
                .statusHistory(List.of(new StatementStatusHistoryDto(ApplicationStatus.PREAPPROVAL, LocalDateTime.now(), ChangeType.AUTOMATIC))).build();
        statementRepository.save(statement);

        log.info("Create and save Statement in date base: {}", statement.getId());
        log.info("Create and send POST request /calculator/offers");

        List<LoanOfferDto> offers = calcClient.getOfferFromTheRequest(loanStatementRequestDto);

        log.info("The received List<LoanOfferDto> ");

        offers.forEach(offer -> offer.setStatementId(statement.getId()));
        return offers;
    }

    @Override
    public void selectOffer(LoanOfferDto loanOfferDto) {
        log.info("Find statement in date base");

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

        log.info("Update statement in date base {}", statement.getId());

        statementRepository.save(statement);
    }

    @Override
    public void calculate(FinishRegistrationRequestDto finishDto, Long statementId) {
        log.info("Find statement in date base");

        Statement statement = statementRepository.findById(statementId)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException("Statement not found");
                });

        Client client = creatorUpdaterData.updateClientFromDto(finishDto, statement.getClient());
        clientRepository.save(client);

        log.info("Update and save Client in date base: {}", client.getId());

        ScoringDataDto scoringData = creatorUpdaterData.createScoringDataDto(statement, client);
        CreditDto creditDto = calcClient.getCreditFromTheRequest(scoringData);
        Credit credit = creatorUpdaterData.createCreditFromDto(creditDto);
        creditRepository.save(credit);

        log.info("Create and save Credit in date base: {}", credit.getId());

        statement.setCredit(credit);
        statement.setClient(client);
        statement.setStatus(ApplicationStatus.CC_APPROVED);
        statement.getStatusHistory().add(
                StatementStatusHistoryDto.builder()
                        .status(ApplicationStatus.CC_APPROVED)
                        .time(LocalDateTime.now())
                        .changeType(ChangeType.AUTOMATIC)
                        .build()
        );

        log.info("Update and save Statement in date base: {}", statement.getId());
        statementRepository.save(statement);
    }
}
