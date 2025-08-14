package org.example.calculator.tests;

import org.example.moduledto.dto.*;
import org.example.calculator.utils.TestUtils;
import org.example.calculator.service.impl.CalculatorCreditService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ServiceTest {

    @Autowired
    private CalculatorCreditService calculatorCreditService;

    @Test
    void createOfferTest_1_OkStatus(){
        LoanStatementRequestDto dto = TestUtils.generateLoanStatementRequestDtoForTest_1();

        LoanOfferDto test = calculatorCreditService.createOffer(dto, true, true);

        assertEquals(test.getRate(), BigDecimal.valueOf(17));
        assertEquals(test.getRequestedAmount(), BigDecimal.valueOf(300000));
        assertEquals(test.getTotalAmount(), BigDecimal.valueOf(400000));
        assertEquals(test.getMonthlyPayment(), BigDecimal.valueOf(19776.91));
        assertEquals(test.getTerm(), Integer.valueOf(24));

    }

    @Test
    void getLoanOffersTest_1_OkStatus(){
        LoanStatementRequestDto dto = TestUtils.generateLoanStatementRequestDto();

        List<LoanOfferDto> test = calculatorCreditService.getLoanOffers(dto);

        assertNotNull(test);
        assertEquals(test.get(0).getRate(), BigDecimal.valueOf(21));
        assertEquals(test.get(1).getRate(), BigDecimal.valueOf(20));
        assertEquals(test.get(2).getRate(), BigDecimal.valueOf(18));
        assertEquals(test.get(3).getRate(), BigDecimal.valueOf(17));

    }

    @Test
    void calculateCreditTest_1_OkStatus(){
        ScoringDataDto test = TestUtils.generateScoringDataDto();

        CreditDto credit = calculatorCreditService.calculateCredit(test);
        System.out.println(credit);

        assertNotNull(credit);
        assertEquals(credit.getAmount(), test.getAmount());
        assertEquals(credit.getTerm(), test.getTerm());
        assertEquals(credit.getRate(), BigDecimal.valueOf(17));
    }

}
