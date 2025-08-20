package org.example.deal.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.deal.dto.*;
import org.example.deal.service.DealService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/deal")
@Validated
@RequiredArgsConstructor
@Slf4j
public class DealController {

    private final DealService dealService;

    @PostMapping("/statement")
    public ResponseEntity<List<LoanOfferDto>> createStatement(@RequestBody @Valid LoanStatementRequestDto request) {
        log.info("Start create statement, request body: {}",  request);

        List<LoanOfferDto> offers =  dealService.createStatement(request);

        log.info("End create statement, response body: {}",  offers);
        return ResponseEntity.ok(offers);
    }


    @PostMapping("/offer/select")
    public ResponseEntity<Void> selectOffer(@RequestBody @Valid LoanOfferDto loanOfferDto) {
        log.info("Start select offer, request body: {}",  loanOfferDto);

        dealService.selectOffer(loanOfferDto);

        log.info("End select offer, response body: {}",  loanOfferDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/calculate/{statementId}")
    public ResponseEntity<Void> calculate(
            @PathVariable @Min(value = 1, message = "statementId должен быть больше 0") Long statementId,
            @RequestBody @Valid FinishRegistrationRequestDto finishDto) {
        log.info("Start calculate credit, request statementId: {}", statementId );

        dealService.calculate(finishDto, statementId);

        log.info("End calculate credit, response statementId: {}",  statementId);
        return ResponseEntity.ok().build();
    }
}
