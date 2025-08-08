package org.example.gateway.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.example.gateway.dto.enums.Gender;
import org.example.gateway.dto.enums.MaritalStatus;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinishRegistrationRequestDto {

    @NotNull(message = "gender должен быть заполнен")
    private Gender gender;

    @NotNull(message = "maritalStatus должен быть заполнен")
    private MaritalStatus maritalStatus;

    @NotNull(message = "dependentAmount должен быть заполнен")
    @DecimalMin(value = "20000", message = "Сумма кредита должна быть не менее 20 000")
    private BigDecimal dependentAmount;

    @NotNull(message = "passportIssueDate должен быть заполнен")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate passportIssueDate;

    @NotNull(message = "passportIssueBrach должен быть заполнен")
    private String passportIssueBrach;

    @NotNull(message = "employment должен быть заполнен")
    private EmploymentDto employment;

    @NotNull(message = "accountNumber должен быть заполнен")
    private String accountNumber;
}
