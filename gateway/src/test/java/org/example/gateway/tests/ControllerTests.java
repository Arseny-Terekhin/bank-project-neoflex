package org.example.gateway.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.gateway.controller.GatewayController;
import org.example.gateway.dto.FinishRegistrationRequestDto;
import org.example.gateway.dto.LoanOfferDto;
import org.example.gateway.dto.LoanStatementRequestDto;
import org.example.gateway.exception.ErrorHandlingControllerAdvice;
import org.example.gateway.service.GatewayService;
import org.example.gateway.utils.TestUtils;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ControllerTests {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Mock
    private GatewayService service;

    @InjectMocks
    private GatewayController controller;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ErrorHandlingControllerAdvice())
                .build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    void calculateCreditTest_OkRequest() throws Exception {
        Long statementId = 1L;

        FinishRegistrationRequestDto request = TestUtils.generateFinishRegistrationRequestDto();
        String requestBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/calculate/{statementId}", statementId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    void calculateCreditTest_1_BadRequest_Valid_NotNull() throws Exception {
        Long statementId = 1L;

        FinishRegistrationRequestDto request = TestUtils.generateFinishRegistrationRequestDto();
        request.setAccountNumber(null);
        String requestBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/calculate/{statementId}", statementId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$.violations[0].fieldName").value("accountNumber"))
                .andExpect(jsonPath("$.violations[0].message").value("accountNumber должен быть заполнен"));
    }

    @Test
    void createStatementTest_OkRequest() throws Exception {
        LoanStatementRequestDto request = TestUtils.generateLoanStatementRequestDto();

        String requestBody = objectMapper.writeValueAsString(request);
        List<LoanOfferDto> loanOfferDtoList = new ArrayList<>();
        loanOfferDtoList.add(LoanOfferDto.builder().rate(BigDecimal.valueOf(17)).build());

        Mockito.when(service.createStatement(any())).thenReturn(loanOfferDtoList);

        mockMvc.perform(post("/api/statement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].rate").value(17));
    }

    @Test
    void selectStatementTest_OkRequest() throws Exception {
        LoanOfferDto request = TestUtils.generateLoanOfferDto();

        String requestBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/offer/select")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
    }


    @Test
    void createStatementTest_1_BadRequest_NotNull() throws Exception {
        LoanStatementRequestDto request = TestUtils.generateLoanStatementRequestDto();
        request.setPassportSeries(null);

        String requestBody = objectMapper.writeValueAsString(request);
        List<LoanOfferDto> loanOfferDtoList = new ArrayList<>();
        loanOfferDtoList.add(LoanOfferDto.builder().rate(BigDecimal.valueOf(17)).build());

        mockMvc.perform(post("/api/statement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.violations[0].fieldName").value("passportSeries"))
                .andExpect(jsonPath("$.violations[0].message").value("passportSeries должен быть заполнен"));
    }

    @Test
    void createStatementTest_2_BadRequest_Min() throws Exception {
        LoanStatementRequestDto request = TestUtils.generateLoanStatementRequestDto();
        request.setTerm(3);

        String requestBody = objectMapper.writeValueAsString(request);
        List<LoanOfferDto> loanOfferDtoList = new ArrayList<>();
        loanOfferDtoList.add(LoanOfferDto.builder().rate(BigDecimal.valueOf(17)).build());

        mockMvc.perform(post("/api/statement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.violations[0].fieldName").value("term"))
                .andExpect(jsonPath("$.violations[0].message").value("Срок кредита должен быть не менее 6 месяцев"));
    }

    @Test
    void createStatementTest_3_BadRequest_Size() throws Exception {
        LoanStatementRequestDto request = TestUtils.generateLoanStatementRequestDto();
        request.setPassportSeries("123");

        String requestBody = objectMapper.writeValueAsString(request);
        List<LoanOfferDto> loanOfferDtoList = new ArrayList<>();
        loanOfferDtoList.add(LoanOfferDto.builder().rate(BigDecimal.valueOf(17)).build());

        mockMvc.perform(post("/api/statement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.violations[0].fieldName").value("passportSeries"))
                .andExpect(jsonPath("$.violations[0].message").value("Серия паспорта должна состоять из 4 цифр"));
    }

    @Test
    void selectStatementTest_1_BadRequest_NotNull() throws Exception {
        LoanOfferDto request = TestUtils.generateLoanOfferDto();
        request.setTerm(null);

        String requestBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/offer/select")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.violations[0].fieldName").value("term"))
                .andExpect(jsonPath("$.violations[0].message").value("term должен быть заполнен"));
    }

    @Test
    void selectStatementTest_2_BadRequest_Min() throws Exception {
        LoanOfferDto request = TestUtils.generateLoanOfferDto();
        request.setTerm(3);

        String requestBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/offer/select")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.violations[0].fieldName").value("term"))
                .andExpect(jsonPath("$.violations[0].message").value("Срок кредита должен быть не менее 6 месяцев"));
    }

}
