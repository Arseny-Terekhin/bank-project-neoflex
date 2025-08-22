package org.example.statement.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.statement.dto.LoanOfferDto;
import org.example.statement.dto.LoanStatementRequestDto;
import org.example.statement.exception.ErrorHandlingControllerAdvice;
import org.example.statement.service.impl.ImplStatementService;
import org.example.statement.service.utils.DealClient;
import org.example.statement.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ServiceTests {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @InjectMocks
    private ImplStatementService service;

    @Mock
    private DealClient  dealClient;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(service)
                .setControllerAdvice(new ErrorHandlingControllerAdvice())
                .build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    void createStatementTest() {
        LoanStatementRequestDto loanStatementRequestDto = TestUtils.generateLoanStatementRequestDto();
        List<LoanOfferDto> loanOfferDtoList = new ArrayList<>();
        loanOfferDtoList.add(TestUtils.generateLoanOfferDto());
        loanOfferDtoList.add(TestUtils.generateLoanOfferDto());
        loanOfferDtoList.add(TestUtils.generateLoanOfferDto());
        loanOfferDtoList.add(TestUtils.generateLoanOfferDto());

        when(dealClient.createStatement(any())).thenReturn(loanOfferDtoList);


        List<LoanOfferDto> offers = service.createStatement(loanStatementRequestDto);

        Assertions.assertNotNull(offers);
        assertEquals(loanOfferDtoList.get(0).getStatementId(), 1l);
    }

    @Test
    void selectOfferTest() {
        LoanOfferDto loanOfferDto = TestUtils.generateLoanOfferDto();
        loanOfferDto.setStatementId(1l);

        service.selectOffer(loanOfferDto);

        verify(dealClient).selectOffer(any(LoanOfferDto.class));

    }
}
