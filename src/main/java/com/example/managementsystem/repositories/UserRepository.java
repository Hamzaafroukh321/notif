package com.example.managementsystem.repositories;


import com.example.managementsystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByMatricule(Long matricule);

    User save(User user);

    void deleteById(Long matricule);

    User saveAndFlush(User user);

}