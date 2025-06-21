package org.example.deal.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.deal.dto.LoanOfferDto;
import org.example.deal.dto.LoanStatementRequestDto;
import org.example.deal.dto.StatementStatusHistoryDto;
import org.example.deal.dto.enums.ApplicationStatus;
import org.example.deal.dto.enums.ChangeType;
import org.example.deal.entity.Client;
import org.example.deal.entity.Statement;
import org.example.deal.repository.ClientRepository;
import org.example.deal.repository.StatementRepository;
import org.example.deal.service.DealService;
import org.example.deal.service.utils.CalcClient;
import org.example.deal.service.utils.CreatorEntityFromDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DealCreateService implements DealService {

    private final StatementRepository statementRepository;
    private final ClientRepository clientRepository;
    private final CalcClient calcClient;
    private final CreatorEntityFromDTO creatorEntityFromDTO;


    @Override
    public List<LoanOfferDto> createStatement(LoanStatementRequestDto loanStatementRequestDto) {

        Client client = creatorEntityFromDTO.createClientFromDto(loanStatementRequestDto);
        clientRepository.save(client);

        log.info("Create and save Client in date base: {}", client);

        Statement statement = Statement.builder()
                .client(client)
                .creationDate(LocalDateTime.now())
                .status(ApplicationStatus.PREAPPROVAL)
                .statusHistory(List.of(new StatementStatusHistoryDto(ApplicationStatus.PREAPPROVAL, LocalDateTime.now(), ChangeType.AUTOMATIC)))
                .build();
        statementRepository.save(statement);

        log.info("Create and save Statement in date base: {}", statement);
        log.info("Create and send POST request /calculator/offers");

        List<LoanOfferDto> offers = calcClient.getOffersQuery(loanStatementRequestDto);

        log.info("The received List<LoanOfferDto> ");

        offers.forEach(offer -> offer.setStatementId(statement.getId()));
        return offers;
    }
}
