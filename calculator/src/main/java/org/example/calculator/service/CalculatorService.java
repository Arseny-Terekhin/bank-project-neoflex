package org.example.calculator.service;


import org.example.moduledto.dto.CreditDto;
import org.example.moduledto.dto.LoanOfferDto;
import org.example.moduledto.dto.LoanStatementRequestDto;
import org.example.moduledto.dto.ScoringDataDto;

import java.util.List;

public interface CalculatorService {

    List<LoanOfferDto> getLoanOffers(LoanStatementRequestDto loanStatementRequestDto);

    CreditDto calculateCredit(ScoringDataDto scoringDataDto);

}
