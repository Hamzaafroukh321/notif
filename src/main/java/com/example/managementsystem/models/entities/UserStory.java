package com.example.managementsystem.models.entities;

import com.example.managementsystem.models.entities.Backlog;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class UserStory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private String priority;

    @ManyToOne
    @JoinColumn(name = "backlog_id")
    private Backlog backlog;
}