package org.example.calculator.service.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class Calculator {

    @Value("${change.insurance.rate}")
    private BigDecimal insuranceRate;

    @Value("${change.salaryClient.rate}")
    private BigDecimal salaryClientRate;

    public BigDecimal calculateMonthlyPayment(BigDecimal principal, BigDecimal rate, int term) {
        BigDecimal monthlyRate = rate.divide(BigDecimal.valueOf(12 * 100), 10, RoundingMode.HALF_UP);
        return principal.multiply(monthlyRate).divide(BigDecimal.ONE.subtract(BigDecimal.ONE.divide(BigDecimal.ONE.add(monthlyRate).pow(term), 10, RoundingMode.HALF_UP)), 2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateRate(boolean insurance, boolean salaryClient, BigDecimal rate ) {
        if (insurance) {
            rate = rate.subtract(insuranceRate);
        }
        if (salaryClient) {
            rate = rate.subtract(salaryClientRate);
        }
        return rate;
    }
}
