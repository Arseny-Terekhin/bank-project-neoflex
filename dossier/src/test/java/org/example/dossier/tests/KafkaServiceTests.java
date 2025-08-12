package org.example.dossier.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.moduledto.dto.EmailMessage;
import org.example.moduledto.dto.enums.Theme;
import org.example.dossier.service.impl.ImplKafkaConsumerService;
import org.example.dossier.service.impl.ImplMailSenderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class KafkaServiceTests {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private EmailMessage message;

    @InjectMocks
    private ImplKafkaConsumerService service;

    @Mock
    private ImplMailSenderService mailSenderService;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(service)
                .build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        message = EmailMessage.builder()
                .address("test@example.com")
                .theme(Theme.SEND_DOCUMENTS)
                .statementId(123L)
                .build();
    }

    @Test
    void testFinishRegistration() {

        service.finishRegistration(message);

        verify(mailSenderService, times(1)).sendMessage(message);
    }

    @Test
    void testCreateDocuments() {

        service.createDocuments(message);

        verify(mailSenderService, times(1)).sendMessage(message);
    }

    @Test
    void testSendDocuments() {

        service.sendDocuments(message);

        verify(mailSenderService, times(1)).sendMessage(message);
    }

    @Test
    void testSendSes() {

        service.sendSes(message);

        verify(mailSenderService, times(1)).sendMessage(message);
    }

    @Test
    void testCreditIssued() {

        service.creditIssued(message);

        verify(mailSenderService, times(1)).sendMessage(message);
    }

    @Test
    void testStatementDenied() {

        service.statementDenied(message);

        verify(mailSenderService, times(1)).sendMessage(message);
    }
}
