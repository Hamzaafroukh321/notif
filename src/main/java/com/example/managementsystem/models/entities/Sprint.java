package com.example.managementsystem.models.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
public class Sprint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private LocalDate dateDebut;
    private LocalDate dateFin;

    @ManyToOne
    @JoinColumn(name = "projet_id")
    private Projet projet;

    @OneToMany(mappedBy = "sprint")
    private List<Task> tasks;
}