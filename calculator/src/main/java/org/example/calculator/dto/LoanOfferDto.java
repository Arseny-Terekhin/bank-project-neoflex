package org.example.calculator.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class LoanOfferDto {

    private UUID applicationId;

    private BigDecimal requestedAmount;
    private BigDecimal totalAmount;
    private Integer term;

    private BigDecimal monthlyPayment;
    private BigDecimal rate;

    private boolean isInsuranceEnabled;
    private boolean isSalaryClient;
}
