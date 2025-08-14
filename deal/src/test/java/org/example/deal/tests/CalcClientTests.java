package org.example.deal.tests;

import org.example.moduledto.dto.*;
import org.example.deal.service.utils.CalcClient;
import org.example.deal.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:postgresql://localhost:5438/postgres",
        "spring.datasource.username=postgres",
        "spring.datasource.password=pgpwd4habr"
})
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
