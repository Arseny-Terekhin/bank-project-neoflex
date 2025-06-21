package org.example.deal.service;

import org.example.deal.dto.LoanOfferDto;
import org.example.deal.dto.LoanStatementRequestDto;

import java.util.List;

public interface DealService {
    List<LoanOfferDto> createStatement(LoanStatementRequestDto loanStatementRequestDto);
}
