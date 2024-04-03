package com.example.managementsystem.repositories;

import com.example.managementsystem.models.Projet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Projet, Long> {
}