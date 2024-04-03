package com.example.managementsystem.repositories;

import com.example.managementsystem.models.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SprintRepository extends JpaRepository<Sprint, Long> {
}