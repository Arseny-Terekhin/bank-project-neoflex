package org.example.deal;

import org.example.deal.dto.LoanOfferDto;
import org.example.deal.dto.LoanStatementRequestDto;
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
        List<LoanOfferDto> offers = calcClient.getOffersQuery(dto);
        offers.stream().forEach(System.out::println);


    }

}
