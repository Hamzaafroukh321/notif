package com.example.managementsystem.repositories;


import com.example.managementsystem.models.entities.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> , JpaSpecificationExecutor<User> {
    Optional<User> findByMatricule(Long matricule);
    Optional<User> findByEmail(String email);
    Page<User> findAll(Pageable pageable);

    Optional<User> findByNom(String nom);

    Optional<User> findByPrenom(String prenom);


}