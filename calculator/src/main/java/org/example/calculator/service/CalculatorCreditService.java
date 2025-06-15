package org.example.calculator.service;

import lombok.RequiredArgsConstructor;
import org.example.calculator.dto.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalculatorCreditService implements CalculatorService {
    
    private final Calculator calc;

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

        return offers.stream().sorted(Comparator.comparing(LoanOfferDto::getRate)) // от худшего к лучшему
                .collect(Collectors.toList());
    }


    public LoanOfferDto createOffer(LoanStatementRequestDto request, boolean insurance, boolean salaryClient) {
        BigDecimal rate = baseRate;

        BigDecimal insuranceCost = BigDecimal.ZERO;
        rate = calc.calculateRate(insurance, salaryClient, rate);
        if (insurance) {insuranceCost = BigDecimal.valueOf(100000);}

        BigDecimal totalAmount = request.getAmount().add(insuranceCost);
        BigDecimal monthlyPayment = calc.calculateMonthlyPayment(totalAmount, rate, request.getTerm());

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

    @Override
    public CreditDto calculateCredit(ScoringDataDto scoringDataDto) {
        BigDecimal rate = baseRate;
        BigDecimal totalAmount = BigDecimal.ZERO;

        rate = calc.calculateRate(scoringDataDto.getIsInsuranceEnabled(), scoringDataDto.getIsSalaryClient(), rate );
        if (scoringDataDto.getIsInsuranceEnabled()) {totalAmount = BigDecimal.valueOf(100000);}

        totalAmount = totalAmount.add(scoringDataDto.getAmount());
        BigDecimal monthlyPayment = calc.calculateMonthlyPayment(totalAmount, rate, scoringDataDto.getTerm());

        List<PaymentScheduleElementDto> schedule = generatePaymentSchedule(totalAmount, rate, scoringDataDto.getTerm());

        BigDecimal psk = monthlyPayment.multiply(BigDecimal.valueOf(scoringDataDto.getTerm())).divide(scoringDataDto.getAmount(), 2, RoundingMode.HALF_UP);
        BigDecimal totalSchedule = schedule.stream().map(PaymentScheduleElementDto::getTotalPayment).reduce(BigDecimal.ZERO, BigDecimal::add);

        return CreditDto.builder()
                .amount(scoringDataDto.getAmount())
                .term(scoringDataDto.getTerm())
                .monthlyPayment(monthlyPayment)
                .rate(rate)
                .isInsuranceEnabled(scoringDataDto.getIsInsuranceEnabled())
                .isSalaryClient(scoringDataDto.getIsSalaryClient())
                .psk(psk).paymentSchedule(schedule)
                .paymentScheduleTotal(totalSchedule)
                .build();
    }

    private List<PaymentScheduleElementDto> generatePaymentSchedule(BigDecimal amount, BigDecimal rate, int term) {
        List<PaymentScheduleElementDto> schedule = new ArrayList<>();
        BigDecimal monthlyRate = rate.divide(BigDecimal.valueOf(12 * 100), 10, RoundingMode.HALF_UP);
        BigDecimal monthlyPayment = calc.calculateMonthlyPayment(amount, rate, term);
        BigDecimal remainingDebt = amount;

        for (int month = 1; month <= term; month++) {
            BigDecimal interest = remainingDebt.multiply(monthlyRate).setScale(2, RoundingMode.HALF_UP);
            BigDecimal principal = monthlyPayment.subtract(interest).setScale(2, RoundingMode.HALF_UP);
            remainingDebt = remainingDebt.subtract(principal).setScale(2, RoundingMode.HALF_UP);

            schedule.add(PaymentScheduleElementDto.builder().number(month).date(LocalDate.now().plusMonths(month)).totalPayment(monthlyPayment).interestPayment(interest).debtPayment(principal).remainingDebt(remainingDebt.max(BigDecimal.ZERO)).build());
        }
        return schedule;
    }
}
