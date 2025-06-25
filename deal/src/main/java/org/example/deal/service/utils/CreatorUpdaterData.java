package org.example.deal.service.utils;

import org.example.deal.dto.CreditDto;
import org.example.deal.dto.FinishRegistrationRequestDto;
import org.example.deal.dto.LoanStatementRequestDto;
import org.example.deal.dto.ScoringDataDto;
import org.example.deal.dto.enums.CreditStatus;
import org.example.deal.entity.Client;
import org.example.deal.entity.Credit;
import org.example.deal.entity.Statement;
import org.example.deal.entity.pojo.Passport;
import org.springframework.stereotype.Component;

@Component
public class CreatorUpdaterData {

    public Client createClientFromDto(LoanStatementRequestDto dto) {
        return Client.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .middleName(dto.getMiddleName())
                .email(dto.getEmail())
                .birthDate(dto.getBirthdate())
                .passport(new Passport(dto.getPassportSeries(), dto.getPassportNumber(), null, null)) // Остальные поля — null
                .build();
    }

    public Client updateClientFromDto(FinishRegistrationRequestDto finishDto, Client client) {
        client.setGender(finishDto.getGender());
        client.setMaritalStatus(finishDto.getMaritalStatus());
        client.setDependentAmount(finishDto.getDependentAmount().intValue());
        client.setEmployment(finishDto.getEmployment());
        client.setAccountNumber(finishDto.getAccountNumber());
        client.getPassport().setIssueDate(finishDto.getPassportIssueDate());
        client.getPassport().setIssueBranch(finishDto.getPassportIssueBrach());
        return client;
    }

    public ScoringDataDto createScoringDataDto(Statement statement, Client client) {
         return ScoringDataDto.builder()
                .amount(statement.getLoanOffer().getRequestedAmount())
                .term(statement.getLoanOffer().getTerm())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .middleName(client.getMiddleName())
                .birthdate(client.getBirthDate())
                .passportSeries(client.getPassport().getSeries())
                .passportNumber(client.getPassport().getNumber())
                .passportIssueDate(client.getPassport().getIssueDate())
                .passportIssueBranch(client.getPassport().getIssueBranch())
                .gender(client.getGender())
                .maritalStatus(client.getMaritalStatus())
                .dependentAmount(client.getDependentAmount())
                .employment(client.getEmployment())
                .account(client.getAccountNumber())
                .isInsuranceEnabled(statement.getLoanOffer().isInsuranceEnabled())
                .isSalaryClient(statement.getLoanOffer().isSalaryClient())
                .build();
    }

    public Credit createCreditFromDto(CreditDto creditDto) {
        return Credit.builder()
                .amount(creditDto.getAmount())
                .term(creditDto.getTerm())
                .monthlyPayment(creditDto.getMonthlyPayment())
                .rate(creditDto.getRate())
                .psk(creditDto.getPsk())
                .isInsuranceEnabled(creditDto.getIsInsuranceEnabled())
                .isSalaryClient(creditDto.getIsSalaryClient())
                .paymentSchedule(creditDto.getPaymentSchedule())
                .creditStatus(CreditStatus.CALCULATED)
                .build();
    }
}
