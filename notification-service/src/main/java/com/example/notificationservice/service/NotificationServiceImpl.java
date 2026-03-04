package com.example.notificationservice.service;

import com.example.common.model.UserEvent;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Override
    public void sendNotification(UserEvent userEvent) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");

        helper.setTo(userEvent.getEmail());
        helper.setFrom(fromEmail);
        helper.setSubject("Notification!");

        switch (userEvent.getStatus()) {
            case CREATED -> helper.setText("Здравствуйте! Ваш аккаунт на сайте был успешно создан.");
            case DELETED -> helper.setText("Здравствуйте! Ваш аккаунт был удалён");
            default -> throw new IllegalStateException("Unexpected value: " + userEvent.getStatus());
        }
        mailSender.send(message);
    }
}

