package org.example.deal;

import org.example.deal.dto.*;
import org.example.deal.dto.enums.EmploymentPosition;
import org.example.deal.dto.enums.EmploymentStatus;
import org.example.deal.dto.enums.Gender;
import org.example.deal.dto.enums.MaritalStatus;
import org.example.deal.service.utils.CalcClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class DealApplicationTests {

    @Autowired
    CalcClient calcClient;

    @Test
    void contextLoads() {
        LoanStatementRequestDto dto = LoanStatementRequestDto.builder()
                .amount(new BigDecimal("300000"))
                .term(6)
                .firstName("Алексей")
                .lastName("Смирнов")
                .email("alex@example.com")
                .middleName("1234")
                .passportSeries("1234")
                .passportNumber("567890")
                .birthdate(LocalDate.of(2000, 1, 1))
                .build();
        List<LoanOfferDto> offers = calcClient.getOfferFromTheRequest(dto);
        offers.stream().forEach(System.out::println);

        ScoringDataDto cdto = ScoringDataDto.builder()
                .amount(new BigDecimal("500000"))
                .term(12)
                .firstName("Иван")
                .lastName("Иванов")
                .birthdate(LocalDate.of(1990, 1, 1))
                .passportSeries("1234")
                .passportNumber("567890")
                .gender(Gender.MALE)
                .maritalStatus(MaritalStatus.MARRIED)
                .dependentAmount(1)
                .isInsuranceEnabled(true)
                .isSalaryClient(true)
                .employment(EmploymentDto.builder()
                        .employmentStatus(EmploymentStatus.EMPLOYED)
                        .employerINN("1234567890")
                        .position(EmploymentPosition.MID_MANAGER)
                        .salary(new BigDecimal("150000"))
                        .workExperienceCurrent(24)
                        .workExperienceTotal(60)
                        .build())
                .account("12345678901234567890")
                .middleName("123")
                .passportIssueBranch("1234")
                .passportIssueDate(LocalDate.of(2000, 1, 1))
                .build();

        CreditDto creditDto = calcClient.getCreditFromTheRequest(cdto);

        System.out.println(creditDto);
    }

}
