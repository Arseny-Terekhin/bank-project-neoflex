package org.example.statement.tests;

import org.example.statement.dto.LoanOfferDto;
import org.example.statement.dto.LoanStatementRequestDto;
import org.example.statement.service.utils.DealClient;
import org.example.statement.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class DealClientTests {

    @Autowired
    private DealClient dealClient;

    @Test
    void dealClientTest_1_createStatement(){
        LoanStatementRequestDto loanStatementRequestDto = TestUtils.generateLoanStatementRequestDto();

        List<LoanOfferDto> offers = dealClient.createStatement(loanStatementRequestDto);

        Assertions.assertNotNull(offers);
    }

}
