package com.example.managementsystem.DTO;

import com.example.managementsystem.models.enums.TaskStatus;

public record TaskDTO(
        Long id,
        String description,
        TaskStatus status,
        Long sprintId,
        Long assignedToMatricule
) {}

