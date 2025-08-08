package org.example.gateway.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.gateway.dto.FinishRegistrationRequestDto;
import org.example.gateway.dto.LoanOfferDto;
import org.example.gateway.dto.LoanStatementRequestDto;
import org.example.gateway.service.GatewayService;
import org.example.gateway.service.utils.DealClient;
import org.example.gateway.service.utils.StatementClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImplGatewayService  implements GatewayService {

    private final DealClient dealClient;
    private final StatementClient statementClient;

    @Override
    public void calculatedCredit(Long statementId, FinishRegistrationRequestDto finishRegistrationRequestDto) {
        dealClient.calculatedCredit(statementId, finishRegistrationRequestDto);

    }

    @Override
    public void selectOffer(LoanOfferDto loanOfferDto) {
        statementClient.selectOffer(loanOfferDto);
    }

    @Override
    public List<LoanOfferDto> createStatement(LoanStatementRequestDto loanStatementRequestDto) {
        return statementClient.createStatement(loanStatementRequestDto);
    }
}
