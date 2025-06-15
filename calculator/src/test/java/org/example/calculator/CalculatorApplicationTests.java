package org.example.calculator;

import org.example.calculator.service.Calculator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CalculatorApplicationTests {

    @Autowired
    Calculator calculator;

    @Test
    void calculateMonthlyPaymentTest_1() {
        BigDecimal test = calculator.calculateMonthlyPayment(BigDecimal.valueOf(1000000), BigDecimal.valueOf(15), 6);

        BigDecimal expected = BigDecimal.valueOf(174033.81);

        assertEquals(expected,test);
    }

    @Test
    void calculateMonthlyPaymentTest_2() {
        BigDecimal test = calculator.calculateMonthlyPayment(BigDecimal.valueOf(1500000), BigDecimal.valueOf(20), 12);

        BigDecimal expected = BigDecimal.valueOf(138951.76);

        assertEquals(expected,test);
    }

    @Test
    void calculateRateTest_1() {
        BigDecimal test = calculator.calculateRate(true,true,BigDecimal.valueOf(20));

        BigDecimal expected = BigDecimal.valueOf(16);

        assertEquals(expected,test);
    }

    @Test
    void calculateRateTest_2() {
        BigDecimal test = calculator.calculateRate(false,true,BigDecimal.valueOf(20));

        BigDecimal expected = BigDecimal.valueOf(19);

        assertEquals(expected,test);
    }




}
