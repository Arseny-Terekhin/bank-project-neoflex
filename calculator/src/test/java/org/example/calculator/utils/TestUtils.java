package org.example.calculator.utils;

import org.example.calculator.dto.EmploymentDto;
import org.example.calculator.dto.LoanStatementRequestDto;
import org.example.calculator.dto.ScoringDataDto;
import org.example.calculator.dto.enums.EmploymentPosition;
import org.example.calculator.dto.enums.EmploymentStatus;
import org.example.calculator.dto.enums.Gender;
import org.example.calculator.dto.enums.MaritalStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TestUtils {
    public static ScoringDataDto generateScoringDataDto(){
        ScoringDataDto dto = ScoringDataDto.builder()
                .amount(new BigDecimal("500000"))
                .term(12)
                .firstName("Иван")
                .lastName("Иванов")
                .birthdate(LocalDate.of(1990, 1, 1))
                .passportSeries("1234")
                .passportNumber("567890")
                .gender(Gender.MALE)
                .maritalStatus(MaritalStatus.MARRIED)
                .dependentAmount(1)
                .isInsuranceEnabled(true)
                .isSalaryClient(true)
                .employment(EmploymentDto.builder()
                        .employmentStatus(EmploymentStatus.EMPLOYED)
                        .employerINN("1234567890")
                        .position(EmploymentPosition.MID_MANAGER)
                        .salary(new BigDecimal("150000"))
                        .workExperienceCurrent(24)
                        .workExperienceTotal(60)
                        .build())
                .account("12345678901234567890")
                .middleName("123")
                .passportIssueBranch("1234")
                .passportIssueDate(LocalDate.of(2000, 1, 1))
                .build();
        return dto;
    }

    public static LoanStatementRequestDto generateLoanStatementRequestDto(){
        LoanStatementRequestDto dto = LoanStatementRequestDto.builder()
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
        return dto;
    }

    public static LoanStatementRequestDto generateLoanStatementRequestDtoForTest_1(){
        LoanStatementRequestDto dto = LoanStatementRequestDto.builder()
                .amount(new BigDecimal("300000"))
                .term(24)
                .firstName("Алексей")
                .lastName("Смирнов")
                .email("alex@example.com")
                .middleName("1234")
                .passportSeries("1234")
                .passportNumber("567890")
                .birthdate(LocalDate.of(2000, 1, 1))
                .build();
        return dto;
    }
}
