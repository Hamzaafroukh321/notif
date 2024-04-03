package com.example.managementsystem.repositories;

import com.example.managementsystem.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}