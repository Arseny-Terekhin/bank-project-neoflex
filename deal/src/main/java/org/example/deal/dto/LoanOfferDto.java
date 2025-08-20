package org.example.deal.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanOfferDto {

    @NotNull(message = "statementId должен быть заполнен")
    @Min(value = 1, message = "statementId должен быть больше 0")
    private long statementId;

    @NotNull(message = "requestedAmount должен быть заполнен")
    @DecimalMin(value = "20000", message = "Сумма кредита должна быть не менее 20 000")
    private BigDecimal requestedAmount;

    @NotNull(message = "totalAmount должен быть заполнен")
    @DecimalMin(value = "20000", message = "Итоговая сумма кредита должна быть не менее 20 000")
    private BigDecimal totalAmount;

    @NotNull(message = "term должен быть заполнен")
    @Min(value = 6, message = "Срок кредита должен быть не менее 6 месяцев")
    private Integer term;

    @NotNull(message = "monthlyPayment должен быть заполнен")
    @Min(value = 1, message = "monthlyPayment должен быть не менее 1")
    private BigDecimal monthlyPayment;

    @NotNull(message = "rate должен быть заполнен")
    @Min(value = 1, message = "rate должен быть больше 0")
    private BigDecimal rate;

    @NotNull(message = "isInsuranceEnabled должен быть заполнен")
    private boolean isInsuranceEnabled;

    @NotNull(message = "isSalaryClient должен быть заполнен")
    private boolean isSalaryClient;
}
