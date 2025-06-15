package org.example.calculator.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import org.example.calculator.dto.enums.Gender;
import org.example.calculator.dto.enums.MaritalStatus;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ScoringDataDto {

    @NotNull(message = "amount должен быть заполнен")
    private BigDecimal amount;

    @NotNull(message = "term должен быть заполнен")
    private Integer term;

    @NotBlank(message = "firstName должен быть заполнен")
    private String firstName;

    @NotBlank(message = "lastName должен быть заполнен")
    private String lastName;

    @NotBlank(message = "email должен быть заполнен")
    @Email(message = "email введен не коректно")
    private String email;

    @NotBlank(message = "phone должен быть заполнен")
    @Pattern(regexp = "\\+79\\d{9}", message = "Телефон должен быть в формате +79**-***-**-**")
    private String phone;

    @NotNull(message = "birthdate должен быть заполнен")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthdate;

    @NotBlank(message = "passportSeries должен быть заполнен")
    @Size(min = 4, max = 4, message = "Серия паспорта должна состоять из 4 цифр")
    private String passportSeries;

    @NotBlank(message = "passportNumber должен быть заполнен")
    @Size(min = 6, max = 6, message = "Номер паспорта должна состоять из 6 цифр")
    private String passportNumber;

    @NotNull(message = "gender должен быть заполнен")
    private Gender gender;

    @NotNull(message = "maritalStatus должен быть заполнен")
    private MaritalStatus maritalStatus;

    @NotNull(message = "dependentAmount должен быть заполнен")
    private Integer dependentAmount;

    @NotNull(message = "employment должен быть заполнен")
    private EmploymentDto employment;

    @NotNull(message = "account должен быть заполнен")
    private String account;

    @NotNull(message = "isInsuranceEnabled должен быть заполнен")
    private Boolean isInsuranceEnabled;

    @NotNull(message = "isSalaryClient должен быть заполнен")
    private Boolean isSalaryClient;
}

