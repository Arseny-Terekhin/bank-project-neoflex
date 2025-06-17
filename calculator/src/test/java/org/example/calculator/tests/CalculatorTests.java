package org.example.calculator.tests;

import org.example.calculator.service.Calculator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CalculatorTests {

    @Autowired
    Calculator calculator;

    @Test
    void calculateMonthlyPaymentTest_1_OkStatus() {
        BigDecimal test = calculator.calculateMonthlyPayment(BigDecimal.valueOf(1000000), BigDecimal.valueOf(15), 6);

        BigDecimal expected = BigDecimal.valueOf(174033.81);

        assertEquals(expected,test);
    }

    @Test
    void calculateMonthlyPaymentTest_2_OkStatus() {
        BigDecimal test = calculator.calculateMonthlyPayment(BigDecimal.valueOf(1500000), BigDecimal.valueOf(20), 12);

        BigDecimal expected = BigDecimal.valueOf(138951.76);

        assertEquals(expected,test);
    }

    @Test
    void calculateRateTest_1_OkStatus_IsInsuranceAbdIsSalary() {
        BigDecimal test = calculator.calculateRate(true,true,BigDecimal.valueOf(20));

        BigDecimal expected = BigDecimal.valueOf(16);

        assertEquals(expected,test);
    }

    @Test
    void calculateRateTest_2_OkStatus_IsSalary() {
        BigDecimal test = calculator.calculateRate(false,true,BigDecimal.valueOf(20));

        BigDecimal expected = BigDecimal.valueOf(19);

        assertEquals(expected,test);
    }

    @Test
    void calculateRateTest_3_OkStatus_IsInsurance() {
        BigDecimal test = calculator.calculateRate(true,false,BigDecimal.valueOf(20));

        BigDecimal expected = BigDecimal.valueOf(17);

        assertEquals(expected,test);
    }

    @Test
    void calculateRateTest_4_OkStatus() {
        BigDecimal test = calculator.calculateRate(false,false,BigDecimal.valueOf(20));

        BigDecimal expected = BigDecimal.valueOf(20);

        assertEquals(expected,test);
    }




}
