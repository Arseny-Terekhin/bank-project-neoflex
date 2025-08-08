package org.example.deal.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.deal.controller.AdminController;
import org.example.deal.entity.Statement;
import org.example.deal.exception.ErrorHandlingControllerAdvice;
import org.example.deal.service.AdminService;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AdminControllerTests {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Mock
    private AdminService service;

    @InjectMocks
    private AdminController controller;

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
    void findAllStatement_OkRequest() throws Exception {
        List<Statement> statements = new ArrayList<>();

        Mockito.when(service.findAllStatement()).thenReturn(statements);

        mockMvc.perform(get("/deal/admin/statement")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void findStatement_OkRequest() throws Exception {
        Statement statement = Statement.builder()
                .id(1l)
                .build();
        Long id = 1l;

        Mockito.when(service.findStatement(any())).thenReturn(statement);

        mockMvc.perform(get("/deal/admin/statement/{statementId}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


}
