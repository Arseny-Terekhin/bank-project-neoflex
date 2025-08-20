package org.example.statement.utils;

import org.example.statement.dto.LoanOfferDto;
import org.example.statement.dto.LoanStatementRequestDto;

import java.math.BigDecimal;
import java.time.LocalDate;


public class TestUtils {

    public static LoanStatementRequestDto generateLoanStatementRequestDto(){
        return LoanStatementRequestDto.builder()
                .amount(new BigDecimal("300000"))
                .term(6)
                .firstName("Алексей")
                .lastName("Смирнов")
                .email("alex@example.com")
                .middleName("1234")
                .passportSeries("1234")
                .passportNumber("567890")
                .birthdate(LocalDate.of(2000, 1, 1))
                .build();
    }

    public static LoanOfferDto generateLoanOfferDto(){
        return LoanOfferDto.builder()
                .rate(BigDecimal.valueOf(17))
                .isInsuranceEnabled(true)
                .isSalaryClient(true)
                .statementId(1)
                .monthlyPayment(BigDecimal.valueOf(15000))
                .requestedAmount(BigDecimal.valueOf(30000))
                .totalAmount(BigDecimal.valueOf(130000))
                .term(6)
                .build();
    }

}
