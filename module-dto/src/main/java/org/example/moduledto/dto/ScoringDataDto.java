package org.example.moduledto.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import org.example.moduledto.dto.enums.Gender;
import org.example.moduledto.dto.enums.MaritalStatus;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScoringDataDto {

    @NotNull(message = "amount должен быть заполнен")
    @DecimalMin(value = "20000", message = "Сумма кредита должна быть не менее 20 000")
    private BigDecimal amount;

    @NotNull(message = "term должен быть заполнен")
    @Min(value = 6, message = "Срок кредита должен быть не менее 6 месяцев")
    private Integer term;

    @NotBlank(message = "firstName должен быть заполнен")
    private String firstName;

    @NotBlank(message = "lastName должен быть заполнен")
    private String lastName;

    @NotBlank(message = "middleName должен быть заполнен")
    private String middleName;

    @NotNull(message = "birthdate должен быть заполнен")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthdate;

    @NotBlank(message = "passportSeries должен быть заполнен")
    @Size(min = 4, max = 4, message = "Серия паспорта должна состоять из 4 цифр")
    private String passportSeries;

    @NotBlank(message = "passportNumber должен быть заполнен")
    @Size(min = 6, max = 6, message = "Номер паспорта должна состоять из 6 цифр")
    private String passportNumber;

    @NotNull(message = "passportIssueDate должен быть заполнен")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate passportIssueDate;

    @NotNull(message = "passportIssueBranch должен быть заполнен")
    private String passportIssueBranch;

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

