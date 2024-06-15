package com.example.managementsystem.repositories;

import com.example.managementsystem.models.entities.Projet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjetRepository extends JpaRepository<Projet, Long> {

    List<Projet> findByChef_Matricule(Long chefMatricule);
}