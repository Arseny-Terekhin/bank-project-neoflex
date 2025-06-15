package org.example.calculator;

import org.example.calculator.dto.EmploymentDto;
import org.example.calculator.dto.LoanStatementRequestDto;
import org.example.calculator.dto.ScoringDataDto;
import org.example.calculator.dto.enums.EmploymentStatus;
import org.example.calculator.dto.enums.Gender;
import org.example.calculator.dto.enums.MaritalStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TestDTO {
    public static ScoringDataDto generateScoringDataDto(){
        ScoringDataDto dto = new ScoringDataDto();
        dto.setAmount(new BigDecimal("500000"));
        dto.setTerm(12);
        dto.setFirstName("Иван");
        dto.setLastName("Иванов");
        dto.setEmail("ivan@example.com");
        dto.setPhone("+79001234567");
        dto.setBirthdate(LocalDate.of(1990, 1, 1));
        dto.setPassportSeries("1234");
        dto.setPassportNumber("567890");
        dto.setGender(Gender.MALE);
        dto.setMaritalStatus(MaritalStatus.MARRIED);
        dto.setDependentAmount(1);
        dto.setIsInsuranceEnabled(true);
        dto.setIsSalaryClient(true);
        EmploymentDto employment = new EmploymentDto();
        employment.setEmploymentStatus(EmploymentStatus.EMPLOYED);
        employment.setEmployerINN("1234567890");
        employment.setPosition("DEVELOPER");
        employment.setSalary(new BigDecimal("150000"));
        employment.setWorkExperienceTotal(60);
        employment.setWorkExperienceCurrent(24);
        dto.setEmployment(employment);
        dto.setAccount("12345678901234567890");
        return dto;
    }

    public static LoanStatementRequestDto generateLoanStatementRequestDto(){
        LoanStatementRequestDto dto = LoanStatementRequestDto.builder()
                .amount(new BigDecimal("300000"))
                .term(6)
                .firstName("Алексей")
                .lastName("Смирнов")
                .phone("+79119876543")
                .email("alex@example.com")
                .build();
        return dto;
    }

    public static LoanStatementRequestDto generateLoanStatementRequestDtoForTest_1(){
        LoanStatementRequestDto dto = LoanStatementRequestDto.builder()
                .amount(new BigDecimal("300000"))
                .term(24)
                .firstName("Алексей")
                .lastName("Смирнов")
                .phone("79009876543")
                .email("alex@example.com")
                .build();
        return dto;
    }
}
