package com.example.managementsystem.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {
    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationRepository notificationRepository;
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    @Autowired
    public NotificationService(SimpMessagingTemplate messagingTemplate, NotificationRepository notificationRepository) {
        this.messagingTemplate = messagingTemplate;
        this.notificationRepository = notificationRepository;
    }

    public Notification sendNotification(String message, String userEmail) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setTimestamp(LocalDateTime.now());
        notification.setRecipient(userEmail);  // Use email as recipient

        Notification savedNotification = notificationRepository.save(notification);
        logger.info("Notification saved: " + savedNotification);
        messagingTemplate.convertAndSendToUser(userEmail, "/queue/notifications", savedNotification);
        logger.info("Notification sent via WebSocket to user " + userEmail);
        return savedNotification;
    }

    public List<Notification> getLatestNotificationsForRecipient(String email, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "timestamp"));
        return notificationRepository.findByRecipientOrderByTimestampDesc(email, pageable).getContent();
    }
}
