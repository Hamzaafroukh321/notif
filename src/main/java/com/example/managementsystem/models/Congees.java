package com.example.managementsystem.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import java.time.LocalDate;


@Entity
class Congees {
    @Id
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public CongeStatus getStatus() {
        return status;
    }

    public void setStatus(CongeStatus status) {
        this.status = status;
    }

    public User getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(User requestedBy) {
        this.requestedBy = requestedBy;
    }

    public User getRemplacant() {
        return remplacant;
    }

    public void setRemplacant(User remplacant) {
        this.remplacant = remplacant;
    }
}