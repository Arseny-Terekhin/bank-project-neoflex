package org.example.deal.service.utils;

import lombok.RequiredArgsConstructor;
import org.example.moduledto.dto.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CalcClient {

    private final RestClient client;

    public List<LoanOfferDto> getOfferFromTheRequest(LoanStatementRequestDto loanStatementRequestDto) {
        return client.post()
                .uri("/calculator/offers")
                .body(loanStatementRequestDto)
                .retrieve()
                .body(new ParameterizedTypeReference<List<LoanOfferDto>>() {});
    }

    public CreditDto getCreditFromTheRequest(ScoringDataDto scoringDataDto) {
        return client.post()
                .uri("/calculator/calc")
                .body(scoringDataDto)
                .retrieve()
                .body(CreditDto.class);
    }
}
