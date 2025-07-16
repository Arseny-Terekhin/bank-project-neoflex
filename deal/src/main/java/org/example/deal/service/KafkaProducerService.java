package org.example.deal.service;

public interface KafkaProducerService {

    void finishRegistration(Long statementId);

    void createDocuments(Long statementId);

    void sendDocuments(Long statementId);

    void sendSes(Long statementId, String code);

    void creditIssued(Long statementId);

    void statementDenied(Long statementId);
}
