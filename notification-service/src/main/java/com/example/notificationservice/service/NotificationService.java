package com.example.notificationservice.service;

import com.example.common.model.UserEvent;
import jakarta.mail.MessagingException;

public interface NotificationService {
    void sendNotification(UserEvent userEvent) throws MessagingException;
}
