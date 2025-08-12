package org.example.statement.service;


import org.example.moduledto.dto.LoanOfferDto;
import org.example.moduledto.dto.LoanStatementRequestDto;

import java.util.List;

public interface StatementService {

    List<LoanOfferDto> createStatement(LoanStatementRequestDto request);

    void selectOffer(LoanOfferDto offer);
}
