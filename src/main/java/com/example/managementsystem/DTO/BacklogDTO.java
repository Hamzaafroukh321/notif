package com.example.managementsystem.DTO;

public record BacklogDTO(
        Long id,
        String titre,
        String description,
        String etat,
        Long projetId
) {}