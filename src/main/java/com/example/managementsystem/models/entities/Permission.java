package com.example.managementsystem.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
//Constructs a new instance of the entity class
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    public Permission() {
    }

    public Permission(String name) {
        this.name = name;
    }
}