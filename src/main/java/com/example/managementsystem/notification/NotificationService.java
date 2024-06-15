package com.example.managementsystem.notification;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class NotificationService {
    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    public SseEmitter createEmitter(Long recipientMatricule) {
        SseEmitter emitter = new SseEmitter();
        emitters.put(recipientMatricule, emitter);
        emitter.onCompletion(() -> emitters.remove(recipientMatricule));
        emitter.onTimeout(() -> emitters.remove(recipientMatricule));
        emitter.onError(e -> emitters.remove(recipientMatricule));
        return emitter;
    }

    public Notification sendNotification(Notification notification) {
        notification.setTimestamp(LocalDateTime.now());
        Long recipientMatricule = notification.getRecipient();
        SseEmitter emitter = emitters.get(recipientMatricule);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().name("notification").data(notification).reconnectTime(3000));
            } catch (Exception e) {
                emitters.remove(recipientMatricule);
            }
        }
        return notification;
    }
}
