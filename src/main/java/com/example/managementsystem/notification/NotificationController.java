package com.example.managementsystem.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/send")
    public ResponseEntity<Void> sendNotification(@RequestParam String message, @RequestParam String userEmail) {
        notificationService.sendNotification(message, userEmail);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/recipient")
    public ResponseEntity<List<Notification>> getLatestNotificationsForRecipient(@RequestParam String email, @RequestParam int page, @RequestParam int size) {
        List<Notification> notifications = notificationService.getLatestNotificationsForRecipient(email, page, size);
        return ResponseEntity.ok(notifications);
    }
}
