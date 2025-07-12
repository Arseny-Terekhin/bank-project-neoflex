package org.example.dossier.service;

import org.example.dossier.dto.EmailMessage;

public interface KafkaConsumerService {

    void sendMessage(EmailMessage emailMessage);
}
