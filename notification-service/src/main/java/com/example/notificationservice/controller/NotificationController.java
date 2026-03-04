package com.example.notificationservice.controller;

import com.example.common.model.UserEvent;
import com.example.notificationservice.service.NotificationService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/notification")
public class NotificationController {
    private NotificationService notificationService;

    @PostMapping("/send")
    public void sendNotification(@RequestBody @Valid UserEvent userEvent) throws MessagingException {
        notificationService.sendNotification(userEvent);
    }
}
