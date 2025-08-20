package org.example.gateway.service.utils;

import lombok.RequiredArgsConstructor;
import org.example.gateway.dto.FinishRegistrationRequestDto;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class DealClient {

    private final RestClient client;

    public void calculatedCredit(Long statementId, FinishRegistrationRequestDto finishRegistrationRequestDto) {
        client.post()
                .uri("http://localhost:8081/deal/calculate/" + statementId)
                .body(finishRegistrationRequestDto);
    }
}
