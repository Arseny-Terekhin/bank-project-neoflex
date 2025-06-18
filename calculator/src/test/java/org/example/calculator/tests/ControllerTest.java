package org.example.calculator.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.calculator.utils.TestUtils;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
    void offersTest_OkRequest() throws Exception {
        LoanStatementRequestDto request = TestUtils.generateLoanStatementRequestDto();

        String requestBody = objectMapper.writeValueAsString(request);
        List<LoanOfferDto> loanOfferDtoList = new ArrayList<>();
        loanOfferDtoList.add(LoanOfferDto.builder().rate(BigDecimal.valueOf(17)).build());

        Mockito.when(creditService.getLoanOffers(any())).thenReturn(loanOfferDtoList);

        mockMvc.perform(post("/calculator/offers")
                                .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].rate").value(17));
    }


    @Test
    void offersTest_2_BadRequest_ValidMin() throws Exception {
        LoanStatementRequestDto request = TestUtils.generateLoanStatementRequestDto();
        request.setTerm(3);

        String requestBody = objectMapper.writeValueAsString(request);

        List<LoanOfferDto> loanOfferDtoList = new ArrayList<>();
        loanOfferDtoList.add(LoanOfferDto.builder().rate(BigDecimal.valueOf(17)).build());

        Mockito.lenient().when(creditService.getLoanOffers(request)).thenReturn(loanOfferDtoList);

        mockMvc.perform(post("/calculator/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.violations[0].fieldName").value("term"))
                .andExpect(jsonPath("$.violations[0].message").value("Срок кредита должен быть не менее 6 месяцев"));
    }

    @Test
    void offersTest_3_BadRequest_ValidDecimalMin() throws Exception {
        LoanStatementRequestDto request = TestUtils.generateLoanStatementRequestDto();
        request.setAmount(BigDecimal.valueOf(1));

        String requestBody = objectMapper.writeValueAsString(request);

        List<LoanOfferDto> loanOfferDtoList = new ArrayList<>();
        loanOfferDtoList.add(LoanOfferDto.builder().rate(BigDecimal.valueOf(17)).build());

        Mockito.lenient().when(creditService.getLoanOffers(request)).thenReturn(loanOfferDtoList);

        mockMvc.perform(post("/calculator/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.violations[0].fieldName").value("amount"))
                .andExpect(jsonPath("$.violations[0].message").value("Сумма кредита должна быть не менее 20 000"));
    }

    @Test
    void offersTest_4_BadRequest_ValidEmail() throws Exception {
        LoanStatementRequestDto request = TestUtils.generateLoanStatementRequestDto();
        request.setEmail("123");

        String requestBody = objectMapper.writeValueAsString(request);

        List<LoanOfferDto> loanOfferDtoList = new ArrayList<>();
        loanOfferDtoList.add(LoanOfferDto.builder().rate(BigDecimal.valueOf(17)).build());

        Mockito.lenient().when(creditService.getLoanOffers(request)).thenReturn(loanOfferDtoList);

        mockMvc.perform(post("/calculator/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.violations[0].fieldName").value("email"))
                .andExpect(jsonPath("$.violations[0].message").value("email введен не коректно"));
    }

    @Test
    void offersTest_5_BadRequest_ValidPhone() throws Exception {
        LoanStatementRequestDto request = TestUtils.generateLoanStatementRequestDto();
        request.setPhone("123");

        String requestBody = objectMapper.writeValueAsString(request);

        List<LoanOfferDto> loanOfferDtoList = new ArrayList<>();
        loanOfferDtoList.add(LoanOfferDto.builder().rate(BigDecimal.valueOf(17)).build());

        Mockito.lenient().when(creditService.getLoanOffers(request)).thenReturn(loanOfferDtoList);

        mockMvc.perform(post("/calculator/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.violations[0].fieldName").value("phone"))
                .andExpect(jsonPath("$.violations[0].message").value("Телефон должен быть в формате +79**-***-**-**"));
    }

    @Test
    void offersTest_6_BadRequest_ValidNotNull() throws Exception {
        LoanStatementRequestDto request = TestUtils.generateLoanStatementRequestDto();
        request.setPhone(null);

        String requestBody = objectMapper.writeValueAsString(request);

        List<LoanOfferDto> loanOfferDtoList = new ArrayList<>();
        loanOfferDtoList.add(LoanOfferDto.builder().rate(BigDecimal.valueOf(17)).build());

        Mockito.lenient().when(creditService.getLoanOffers(request)).thenReturn(loanOfferDtoList);

        mockMvc.perform(post("/calculator/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.violations[0].fieldName").value("phone"))
                .andExpect(jsonPath("$.violations[0].message").value("phone должен быть заполнен"));
    }

    @Test
    void calcTest_1_OkRequest() throws Exception {
        ScoringDataDto data = TestUtils.generateScoringDataDto();
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
                .andExpect(jsonPath("$.amount").value(data.getAmount()))
                .andExpect(jsonPath("$.term").value(data.getTerm()));
    }

    @Test
    void calcTest_2_BadRequest_ValidSize() throws Exception {
        ScoringDataDto data = TestUtils.generateScoringDataDto();
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
                .andExpect(jsonPath("$.violations[0].fieldName").value("passportSeries"))
                .andExpect(jsonPath("$.violations[0].message").value("Серия паспорта должна состоять из 4 цифр"));
    }

    @Test
    void calcTest_3_BadRequest_ValidNotNull() throws Exception {
        ScoringDataDto data = TestUtils.generateScoringDataDto();
        data.setPassportSeries(null);
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
                .andExpect(jsonPath("$.violations[0].fieldName").value("passportSeries"))
                .andExpect(jsonPath("$.violations[0].message").value("passportSeries должен быть заполнен"));
    }

    @Test
    void calcTest_4_BadRequest_ValidPhone() throws Exception {
        ScoringDataDto data = TestUtils.generateScoringDataDto();
        data.setPhone("asd");
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
                .andExpect(jsonPath("$.violations[0].fieldName").value("phone"))
                .andExpect(jsonPath("$.violations[0].message").value("Телефон должен быть в формате +79**-***-**-**"));
    }

    @Test
    void calcTest_5_BadRequest_ValidEmail() throws Exception {
        ScoringDataDto data = TestUtils.generateScoringDataDto();
        data.setEmail("asdasd");
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
                .andExpect(jsonPath("$.violations[0].fieldName").value("email"))
                .andExpect(jsonPath("$.violations[0].message").value("email введен не коректно"));
    }

}
