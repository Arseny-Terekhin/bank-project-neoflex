package org.example.deal.service.utils;

import lombok.RequiredArgsConstructor;
import org.example.deal.dto.LoanOfferDto;
import org.example.deal.dto.LoanStatementRequestDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CalcClient {

    private final RestClient client;

    public List<LoanOfferDto> getOffersQuery(LoanStatementRequestDto loanStatementRequestDto) {
        List<LoanOfferDto> loanOfferDtos = client.post()
                .uri("/calculator/offers")
                .body(loanStatementRequestDto)
                .retrieve()
                .body(new ParameterizedTypeReference<List<LoanOfferDto>>() {});
        return loanOfferDtos;
    }
}
