package com.example.managementsystem.notification;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class Notification {
    private String message;
    private LocalDateTime timestamp;
    private String recipient;
}