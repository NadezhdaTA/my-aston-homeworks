package com.example.kafka;

import com.example.common.model.UserEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@Log4j2
@Service
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, UserEvent> kafkaTemplate;

    private static final String USER_EVENT_TOPIC = "user-event";

    public void sendUserEvent(UserEvent user) {
        CompletableFuture<SendResult<String, UserEvent>> future = kafkaTemplate
                .send(USER_EVENT_TOPIC, String.valueOf(user.getId()), user);

        future.whenComplete((result, exception) -> {
            if (exception != null) {
                log.error("Kafka user-event sending exception: {} ", exception.getMessage());
            } else {
                log.info("Kafka user-event sent successfully {}", result.getRecordMetadata());
            }
        });
        log.info(LocalDateTime.now() + "Kafka user-event sent to topic: {} " + USER_EVENT_TOPIC, user);
    }

}
