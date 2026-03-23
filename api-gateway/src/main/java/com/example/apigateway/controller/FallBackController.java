package com.example.apigateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping
public class FallBackController {

    @GetMapping("/fallback/user-service")
    public Mono<String> userServiceFallback() {
        log.info("fallback user-service");
        return Mono.just("User Service недоступен. Попробуйте отправить запрос позже.");
    }

    @GetMapping("/fallback/notification-service")
    public Mono<String> notificationServiceFallback() {
        log.info("fallback notification-service");
        return Mono.just("Notification Service недоступен. Попробуйте отправить запрос позже.");
    }
}
