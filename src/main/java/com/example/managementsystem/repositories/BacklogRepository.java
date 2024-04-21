package com.example.managementsystem.repositories;

import com.example.managementsystem.models.entities.Backlog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BacklogRepository extends JpaRepository<Backlog, Integer> {
}