package com.example.managementsystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import com.example.managementsystem.models.Notification;

@Controller
public class NotificationController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void sendNotification(Notification notification) {
        messagingTemplate.convertAndSend("/topic/notifications", notification);
    }
}
