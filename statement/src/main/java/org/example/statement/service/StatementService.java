package org.example.statement.service;

import org.example.statement.dto.LoanOfferDto;
import org.example.statement.dto.LoanStatementRequestDto;

import java.util.List;

public interface StatementService {

    List<LoanOfferDto> createStatement(LoanStatementRequestDto request);

    void selectOffer(LoanOfferDto offer);
}
