package com.example.managementsystem.repositories;

import com.example.managementsystem.models.entities.Backlog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BacklogRepository extends JpaRepository<Backlog, Integer> {

    Page<Backlog> findAllByProjetId(Long projetId, Pageable pageable);
}