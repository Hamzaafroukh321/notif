package com.example.managementsystem.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class NotificationListener {
    private static final Logger logger = LoggerFactory.getLogger(NotificationListener.class);

    @SubscribeMapping("/user/project_manager@dxc.com/queue/notifications")
    @SendToUser("/queue/notifications")
    public Notification handleNotification(Notification notification) {
        logger.info("Received notification: {}", notification);
        return notification;
    }
}
