package com.example.managementsystem.repositories;

import com.example.managementsystem.models.entities.Congees;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CongeesRepository extends JpaRepository<Congees, Long> {
}