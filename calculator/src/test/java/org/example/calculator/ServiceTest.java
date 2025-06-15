package org.example.calculator;

import org.example.calculator.dto.*;
import org.example.calculator.dto.enums.EmploymentStatus;
import org.example.calculator.dto.enums.Gender;
import org.example.calculator.dto.enums.MaritalStatus;
import org.example.calculator.service.CalculatorCreditService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ServiceTest {

    @Autowired
    private CalculatorCreditService calculatorCreditService;

    @Test
    void createOfferTest_1(){
        LoanStatementRequestDto dto = TestDTO.generateLoanStatementRequestDtoForTest_1();

        LoanOfferDto test = calculatorCreditService.createOffer(dto, true, true);

        assertEquals(test.getRate(), BigDecimal.valueOf(17));
        assertEquals(test.getRequestedAmount(), BigDecimal.valueOf(300000));
        assertEquals(test.getTotalAmount(), BigDecimal.valueOf(400000));
        assertEquals(test.getMonthlyPayment(), BigDecimal.valueOf(19776.91));
        assertEquals(test.getTerm(), Integer.valueOf(24));

    }

    @Test
    void getLoanOffersTest_1(){
        LoanStatementRequestDto dto = TestDTO.generateLoanStatementRequestDto();

        List<LoanOfferDto> test = calculatorCreditService.getLoanOffers(dto);

        assertNotNull(test);
        assertEquals(test.get(0).getRate(), BigDecimal.valueOf(17));
        assertEquals(test.get(1).getRate(), BigDecimal.valueOf(18));
        assertEquals(test.get(2).getRate(), BigDecimal.valueOf(20));
        assertEquals(test.get(3).getRate(), BigDecimal.valueOf(21));

    }

    @Test
    void calculateCreditTest_1(){
        ScoringDataDto test =TestDTO.generateScoringDataDto();

        CreditDto credit = calculatorCreditService.calculateCredit(test);
        System.out.println(credit);

        assertNotNull(credit);
        assertEquals(credit.getAmount(), test.getAmount());
        assertEquals(credit.getTerm(), test.getTerm());
        assertEquals(credit.getRate(), BigDecimal.valueOf(17));
        assertEquals(credit.getPaymentScheduleTotal(), new BigDecimal("656674.20"));
    }

}
