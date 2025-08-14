package org.example.statement.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.moduledto.dto.LoanOfferDto;
import org.example.moduledto.dto.LoanStatementRequestDto;
import org.example.statement.service.StatementService;
import org.example.statement.service.utils.DealClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImplStatementService implements StatementService {

    private final DealClient dealClient;

    @Override
    public List<LoanOfferDto> createStatement(LoanStatementRequestDto request) {
        return dealClient.createStatement(request);
    }

    @Override
    public void selectOffer(LoanOfferDto offer) {
        dealClient.selectOffer(offer);
    }
}
