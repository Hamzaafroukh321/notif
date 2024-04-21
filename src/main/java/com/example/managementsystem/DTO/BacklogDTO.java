package com.example.managementsystem.DTO;

public record BacklogDTO(
        Integer id,
        String titre,
        String description,
        String etat,
        Long projetId
) {}