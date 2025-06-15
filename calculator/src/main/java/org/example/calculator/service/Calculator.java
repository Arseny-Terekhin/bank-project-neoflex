package org.example.calculator.service;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class Calculator {

    public BigDecimal calculateMonthlyPayment(BigDecimal principal, BigDecimal rate, int term) {
        BigDecimal monthlyRate = rate.divide(BigDecimal.valueOf(12 * 100), 10, RoundingMode.HALF_UP);
        return principal.multiply(monthlyRate).divide(BigDecimal.ONE.subtract(BigDecimal.ONE.divide(BigDecimal.ONE.add(monthlyRate).pow(term), 10, RoundingMode.HALF_UP)), 2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateRate(boolean insurance, boolean salaryClient, BigDecimal rate ) {
        if (insurance) {
            rate = rate.subtract(BigDecimal.valueOf(3));
        }
        if (salaryClient) {
            rate = rate.subtract(BigDecimal.valueOf(1));
        }
        return rate;
    }
}
