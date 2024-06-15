package com.example.managementsystem.repositories;

import com.example.managementsystem.models.entities.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findAllBySprintId(Long sprintId, Pageable pageable);

    @Query("SELECT t FROM Task t WHERE t.sprint.projet.id = :projetId")
    List<Task> findTasksByProjetId(@Param("projetId") Long projetId);

    Page<Task> findAllByAssignedToMatricule(Long matricule, Pageable pageable);


}
