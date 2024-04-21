package com.example.managementsystem.models.entities;

import com.example.managementsystem.models.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", initialValue = 10000, allocationSize = 1)
    private Long matricule;

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    private String prenom;
    // on doit pas avoir deux emails identiques

    @Column(nullable = false, unique = true)
    private String emailpersonnel;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @NotBlank(message = "Le numéro de téléphone est obligatoire")
    private String tel;

    @NotBlank(message = "L'adresse est obligatoire")
    private String adresse;

    @NotBlank(message = "Le département est obligatoire")
    private String departement;

    @NotBlank(message = "La civilité est obligatoire")
    private String civilite;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany(mappedBy = "requestedBy")
    private List<Congees> conges;

    @ManyToMany(mappedBy = "teamMembers")
    private List<Projet> projets;

    @OneToOne(mappedBy = "user")
    private UserProfile userProfile;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}