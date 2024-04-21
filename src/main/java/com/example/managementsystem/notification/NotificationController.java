package com.example.managementsystem.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import com.example.managementsystem.notification.Notification;


@Controller
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @MessageMapping("/notifications")
    @SendTo("/topic/notifications")
    public Notification sendNotification(Notification notification) {
        return notificationService.sendNotification(notification);
    }
}
