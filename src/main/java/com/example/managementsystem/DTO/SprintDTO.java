package com.example.managementsystem.DTO;

import java.time.LocalDate;

public record SprintDTO(
        Long id,
        String nom,
        LocalDate dateDebut,
        LocalDate dateFin,
        Long projetId
) {}
