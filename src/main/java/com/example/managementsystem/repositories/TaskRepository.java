package com.example.managementsystem.repositories;

import com.example.managementsystem.models.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}