package com.example.managementsystem.DTO;

import com.example.managementsystem.models.enums.CongeStatus;

import java.time.LocalDate;

public record CongeDTO(
        Long id,
        LocalDate dateDebut,
        LocalDate dateFin,
        String motif,
        CongeStatus status,
        Long requestedByMatricule,
        Long remplacantMatricule
) {}
