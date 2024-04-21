package com.example.managementsystem.DTO;

import java.time.LocalDate;
import java.util.List;

public record ProjetDTO(
        Long id,
        String nom,
        String description,
        LocalDate dateDebut,
        LocalDate dateFin,
        String mode,
        String status,
        Long chefMatricule,
        List<Long> teamMembersMatricules
) {}
