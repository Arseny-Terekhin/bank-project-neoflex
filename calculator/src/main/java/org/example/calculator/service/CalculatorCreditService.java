package org.example.calculator.service;

import org.example.calculator.dto.CreditDto;
import org.example.calculator.dto.LoanOfferDto;
import org.example.calculator.dto.LoanStatementRequestDto;
import org.example.calculator.dto.ScoringDataDto;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class CalculatorCreditService implements CalculatorService {
    @Override
    public List<LoanOfferDto> getLoanOffers(LoanStatementRequestDto loanStatementRequestDto) {
        return List.of();
    }

    @Override
    public CreditDto calculateCredit(ScoringDataDto scoringDataDto) {
        return null;
    }
}
