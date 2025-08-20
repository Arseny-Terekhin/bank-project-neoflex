package org.example.deal.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.deal.entity.Statement;
import org.example.deal.repository.StatementRepository;
import org.example.deal.service.KafkaProducerService;
import org.example.deal.dto.EmailMessage;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.example.deal.dto.*;
import org.example.deal.dto.enums.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImplKafkaProducerService implements KafkaProducerService {

    private final KafkaTemplate<String, EmailMessage> kafkaTemplate;
    private final StatementRepository statementRepository;

    @Override
    public void finishRegistration(Long statementId) {
        Statement statement = findStatement(statementId);

        EmailMessage emailMessage = EmailMessage.builder()
                .statementId(statementId)
                .text("Завершите оформление")
                .theme(Theme.FINISH_REGISTRATION)
                .address(statement.getClient().getEmail())
                .build();

        kafkaTemplate.send("finish-registration", emailMessage);
    }

    @Override
    public void createDocuments(Long statementId) {
        Statement statement = findStatement(statementId);

        EmailMessage emailMessage = EmailMessage.builder()
                .statementId(statementId)
                .text("Документы готовы. Вот ваши документы:... \n" +
                        "Отправьте запрос на подписание документов\n" +
                        "http://localhost:8081/deal/document/"+statementId +"/sign")
                .theme(Theme.CREATE_DOCUMENTS)
                .address(statement.getClient().getEmail())
                .build();

        statement.setStatus(ApplicationStatus.DOCUMENT_CREATED);
        statement.getStatusHistory().add(
                StatementStatusHistoryDto.builder()
                        .status(ApplicationStatus.DOCUMENT_CREATED)
                        .time(LocalDateTime.now())
                        .changeType(ChangeType.AUTOMATIC)
                        .build());
        statementRepository.save(statement);

        log.info("Statement updated to DOCUMENT_CREATED: id={}", statement.getId());

        kafkaTemplate.send("create-documents", emailMessage);
    }

    @Override
    public void sendDocuments(Long statementId) {
        Statement statement = findStatement(statementId);
        int randomInt = ThreadLocalRandom.current().nextInt(100000, 999999);
        String code = randomInt +"";
        statement.setSesCode(code);

        EmailMessage emailMessage = EmailMessage.builder()
                .statementId(statementId)
                .text("Код для подписание доккументов: " + code)
                .theme(Theme.SEND_DOCUMENTS)
                .address(statement.getClient().getEmail())
                .build();
        statementRepository.save(statement);

        kafkaTemplate.send("send-documents", emailMessage);
    }

    @Override
    public void sendSes(Long statementId, String code) {
        Statement statement = findStatement(statementId);
        String sesCode = statement.getSesCode();


        if (sesCode.equals(code)) {
            EmailMessage emailMessage = EmailMessage.builder()
                    .statementId(statementId)
                    .text("Верный код подтверждения.\nДокументы подписаны")
                    .theme(Theme.SEND_SES)
                    .address(statement.getClient().getEmail())
                    .build();

            statement.setStatus(ApplicationStatus.DOCUMENT_SIGNED);
            statement.getStatusHistory().add(
                    StatementStatusHistoryDto.builder()
                            .status(ApplicationStatus.DOCUMENT_SIGNED)
                            .time(LocalDateTime.now())
                            .changeType(ChangeType.AUTOMATIC)
                            .build());
            statementRepository.save(statement);

            log.info("Statement updated to DOCUMENT_SIGNED: id={}", statement.getId());

            kafkaTemplate.send("send-ses", emailMessage);
            creditIssued(statementId);

        }else {
            EmailMessage emailMessage = EmailMessage.builder()
                    .statementId(statementId)
                    .text("Не верный код подтверждения.\nДокументы не подписаны")
                    .theme(Theme.SEND_SES)
                    .address(statement.getClient().getEmail())
                    .build();
            kafkaTemplate.send("send-ses", emailMessage);
        }


    }

    @Override
    public void creditIssued(Long statementId) {
        Statement statement = findStatement(statementId);

        EmailMessage emailMessage = EmailMessage.builder()
                .statementId(statementId)
                .text("Кредит выдан")
                .theme(Theme.CREDIT_ISSUED)
                .address(statement.getClient().getEmail())
                .build();

        statement.setStatus(ApplicationStatus.CREDIT_ISSUED);
        statement.getStatusHistory().add(
                StatementStatusHistoryDto.builder()
                        .status(ApplicationStatus.CREDIT_ISSUED)
                        .time(LocalDateTime.now())
                        .changeType(ChangeType.AUTOMATIC)
                        .build());
        statementRepository.save(statement);

        log.info("Statement updated to CREDIT_ISSUED: id={}", statement.getId());

        kafkaTemplate.send("credit-issued", emailMessage);
    }

    @Override
    public void statementDenied(Long statementId) {
        Statement statement = findStatement(statementId);

        EmailMessage emailMessage = EmailMessage.builder()
                .statementId(statementId)
                .text("Вы отказались от оформления")
                .theme(Theme.STATEMENT_DENIED)
                .address(statement.getClient().getEmail())
                .build();

        kafkaTemplate.send("statement-denied", emailMessage);
    }

    private Statement findStatement(Long statementId){
        return statementRepository.findById(statementId)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException("Statement not found");
                });
    }
}
