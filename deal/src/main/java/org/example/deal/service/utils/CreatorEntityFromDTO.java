package org.example.deal.service.utils;

import org.example.deal.dto.LoanStatementRequestDto;
import org.example.deal.entity.Client;
import org.example.deal.entity.pojo.Passport;
import org.springframework.stereotype.Component;

@Component
public class CreatorEntityFromDTO {

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
}
