package org.example.calculator.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class LoanStatementRequestDto {

    @NotNull
    @DecimalMin(value = "20000", message = "Сумма кредита должна быть не менее 20 000")
    private BigDecimal amount;

    @NotNull
    @Min(value = 6, message = "Срок кредита должен быть не менее 6 месяцев")
    private Integer term;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    private String email;

    @Pattern(regexp = "\\+79\\d{9}", message = "Телефон должен быть в формате +79XXXXXXXXX")
    private String phone;

}
