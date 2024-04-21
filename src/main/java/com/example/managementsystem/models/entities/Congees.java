package com.example.managementsystem.models.entities;

import com.example.managementsystem.models.enums.CongeStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Congees {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String motif;
    @Enumerated(EnumType.STRING)
    private CongeStatus status;

    @ManyToOne
    @JoinColumn(name = "requested_by_matricule", insertable = false, updatable = false)
    private User requestedBy;

    @ManyToOne
    @JoinColumn(name = "remplacant_matricule", insertable = false, updatable = false)
    private User remplacant;
}