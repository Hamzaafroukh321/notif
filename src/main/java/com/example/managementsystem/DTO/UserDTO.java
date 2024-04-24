package com.example.managementsystem.DTO;

import com.example.managementsystem.models.entities.UserRole;

import java.util.Set;

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
        Set<UserRole> roles
){
    public Long getMatricule() {
        return matricule;
    }
}