package com.example.managementsystem.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Entity
public class Backlog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String titre;
    private String description;
    private String etat;

    @ManyToOne
    @JoinColumn(name = "projet_id")
    private Projet projet;

    @OneToMany(mappedBy = "backlog")
    private List<UserStory> userStories;
}