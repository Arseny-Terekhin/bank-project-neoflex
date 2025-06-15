package org.example.calculator.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.calculator.dto.CreditDto;
import org.example.calculator.dto.LoanOfferDto;
import org.example.calculator.dto.LoanStatementRequestDto;
import org.example.calculator.dto.ScoringDataDto;
import org.example.calculator.service.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/calculator")
@Validated
@RequiredArgsConstructor
@Slf4j
public class CalcController {

    private final CalculatorService calculator;


    @PostMapping("/offers")
    public ResponseEntity offersCalc(@RequestBody @Valid LoanStatementRequestDto loanStatementRequestDto) {
        log.info("Start generate offers, request body: {}", loanStatementRequestDto);
        List<LoanOfferDto> offers = calculator.getLoanOffers(loanStatementRequestDto);
        log.info("End generate offers, response body: {}", offers);
        return ResponseEntity.ok(offers);
    }

    @PostMapping("/calc")
    public ResponseEntity calc(@RequestBody @Valid ScoringDataDto scoringDataDto) {
        log.info("Start calculation, request body: {}", scoringDataDto);
        CreditDto creditDto = calculator.calculateCredit(scoringDataDto);
        log.info("End calculation, response body: {}", creditDto);
        return ResponseEntity.ok(creditDto);
    }
}
