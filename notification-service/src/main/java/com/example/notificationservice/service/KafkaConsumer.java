package com.example.notificationservice.service;

import com.example.common.model.UserEvent;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final NotificationService notificationService;

    @Transactional
    @KafkaListener(topics = "user-event", groupId = "notification-service")
    public void listen(UserEvent userEvent) throws MessagingException {
        try {
            log.info("Message consumed: {} ", userEvent);
            notificationService.sendNotification(userEvent);
            log.info("Successfully processed message: {}", userEvent.getId());
        } catch (Exception e) {
            log.error("Failed to process message {}: {}",
                    userEvent.getId(), e.getMessage(), e);
            throw e;
        }

    }

}
