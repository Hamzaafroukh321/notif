package com.example.managementsystem.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
public class Projet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String description;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String mode;
    private String status;

    @ManyToOne
    private User chef;

    @ManyToMany
    @JoinTable(
            name = "projet_team_members",
            joinColumns = @JoinColumn(name = "projet_id"),
            inverseJoinColumns = @JoinColumn(name = "user_matricule")
    )
    private List<User> teamMembers;

    @OneToMany(mappedBy = "projet")
    private List<Backlog> backlogs;

    @OneToMany(mappedBy = "projet")
    private List<Sprint> sprints;
}