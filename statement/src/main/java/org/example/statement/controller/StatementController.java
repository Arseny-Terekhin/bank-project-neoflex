package org.example.statement.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.statement.dto.LoanOfferDto;
import org.example.statement.dto.LoanStatementRequestDto;
import org.example.statement.service.StatementService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/statement")
public class StatementController {

    private final StatementService statementService;

    @PostMapping
    public ResponseEntity<List<LoanOfferDto>> createStatement(@RequestBody @Valid LoanStatementRequestDto request) {
        log.info("Start create statement, request body: {}",  request);

        List<LoanOfferDto> offers =  statementService.createStatement(request);

        log.info("End create statement, response body: {}",  offers);
        return ResponseEntity.ok(offers);
    }

    @PostMapping("/offer")
    public ResponseEntity<Void> selectOffer(@RequestBody @Valid LoanOfferDto loanOfferDto) {
        log.info("Start select offer, request body: {}",  loanOfferDto);

        statementService.selectOffer(loanOfferDto);

        log.info("End select offer, response body: {}",  loanOfferDto);
        return ResponseEntity.ok().build();
    }
}
