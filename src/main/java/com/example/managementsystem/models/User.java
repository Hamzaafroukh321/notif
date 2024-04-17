package com.example.managementsystem.models;


import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", initialValue = 10000, allocationSize = 1)
    private Long matricule;
    private String nom;
    private String prenom;
    private String emailpersonnel;

    private String email;
    @Column(nullable = false)
    private String password;
    private String tel;
    private String adresse;

    private String departement;

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

    // constructor

    public Long getMatricule() {
        return matricule;
    }

    public void setMatricule(Long matricule) {
        this.matricule = matricule;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public List<Congees> getConges() {
        return conges;
    }

    public void setConges(List<Congees> conges) {
        this.conges = conges;
    }

    public List<Projet> getProjets() {
        return projets;
    }

    public void setProjets(List<Projet> projets) {
        this.projets = projets;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public String getCivilite() {
        return civilite;
    }

    public void setCivilite(String civilite) {
        this.civilite = civilite;
    }

    public String getEmailpersonnel() {
        return emailpersonnel;
    }

    public void setEmailpersonnel(String emailpersonnel) {
        this.emailpersonnel = emailpersonnel;
    }
}
