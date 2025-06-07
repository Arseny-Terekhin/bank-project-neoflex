package org.example.calculator.service;

import org.example.calculator.dto.CreditDto;
import org.example.calculator.dto.LoanOfferDto;
import org.example.calculator.dto.LoanStatementRequestDto;
import org.example.calculator.dto.ScoringDataDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Primary
public class CalculatorCreditService implements CalculatorService {

    @Value("${loan.base-rate}")
    private BigDecimal baseRate;


    @Override
    public List<LoanOfferDto> getLoanOffers(LoanStatementRequestDto request) {
        List<LoanOfferDto> offers = new ArrayList<>();

        boolean[] options = {false, true};
        for (boolean insurance : options) {
            for (boolean salaryClient : options) {
                offers.add(createOffer(request, insurance, salaryClient));
            }
        }

        return offers.stream()
                .sorted(Comparator.comparing(LoanOfferDto::getRate)) // от худшего к лучшему
                .collect(Collectors.toList());
    }


    private LoanOfferDto createOffer(LoanStatementRequestDto request, boolean insurance, boolean salaryClient) {
        BigDecimal rate = baseRate;

        BigDecimal insuranceCost = BigDecimal.ZERO;
        if (insurance) {
            insuranceCost = BigDecimal.valueOf(100000);
            rate = rate.subtract(BigDecimal.valueOf(3));
        }
        if (salaryClient) {
            rate = rate.subtract(BigDecimal.valueOf(1));
        }

        BigDecimal totalAmount = request.getAmount().add(insuranceCost);
        BigDecimal monthlyPayment = calculateMonthlyPayment(totalAmount, rate, request.getTerm());

        LoanOfferDto offer = LoanOfferDto.builder()
                .applicationId(UUID.randomUUID())
                .requestedAmount(request.getAmount())
                .totalAmount(totalAmount)
                .term(request.getTerm())
                .rate(rate)
                .monthlyPayment(monthlyPayment)
                .isInsuranceEnabled(insurance)
                .isSalaryClient(salaryClient)
                .build();

        return offer;
    }


    private BigDecimal calculateMonthlyPayment(BigDecimal principal, BigDecimal rate, int term) {
        BigDecimal monthlyRate = rate.divide(BigDecimal.valueOf(12 * 100), 10, RoundingMode.HALF_UP);
        return principal.multiply(monthlyRate)
                .divide(BigDecimal.ONE.subtract(BigDecimal.ONE.divide(BigDecimal.ONE.add(monthlyRate).pow(term), 10, RoundingMode.HALF_UP)),
                        2, RoundingMode.HALF_UP);
    }


    @Override
    public CreditDto calculateCredit(ScoringDataDto scoringDataDto) {
        return null;
    }
}
