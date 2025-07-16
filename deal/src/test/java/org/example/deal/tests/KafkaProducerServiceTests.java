package org.example.deal.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.example.deal.dto.EmailMessage;
import org.example.deal.dto.enums.Theme;
import org.example.deal.entity.Client;
import org.example.deal.entity.Statement;
import org.example.deal.repository.StatementRepository;
import org.example.deal.service.impl.ImplKafkaProducerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class KafkaProducerServiceTests {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @InjectMocks
    private ImplKafkaProducerService service;

    @Mock
    private StatementRepository statementRepository;

    @Mock
    private KafkaTemplate<String, EmailMessage> kafkaTemplate;;

    @Captor
    private ArgumentCaptor<EmailMessage> emailCaptor;


    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(service)
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void finishRegistrationTest(){
        Long statementId = 1l;

        Statement statement = Statement.builder()
                .client(Client.builder().email("123@na.ru").build())
                .id(statementId)
                .build();

        when(statementRepository.findById(statementId)).thenReturn(Optional.of(statement));

        service.finishRegistration(statementId);

        verify(kafkaTemplate).send(eq("finish-registration"), emailCaptor.capture());

        EmailMessage capturedMessage = emailCaptor.getValue();

        assertEquals(statementId, capturedMessage.getStatementId());
        assertEquals("123@na.ru", capturedMessage.getAddress());
        assertEquals(Theme.FINISH_REGISTRATION, capturedMessage.getTheme());

    }

    @Test
    void createDocumentsTest(){
        Long statementId = 1l;

        Statement statement = Statement.builder()
                .client(Client.builder().email("123@na.ru").build())
                .id(statementId)
                .statusHistory(new ArrayList<>())
                .build();

        when(statementRepository.findById(statementId)).thenReturn(Optional.of(statement));

        service.createDocuments(statementId);

        verify(kafkaTemplate).send(eq("create-documents"), emailCaptor.capture());

        EmailMessage capturedMessage = emailCaptor.getValue();

        assertEquals(statementId, capturedMessage.getStatementId());
        assertEquals("123@na.ru", capturedMessage.getAddress());
        assertEquals(Theme.CREATE_DOCUMENTS, capturedMessage.getTheme());
    }

    @Test
    void sendDocumentsTest(){
        Long statementId = 1l;

        Statement statement = Statement.builder()
                .client(Client.builder().email("123@na.ru").build())
                .id(statementId)
                .statusHistory(new ArrayList<>())
                .build();

        when(statementRepository.findById(statementId)).thenReturn(Optional.of(statement));

        service.sendDocuments(statementId);

        verify(kafkaTemplate).send(eq("send-documents"), emailCaptor.capture());

        EmailMessage capturedMessage = emailCaptor.getValue();

        assertEquals(statementId, capturedMessage.getStatementId());
        assertEquals("123@na.ru", capturedMessage.getAddress());
        assertEquals(Theme.SEND_DOCUMENTS, capturedMessage.getTheme());
    }

    @Test
    void sendSesTest(){
        Long statementId = 1l;

        Statement statement = Statement.builder()
                .client(Client.builder().email("123@na.ru").build())
                .id(statementId)
                .statusHistory(new ArrayList<>())
                .sesCode("111")
                .build();

        when(statementRepository.findById(statementId)).thenReturn(Optional.of(statement));

        service.sendSes(statementId, "111");

        verify(kafkaTemplate).send(eq("send-ses"), emailCaptor.capture());

        EmailMessage capturedMessage = emailCaptor.getValue();

        assertEquals(statementId, capturedMessage.getStatementId());
        assertEquals("123@na.ru", capturedMessage.getAddress());
        assertEquals(Theme.SEND_SES, capturedMessage.getTheme());
    }

    @Test
    void creditIssuedTest(){
        Long statementId = 1l;

        Statement statement = Statement.builder()
                .client(Client.builder().email("123@na.ru").build())
                .id(statementId)
                .statusHistory(new ArrayList<>())
                .build();

        when(statementRepository.findById(statementId)).thenReturn(Optional.of(statement));

        service.creditIssued(statementId);

        verify(kafkaTemplate).send(eq("credit-issued"), emailCaptor.capture());

        EmailMessage capturedMessage = emailCaptor.getValue();

        assertEquals(statementId, capturedMessage.getStatementId());
        assertEquals("123@na.ru", capturedMessage.getAddress());
        assertEquals(Theme.CREDIT_ISSUED, capturedMessage.getTheme());
    }

    @Test
    void statementDeniedTest(){
        Long statementId = 1l;

        Statement statement = Statement.builder()
                .client(Client.builder().email("123@na.ru").build())
                .id(statementId)
                .statusHistory(new ArrayList<>())
                .build();

        when(statementRepository.findById(statementId)).thenReturn(Optional.of(statement));

        service.statementDenied(statementId);

        verify(kafkaTemplate).send(eq("statement-denied"), emailCaptor.capture());

        EmailMessage capturedMessage = emailCaptor.getValue();

        assertEquals(statementId, capturedMessage.getStatementId());
        assertEquals("123@na.ru", capturedMessage.getAddress());
        assertEquals(Theme.STATEMENT_DENIED, capturedMessage.getTheme());
    }

    @Test
    void Test_NotFoundException(){
        Long nonExistentId = 999L;

        when(statementRepository.findById(nonExistentId))
                .thenReturn(Optional.empty());

        EntityNotFoundException thrown = assertThrows(
                EntityNotFoundException.class,
                () -> service.statementDenied(nonExistentId)
        );
    }


}
