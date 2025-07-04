package org.example.statement.service.utils;

import lombok.RequiredArgsConstructor;
import org.example.statement.dto.LoanOfferDto;
import org.example.statement.dto.LoanStatementRequestDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DealClient {

    private final RestClient client;

    public List<LoanOfferDto> createStatement(LoanStatementRequestDto loanStatementRequestDto) {
        return client.post()
                .uri("/deal/statement")
                .body(loanStatementRequestDto)
                .retrieve()
                .body(new ParameterizedTypeReference<List<LoanOfferDto>>() {});
    }

    public void selectOffer(LoanOfferDto  loanOfferDto) {
         client.post()
                 .uri("/deal/offer/select")
                .body(loanOfferDto)
                .retrieve()
                .body(void.class);
    }
}
