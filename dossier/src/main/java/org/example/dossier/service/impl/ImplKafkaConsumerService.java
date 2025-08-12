package org.example.dossier.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dossier.service.MailSenderService;
import org.example.moduledto.dto.EmailMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.example.dossier.service.KafkaConsumerService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImplKafkaConsumerService implements KafkaConsumerService {

    private final MailSenderService mailSenderService;

    @KafkaListener(topics = "finish-registration", groupId = "dossier-group")
    @Override
    public void finishRegistration(EmailMessage emailMessage) {
        log.info("Start sending email. Email: {}, Theme: {}, Statement Id: {}, Kafka topic: finish-registration",
                emailMessage.getAddress(), emailMessage.getTheme(), emailMessage.getStatementId());

        mailSenderService.sendMessage(emailMessage);

        log.info("End sending email, Kafka topic: finish-registration");
    }

    @KafkaListener(topics = "create-documents", groupId = "dossier-group")
    @Override
    public void createDocuments(EmailMessage emailMessage) {
        log.info("Start sending email. Email: {}, Theme: {}, Statement Id: {}, Kafka topic: create-documents",
                emailMessage.getAddress(), emailMessage.getTheme(), emailMessage.getStatementId());

        mailSenderService.sendMessage(emailMessage);

        log.info("End sending email, Kafka topic: create-documents");
    }

    @KafkaListener(topics = "send-documents", groupId = "dossier-group")
    @Override
    public void sendDocuments(EmailMessage emailMessage) {
        log.info("Start sending email. Email: {}, Theme: {}, Statement Id: {}, Kafka topic: send-documents",
                emailMessage.getAddress(), emailMessage.getTheme(), emailMessage.getStatementId());

        mailSenderService.sendMessage(emailMessage);

        log.info("End sending email, Kafka topic: send-documents");
    }

    @KafkaListener(topics ="send-ses", groupId = "dossier-group")
    @Override
    public void sendSes(EmailMessage emailMessage) {
        log.info("Start sending email. Email: {}, Theme: {}, Statement Id: {}, Kafka topic: send-ses",
                emailMessage.getAddress(), emailMessage.getTheme(), emailMessage.getStatementId());

        mailSenderService.sendMessage(emailMessage);

        log.info("End sending email, Kafka topic: send-ses");
    }

    @KafkaListener(topics = "credit-issued", groupId = "dossier-group")
    @Override
    public void creditIssued(EmailMessage emailMessage) {
        log.info("Start sending email. Email: {}, Theme: {}, Statement Id: {}, Kafka topic: credit-issued",
                emailMessage.getAddress(), emailMessage.getTheme(), emailMessage.getStatementId());

        mailSenderService.sendMessage(emailMessage);

        log.info("End sending email, Kafka topic: credit-issued");
    }

    @KafkaListener(topics = "statement-denied", groupId = "dossier-group")
    @Override
    public void statementDenied(EmailMessage emailMessage) {
        log.info("Start sending email. Email: {}, Theme: {}, Statement Id: {}, Kafka topic: statement-denied",
                emailMessage.getAddress(), emailMessage.getTheme(), emailMessage.getStatementId());

        mailSenderService.sendMessage(emailMessage);

        log.info("End sending email, Kafka topic: statement-denied");
    }
}
