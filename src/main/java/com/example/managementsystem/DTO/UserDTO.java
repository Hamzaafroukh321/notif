package com.example.managementsystem.DTO;

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
        Set<String> roles
){
    public Long getMatricule() {
        return matricule;
    }

    // getRoles Set<String> roles



}