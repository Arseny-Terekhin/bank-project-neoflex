package org.example.gateway.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.gateway.dto.FinishRegistrationRequestDto;
import org.example.gateway.dto.LoanOfferDto;
import org.example.gateway.dto.LoanStatementRequestDto;
import org.example.gateway.service.GatewayService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
@Validated
@RequiredArgsConstructor
@Slf4j
public class GatewayController {

    private final GatewayService gatewayService;

    @PostMapping("/statement")
    public ResponseEntity<List<LoanOfferDto>> createStatement(@RequestBody @Valid LoanStatementRequestDto loanStatementRequestDto) {
        log.info("Start create statement, MVP Gateway,  request body: {}",  loanStatementRequestDto);

        List<LoanOfferDto> list = gatewayService.createStatement(loanStatementRequestDto);

        log.info("End create statement, MVP Gateway,  response body: {}",  list);
        return ResponseEntity.ok().body(list);
    }

    @PostMapping("/offer/select")
    public  ResponseEntity<Void> selectOffer(@RequestBody @Valid LoanOfferDto loanOfferDto) {
        log.info("Start select offer, MVP Gateway,  request body: {}",  loanOfferDto);

        gatewayService.selectOffer(loanOfferDto);

        log.info("End select offer, MVP Gateway");
        return ResponseEntity.ok().build();
    }

    @PostMapping("/calculate/{statementId}")
    public ResponseEntity<Void> calculateCredit(@PathVariable @Min(value = 1, message = "statementId должен быть больше 0") Long statementId,
                                                @RequestBody @Valid FinishRegistrationRequestDto finishRegistrationRequestDto) {
        log.info("Start calculate credit, MVP Gateway, request statementId: {}", statementId);

        gatewayService.calculatedCredit(statementId, finishRegistrationRequestDto);

        log.info("End calculate credit, MVP Gateway");
        return  ResponseEntity.ok().build();
    }


}
