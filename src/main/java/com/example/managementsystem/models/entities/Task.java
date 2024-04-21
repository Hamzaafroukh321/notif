package com.example.managementsystem.models.entities;

import com.example.managementsystem.models.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @ManyToOne
    @JoinColumn(name = "sprint_id")
    private Sprint sprint;

    @ManyToOne
    @JoinColumn(name = "user_matricule", insertable = false, updatable = false)
    private User assignedTo;
}