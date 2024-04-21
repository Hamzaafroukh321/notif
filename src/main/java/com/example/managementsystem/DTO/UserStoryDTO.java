package com.example.managementsystem.DTO;

public record UserStoryDTO(
        Long id,
        String description,
        String priority,
        Integer backlogId
) {}
