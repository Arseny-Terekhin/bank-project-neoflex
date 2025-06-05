package org.example.calculator.dto;


import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CreditDto {

    private BigDecimal amount;
    private Integer term;
    private BigDecimal monthlyPayment;
    private BigDecimal rate;

    private Boolean isInsuranceEnabled;
    private Boolean isSalaryClient;

    private BigDecimal psk; // полная стоимость кредита
    private BigDecimal paymentScheduleTotal;

    private List<PaymentScheduleElementDto> paymentSchedule;



}
