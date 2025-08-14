package org.example.deal.service.utils;

import org.example.moduledto.dto.*;
import org.example.moduledto.dto.enums.*;
import org.example.deal.entity.Client;
import org.example.deal.entity.Credit;
import org.example.deal.entity.Statement;
import org.example.deal.entity.jsonb.Passport;
import org.mapstruct.*;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = {Passport.class, CreditStatus.class})
public interface MapperData {

    @Mapping(target = "passport", expression = "java(new Passport(dto.getPassportSeries(), dto.getPassportNumber(), null, null))")
    Client toClient(LoanStatementRequestDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "gender", source = "gender")
    @Mapping(target = "maritalStatus", source = "maritalStatus")
    @Mapping(target = "dependentAmount", expression = "java(finishDto.getDependentAmount().intValue())")
    @Mapping(target = "employment", source = "employment")
    @Mapping(target = "accountNumber", source = "accountNumber")
    Client updateClient(FinishRegistrationRequestDto finishDto, @MappingTarget Client client);

    @AfterMapping
    default void updatePassport(FinishRegistrationRequestDto dto, @MappingTarget Client client) {
        if (client.getPassport() != null) {
            client.getPassport().setIssueDate(dto.getPassportIssueDate());
            client.getPassport().setIssueBranch(dto.getPassportIssueBrach());
        }
    }

    @Mapping(source = "statement.loanOffer.requestedAmount", target = "amount")
    @Mapping(source = "statement.loanOffer.term", target = "term")
    @Mapping(source = "client.firstName", target = "firstName")
    @Mapping(source = "client.lastName", target = "lastName")
    @Mapping(source = "client.middleName", target = "middleName")
    @Mapping(source = "client.birthdate", target = "birthdate")
    @Mapping(source = "client.passport.series", target = "passportSeries")
    @Mapping(source = "client.passport.number", target = "passportNumber")
    @Mapping(source = "client.passport.issueDate", target = "passportIssueDate")
    @Mapping(source = "client.passport.issueBranch", target = "passportIssueBranch")
    @Mapping(source = "client.gender", target = "gender")
    @Mapping(source = "client.maritalStatus", target = "maritalStatus")
    @Mapping(source = "client.dependentAmount", target = "dependentAmount")
    @Mapping(source = "client.employment", target = "employment")
    @Mapping(source = "client.accountNumber", target = "account")
    @Mapping(source = "statement.loanOffer.insuranceEnabled", target = "isInsuranceEnabled")
    @Mapping(source = "statement.loanOffer.salaryClient", target = "isSalaryClient")
    ScoringDataDto toScoringDataDto(Statement statement, Client client);

    @Mapping(target = "creditStatus", expression = "java(CreditStatus.CALCULATED)")
    Credit toCredit(CreditDto creditDto);
}
