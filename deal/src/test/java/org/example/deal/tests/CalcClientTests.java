package org.example.deal.tests;

import org.example.deal.dto.CreditDto;
import org.example.deal.dto.LoanOfferDto;
import org.example.deal.dto.LoanStatementRequestDto;
import org.example.deal.dto.ScoringDataDto;
import org.example.deal.service.utils.CalcClient;
import org.example.deal.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CalcClientTests {

    @Autowired
    private CalcClient calcClient;

    @Test
    void calcClientTest_1_getOffers(){
        LoanStatementRequestDto loanStatementRequestDto = TestUtils.generateLoanStatementRequestDto();

        List<LoanOfferDto> loanOfferDto = calcClient.getOfferFromTheRequest(loanStatementRequestDto);

        assertEquals(loanOfferDto.get(0).getStatementId(), 0);
    }

    @Test
    void calcClientTest_2_getCredit(){
        ScoringDataDto scoringDataDto = TestUtils.generateScoringDataDto();

        CreditDto credit = calcClient.getCreditFromTheRequest(scoringDataDto);

        assertEquals(credit.getTerm(), 12);
    }
}
