package org.example.dossier.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dossier.dto.EmailMessage;
import org.example.dossier.service.MailSenderService;
import org.springframework.kafka.annotation.KafkaListener;
import org.example.dossier.service.KafkaConsumerService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImplKafkaConsumerService implements KafkaConsumerService {

    private final MailSenderService mailSenderService;

    @KafkaListener(topics = {"finish-registration",
            "create-documents", "send-documents", "send-ses"
            , "credit-issued", "statement-denied"}, groupId = "dossier-group")
    @Override
    public void sendMessage(EmailMessage emailMessage) {
        log.info("Start sending email. Email: {}, Theme: {}, Statement Id: {}",
                emailMessage.getAddress(), emailMessage.getTheme(), emailMessage.getStatementId());

        mailSenderService.sendMessage(emailMessage);

        log.info("End sending email.");
    }
}
