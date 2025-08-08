package org.example.gateway.service;

import org.example.gateway.dto.FinishRegistrationRequestDto;
import org.example.gateway.dto.LoanOfferDto;
import org.example.gateway.dto.LoanStatementRequestDto;

import java.util.List;

public interface GatewayService {

    void calculatedCredit(Long statementId, FinishRegistrationRequestDto finishRegistrationRequestDto);

    void  selectOffer(LoanOfferDto loanOfferDto);

    List<LoanOfferDto> createStatement(LoanStatementRequestDto loanStatementRequestDto);
}
