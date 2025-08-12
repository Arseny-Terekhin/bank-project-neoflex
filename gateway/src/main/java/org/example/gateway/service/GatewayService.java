package org.example.gateway.service;

import org.example.moduledto.dto.FinishRegistrationRequestDto;
import org.example.moduledto.dto.LoanOfferDto;
import org.example.moduledto.dto.LoanStatementRequestDto;

import java.util.List;

public interface GatewayService {

    void calculatedCredit(Long statementId, FinishRegistrationRequestDto finishRegistrationRequestDto);

    void  selectOffer(LoanOfferDto loanOfferDto);

    List<LoanOfferDto> createStatement(LoanStatementRequestDto loanStatementRequestDto);
}
