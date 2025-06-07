package org.example.calculator.controller;

import jakarta.validation.Valid;
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
public class CalcController {

    @Autowired
     private CalculatorService calculator;

    @PostMapping("/offers")
    public ResponseEntity offersCalc(@RequestBody @Valid LoanStatementRequestDto  loanStatementRequestDto) {
        List<LoanOfferDto> offers = calculator.getLoanOffers(loanStatementRequestDto);
        return ResponseEntity.ok(offers);
    }

    @PostMapping("/calc")
    public ResponseEntity calc(@RequestBody @Valid ScoringDataDto scoringDataDto) {
        CreditDto creditDto = new CreditDto();
        return ResponseEntity.ok(creditDto);
    }
}
