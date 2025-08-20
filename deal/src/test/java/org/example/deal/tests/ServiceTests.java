package org.example.deal.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.example.deal.service.KafkaProducerService;
import org.example.deal.dto.*;
import org.example.deal.dto.enums.*;
import org.example.deal.entity.Client;
import org.example.deal.entity.Credit;
import org.example.deal.entity.Statement;
import org.example.deal.exception.ErrorHandlingControllerAdvice;
import org.example.deal.repository.ClientRepository;
import org.example.deal.repository.CreditRepository;
import org.example.deal.repository.StatementRepository;
import org.example.deal.service.impl.ImplDealService;
import org.example.deal.service.utils.CalcClient;
import org.example.deal.service.utils.MapperData;
import org.example.deal.utils.TestUtils;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServiceTests {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @InjectMocks
    private ImplDealService service;

    @Mock
    private StatementRepository statementRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private CreditRepository creditRepository;

    @Mock
    private CalcClient calcClient;

    @Mock
    private MapperData mapperData;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private KafkaProducerService kafkaProducerService;

    @Mock
    private HttpSession httpSession;

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
        Client client = new Client();
        List<LoanOfferDto> loanOfferDtoList = new ArrayList<>();
        loanOfferDtoList.add(TestUtils.generateLoanOfferDto());
        loanOfferDtoList.add(TestUtils.generateLoanOfferDto());
        loanOfferDtoList.add(TestUtils.generateLoanOfferDto());
        loanOfferDtoList.add(TestUtils.generateLoanOfferDto());

        when(mapperData.toClient(loanStatementRequestDto)).thenReturn(client);
        when(clientRepository.save(any())).thenReturn(client);
        when(statementRepository.save(any(Statement.class))).thenAnswer(invocation -> {
            Statement s = invocation.getArgument(0);
            s.setId(4L);
            return s;
        });
        when(calcClient.getOfferFromTheRequest(any())).thenReturn(loanOfferDtoList);
        


        List<LoanOfferDto> offers = service.createStatement(loanStatementRequestDto);

        Assertions.assertNotNull(offers);
        assertEquals(loanOfferDtoList.get(0).getStatementId(), 4l);
    }

    @Test
    void selectOfferTest() {
        LoanOfferDto loanOfferDto = TestUtils.generateLoanOfferDto();
        loanOfferDto.setStatementId(1l);

        Statement statement = new Statement();
        statement.setId(1l);
        statement.setStatus(ApplicationStatus.PREAPPROVAL);
        statement.setStatusHistory(new ArrayList<>());

        when(statementRepository.findById(1l)).thenReturn(Optional.of(statement));

        service.selectOffer(loanOfferDto);

        verify(statementRepository).save(any(Statement.class));

    }

    @Test
    void calculateCreditTest() {
        Long statementId = 1L;
        FinishRegistrationRequestDto finishDto = TestUtils.generateFinishRegistrationRequestDto();

        Client existingClient = new Client();
        Client updatedClient = new Client();
        updatedClient.setId(5L);

        Credit credit = new Credit();
        credit.setId(10L);

        CreditDto creditDto = new CreditDto();
        ScoringDataDto scoringData = new ScoringDataDto();

        Statement statement = new Statement();
        statement.setId(statementId);
        statement.setClient(existingClient);
        statement.setStatusHistory(new ArrayList<>());

        when(statementRepository.findById(statementId)).thenReturn(Optional.of(statement));
        when(mapperData.updateClient(finishDto, existingClient)).thenReturn(updatedClient);
        when(clientRepository.save(updatedClient)).thenReturn(updatedClient);
        when(mapperData.toScoringDataDto(statement, updatedClient)).thenReturn(scoringData);
        when(calcClient.getCreditFromTheRequest(scoringData)).thenReturn(creditDto);
        when(mapperData.toCredit(creditDto)).thenReturn(credit);
        when(creditRepository.save(credit)).thenReturn(credit);

        service.calculate(finishDto, statementId);

        verify(statementRepository).save(statement);
        verify(clientRepository).save(updatedClient);
        verify(creditRepository).save(credit);
    }

    @Test
    void selectOfferTest_shouldThrowEntityNotFoundException_whenStatementNotFound() {
        Long nonExistentId = 999L;
        LoanOfferDto loanOfferDto = new LoanOfferDto();
        loanOfferDto.setStatementId(nonExistentId);

        when(statementRepository.findById(nonExistentId))
                .thenReturn(Optional.empty());

        EntityNotFoundException thrown = assertThrows(
                EntityNotFoundException.class,
                () -> service.selectOffer(loanOfferDto)
        );
    }

    @Test
    void calculateCreditTest_shouldThrowEntityNotFoundException_whenStatementNotFound() {
        Long nonExistentId = 999L;
        FinishRegistrationRequestDto finishDto = TestUtils.generateFinishRegistrationRequestDto();

        when(statementRepository.findById(nonExistentId))
                .thenReturn(Optional.empty());

        EntityNotFoundException thrown = assertThrows(
                EntityNotFoundException.class,
                () -> service.calculate(finishDto, nonExistentId)
        );
    }
}
