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
    @JoinColumn(name = "requested_by_matricule")
    private User requestedBy;

    @ManyToOne
    @JoinColumn(name = "remplacant_matricule", nullable = true)
    private User remplacant;

    @ManyToOne
    @JoinColumn(name = "approved_or_rejected_by_matricule", nullable = true)
    private User approvedOrRejectedBy;

    @Override
    public String toString() {
        return "Congees{" +
                "id=" + id +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", motif='" + motif + '\'' +
                ", status=" + status +
                '}';
    }
}
