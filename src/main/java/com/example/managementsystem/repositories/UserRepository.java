package com.example.managementsystem.repositories;


import com.example.managementsystem.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByMatricule(Long matricule);
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailpersonnel(String emailpersonnel);

    Optional<User> findByEmailOrMatricule(String email, Long matricule);


}