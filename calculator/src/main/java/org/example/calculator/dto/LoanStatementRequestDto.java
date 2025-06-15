package org.example.calculator.dto;

import jakarta.validation.constraints.*;
import lombok.*;


import java.math.BigDecimal;

@Data
@Builder
public class LoanStatementRequestDto {

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

    @NotBlank(message = "email должен быть заполнен")
    @Email(message = "email введен не коректно")
    private String email;

    @NotBlank(message = "phone должен быть заполнен")
    @Pattern(regexp = "\\+79\\d{9}", message = "Телефон должен быть в формате +79**-***-**-**")
    private String phone;

}
