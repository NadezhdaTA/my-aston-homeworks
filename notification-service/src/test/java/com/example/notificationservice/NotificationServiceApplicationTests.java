package com.example.notificationservice;

import com.example.common.model.UserEvent;
import com.example.common.model.UserStatus;
import com.example.notificationservice.service.NotificationServiceImpl;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = NotificationServiceImpl.class)
class NotificationServiceImplTest {

    @MockitoBean
    private JavaMailSender mailSender;

    private final String fromEmail = "test@test.com";

    @InjectMocks
    private NotificationServiceImpl notificationService;

    private final UserEvent userCreatedEvent = UserEvent.builder()
            .id(7L)
            .email("test@example.com")
            .status(UserStatus.CREATED)
            .build();

    private final UserEvent userDeletedEvent = UserEvent.builder()
            .id(7L)
            .email("test@example.com")
            .status(UserStatus.DELETED)
            .build();

    @BeforeEach
    void setUp() {
        MimeMessage mimeMessage = new MimeMessage((Session) null);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        ReflectionTestUtils.setField(notificationService, "fromEmail", "test@test.com");
    }

    @Test
    void shouldSendEmailOnUserCreated() throws MessagingException, IOException {
        notificationService.sendNotification(userCreatedEvent);

        verify(mailSender).createMimeMessage();
        verify(mailSender).send(any(MimeMessage.class));

        ArgumentCaptor<MimeMessage> messageCaptor = ArgumentCaptor.forClass(MimeMessage.class);
        verify(mailSender).send(messageCaptor.capture());

        MimeMessage sentMessage = messageCaptor.getValue();
        assertEquals("test@example.com", sentMessage.getRecipients(Message.RecipientType.TO)[0].toString());
        assertEquals("test@test.com", sentMessage.getFrom()[0].toString());
        assertEquals("Notification!", sentMessage.getSubject());

        String content = new String(sentMessage.getContent().toString().getBytes(), StandardCharsets.UTF_8);
        assertTrue(content.contains("Здравствуйте! Ваш аккаунт на сайте был успешно создан."));
    }

    @Test
    void shouldSendEmailOnUserDeleted() throws MessagingException, IOException {

        notificationService.sendNotification(userDeletedEvent);

        verify(mailSender).createMimeMessage();
        verify(mailSender).send(any(MimeMessage.class));

        ArgumentCaptor<MimeMessage> messageCaptor = ArgumentCaptor.forClass(MimeMessage.class);
        verify(mailSender).send(messageCaptor.capture());

        MimeMessage sentMessage = messageCaptor.getValue();
        assertEquals("test@example.com", sentMessage.getRecipients(Message.RecipientType.TO)[0].toString());
        assertEquals("test@test.com", sentMessage.getFrom()[0].toString());
        assertEquals("Notification!", sentMessage.getSubject());

        String content = new String(sentMessage.getContent().toString().getBytes(), StandardCharsets.UTF_8);
        assertTrue(content.contains("Здравствуйте! Ваш аккаунт был удалён"));
    }


}
