package org.example.calculator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.calculator.controller.CalcController;
import org.example.calculator.dto.CreditDto;
import org.example.calculator.dto.LoanOfferDto;
import org.example.calculator.dto.LoanStatementRequestDto;
import org.example.calculator.dto.ScoringDataDto;
import org.example.calculator.exception.ErrorHandlingControllerAdvice;
import org.example.calculator.service.CalculatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
public class ControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Mock
    private CalculatorService creditService;

    @InjectMocks
    private CalcController calcController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(calcController)
                .setControllerAdvice(new ErrorHandlingControllerAdvice())
                .build();
        objectMapper =  new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    void offersTest_1() throws Exception {
        LoanStatementRequestDto request = TestDTO.generateLoanStatementRequestDto();

        String requestBody = objectMapper.writeValueAsString(request);
        List<LoanOfferDto> loanOfferDtoList = new ArrayList<>();
        loanOfferDtoList.add(LoanOfferDto.builder().rate(BigDecimal.valueOf(17)).build());

        Mockito.when(creditService.getLoanOffers(any())).thenReturn(loanOfferDtoList);

        mockMvc.perform(post("/calculator/offers")
                                .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andDo(result -> System.out.println("Response: " + result.getResponse().getContentAsString()));
    }


    @Test
    void offersTest_2() throws Exception {
        LoanStatementRequestDto request = TestDTO.generateLoanStatementRequestDto();
        request.setTerm(3);

        String requestBody = objectMapper.writeValueAsString(request);

        List<LoanOfferDto> loanOfferDtoList = new ArrayList<>();
        loanOfferDtoList.add(LoanOfferDto.builder().rate(BigDecimal.valueOf(17)).build());

        Mockito.lenient().when(creditService.getLoanOffers(request)).thenReturn(loanOfferDtoList);

        mockMvc.perform(post("/calculator/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andDo(resul -> System.out.println("Response: " + resul.getResponse().getContentAsString()));
    }

    @Test
    void calcTest_1() throws Exception {
        ScoringDataDto data = TestDTO.generateScoringDataDto();
        CreditDto result = CreditDto.builder()
                .amount(data.getAmount())
                .term(data.getTerm())
                .build();

        String requestBody = objectMapper.writeValueAsString(data);

        Mockito.when(creditService.calculateCredit(any())).thenReturn(result);

        mockMvc.perform(post("/calculator/calc")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andDo(resul -> System.out.println("Response: " + resul.getResponse().getContentAsString()))
                .andExpect(jsonPath("$.amount").value(data.getAmount()))
                .andExpect(jsonPath("$.term").value(data.getTerm()));
    }

    @Test
    void calcTest_2() throws Exception {
        ScoringDataDto data = TestDTO.generateScoringDataDto();
        data.setPassportSeries("111");
        CreditDto result = CreditDto.builder()
                .amount(data.getAmount())
                .term(data.getTerm())
                .build();

        String requestBody = objectMapper.writeValueAsString(data);

        Mockito.lenient().when(creditService.calculateCredit(any())).thenReturn(result);

        mockMvc.perform(post("/calculator/calc")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andDo(resul -> System.out.println("Response: " + resul.getResponse().getContentAsString()));
    }
}
