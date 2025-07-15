package org.example.dossier.service;

import org.example.dossier.dto.EmailMessage;

public interface KafkaConsumerService {

    void finishRegistration(EmailMessage emailMessage);
    void createDocuments(EmailMessage emailMessage);
    void sendDocuments(EmailMessage emailMessage);
    void sendSes(EmailMessage emailMessage);
    void creditIssued(EmailMessage emailMessage);
    void statementDenied(EmailMessage emailMessage);
}
