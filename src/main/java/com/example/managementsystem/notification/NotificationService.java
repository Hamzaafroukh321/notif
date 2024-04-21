package com.example.managementsystem.notification;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationService {
    private final SimpMessagingTemplate messagingTemplate;

    public NotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public Notification sendNotification(Notification notification) {
        notification.setTimestamp(LocalDateTime.now());
        messagingTemplate.convertAndSendToUser(notification.getRecipient(), "/topic/notifications", notification);
        return notification;
    }
}
