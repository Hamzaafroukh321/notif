package com.example.managementsystem.repositories;


import com.example.managementsystem.models.entities.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> , JpaSpecificationExecutor<User> {
    Optional<User> findByMatricule(Long matricule);
    Optional<User> findByEmail(String email);
    Page<User> findAll(Pageable pageable);

    Optional<User> findByNom(String nom);

    Optional<User> findByPrenom(String prenom);


    List<User> findAllById(Iterable<Long> ids);

    //get all users matricule

    @Query("SELECT u.matricule FROM User u")
    List<Long> findAllMatricule();

    //return all users with role name

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = :roleName")
    List<User> findAllByRoleName(@Param("roleName") String roleName);





}