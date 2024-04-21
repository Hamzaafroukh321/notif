package com.example.managementsystem.repositories;

import com.example.managementsystem.models.entities.Projet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjetRepository extends JpaRepository<Projet, Long> {
}