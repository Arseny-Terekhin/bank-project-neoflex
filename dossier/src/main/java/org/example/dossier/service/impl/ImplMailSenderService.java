package org.example.dossier.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.dossier.dto.EmailMessage;
import org.example.dossier.service.MailSenderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImplMailSenderService implements MailSenderService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    String username;

    @Override
    public void sendMessage(EmailMessage emailMessage) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(emailMessage.getAddress());
        simpleMailMessage.setSubject(String.valueOf(emailMessage.getTheme()));
        simpleMailMessage.setText(emailMessage.getText());
        simpleMailMessage.setFrom(username);

        mailSender.send(simpleMailMessage);
    }
}
