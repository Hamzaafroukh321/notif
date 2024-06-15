package com.example.managementsystem.repositories;

import com.example.managementsystem.models.entities.Sprint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SprintRepository extends JpaRepository<Sprint, Long> {
    Page<Sprint> findAllByProjetId(Long projetId, Pageable pageable);
}
