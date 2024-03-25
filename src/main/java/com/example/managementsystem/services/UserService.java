package com.example.managementsystem.services;

import com.example.managementsystem.models.User;
import com.example.managementsystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByMatricule(Long matricule) {
        return userRepository.findByMatricule(matricule);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUserByMatricule(Long matricule) {
        userRepository.deleteById(matricule);
    }

    public User saveAndFlushUser(User user) {
        return userRepository.saveAndFlush(user);
    }
}