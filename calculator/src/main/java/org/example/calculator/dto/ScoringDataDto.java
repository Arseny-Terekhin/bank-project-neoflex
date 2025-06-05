package org.example.calculator.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ScoringDataDto {

    @NotNull
    private BigDecimal amount;

    @NotNull
    private Integer term;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    private String email;

    @Pattern(regexp = "\\+7\\d{10}")
    private String phone;

    @Past
    private LocalDate birthdate;

    @NotBlank
    @Size(min = 4, max = 4, message = "Серия паспорта должна состоять из 4 цифр")
    private String passportSeries;

    @NotBlank
    @Size(min = 6, max = 6, message = "Номер паспорта должна состоять из 6 цифр")
    private String passportNumber;

    @NotBlank
    private Gender gender;

    @NotBlank
    private MaritalStatus maritalStatus;

    @NotNull
    private Integer dependentAmount;

    @NotNull
    private EmploymentDto employment;

    private String account;

    private Boolean isInsuranceEnabled;
    private Boolean isSalaryClient;
}
enum Gender{
    MALE,FEMALE
}
enum MaritalStatus {
    SINGLE,
    MARRIED,
    WIDOWED,
}