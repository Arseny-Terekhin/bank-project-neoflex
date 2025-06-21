package org.example.deal.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.deal.dto.LoanOfferDto;
import org.example.deal.dto.LoanStatementRequestDto;
import org.example.deal.service.DealService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/deal")
@Validated
@RequiredArgsConstructor
@Slf4j
public class DealController {

    private final DealService dealService;

    @PostMapping("/deal/statement")
    public ResponseEntity<List<LoanOfferDto>> createStatement(@RequestBody @Valid LoanStatementRequestDto request) {
        log.info("Start create statement, request body: {}",  request);

        List<LoanOfferDto> offers =  dealService.createStatement(request);

        log.info("End create statement, response body: {}",  offers);
        return ResponseEntity.ok(offers);
    }
}
