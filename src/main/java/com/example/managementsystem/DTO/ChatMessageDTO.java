package com.example.managementsystem.DTO;

import java.time.LocalDateTime;

public record ChatMessageDTO(
        Long id,
        Long senderId,
        Long recipientId,
        String content,
        LocalDateTime timestamp,
        boolean isPrivate
) {
}
