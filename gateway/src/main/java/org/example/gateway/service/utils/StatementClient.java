package org.example.gateway.service.utils;

import lombok.RequiredArgsConstructor;
import org.example.gateway.dto.LoanOfferDto;
import org.example.gateway.dto.LoanStatementRequestDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StatementClient {

    private final RestClient client;

    public List<LoanOfferDto> createStatement(LoanStatementRequestDto loanStatementRequestDto) {
        return client.post()
                .uri("http://localhost:8084/statement")
                .body(loanStatementRequestDto)
                .retrieve()
                .body(new ParameterizedTypeReference<List<LoanOfferDto>>() {});
    }

    public void selectOffer(LoanOfferDto loanOfferDto) {
        client.post()
                .uri("http://localhost:8084/statement/offer")
                .body(loanOfferDto);
    }
}
