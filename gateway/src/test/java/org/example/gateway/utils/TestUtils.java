package org.example.gateway.utils;

import org.example.gateway.dto.EmploymentDto;
import org.example.gateway.dto.FinishRegistrationRequestDto;
import org.example.gateway.dto.LoanOfferDto;
import org.example.gateway.dto.LoanStatementRequestDto;
import org.example.gateway.dto.enums.EmploymentPosition;
import org.example.gateway.dto.enums.EmploymentStatus;
import org.example.gateway.dto.enums.Gender;
import org.example.gateway.dto.enums.MaritalStatus;

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

    public static FinishRegistrationRequestDto generateFinishRegistrationRequestDto(){
        return FinishRegistrationRequestDto.builder()
                .accountNumber("qwerqweqr")
                .dependentAmount(BigDecimal.valueOf(30000))
                .gender(Gender.MALE)
                .maritalStatus(MaritalStatus.MARRIED)
                .passportIssueDate(LocalDate.of(2000, 1, 1))
                .passportIssueBrach("qqqq")
                .employment(EmploymentDto.builder()
                        .employmentStatus(EmploymentStatus.EMPLOYED)
                        .employerINN("1234567890")
                        .position(EmploymentPosition.MID_MANAGER)
                        .salary(new BigDecimal("150000"))
                        .workExperienceCurrent(24)
                        .workExperienceTotal(60)
                        .build())
                .build();
    }


}
