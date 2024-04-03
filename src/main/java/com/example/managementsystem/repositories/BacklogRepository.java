package com.example.managementsystem.repositories;

import com.example.managementsystem.models.Backlog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BacklogRepository extends JpaRepository<Backlog, Integer> {
}