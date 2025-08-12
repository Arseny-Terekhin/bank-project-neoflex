package org.example.dossier.service;


import org.example.moduledto.dto.EmailMessage;

public interface MailSenderService {

    void sendMessage(EmailMessage emailMessage);
}
