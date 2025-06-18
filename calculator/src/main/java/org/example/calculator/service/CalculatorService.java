package org.example.calculator.service;

import org.example.calculator.dto.CreditDto;
import org.example.calculator.dto.LoanOfferDto;
import org.example.calculator.dto.LoanStatementRequestDto;
import org.example.calculator.dto.ScoringDataDto;

import java.util.List;

public interface CalculatorService {

    List<LoanOfferDto> getLoanOffers(LoanStatementRequestDto  loanStatementRequestDto);

    CreditDto calculateCredit(ScoringDataDto  scoringDataDto);

}
