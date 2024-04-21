package com.example.managementsystem.models.entities;

import com.example.managementsystem.models.entities.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class UserProfile {
    @Id
    private Long id;
    private Long matricule;

    @OneToOne
    @MapsId("matricule")
    private User user;
}