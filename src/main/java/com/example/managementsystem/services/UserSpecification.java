package com.example.managementsystem.services;

import com.example.managementsystem.models.entities.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;



public class UserSpecification {

    public static Specification<User> searchByNom(String nom) {
        return (Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            return builder.like(builder.lower(root.get("nom")), "%" + nom.toLowerCase() + "%");
        };
    }

    public static Specification<User> searchByPrenom(String prenom) {
        return (Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            return builder.like(builder.lower(root.get("prenom")), "%" + prenom.toLowerCase() + "%");
        };
    }

    public static Specification<User> searchByMatricule(Long matricule) {
        return (Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            return builder.equal(root.get("matricule"), matricule);
        };
    }
}