package com.example.managementsystem.DTO;

import com.example.managementsystem.models.enums.UserRole;

public record UserDTO(
        Long matricule,
        String nom,
        String prenom,
        String emailpersonnel,
        String email,
        String tel,
        String adresse,
        String departement,
        String civilite,
        UserRole role
){
    public Long getMatricule() {
        return matricule;
    }
}