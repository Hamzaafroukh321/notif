package com.example.managementsystem.controllers;

import com.example.managementsystem.models.User;
import com.example.managementsystem.models.UserRole;
import com.example.managementsystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping("/{matricule}")
    public User getUserByMatricule(@PathVariable Long matricule) {
        return userService.getUserByMatricule(matricule);
    }

    @DeleteMapping("/{matricule}")
    public void deleteUserByMatricule(@PathVariable Long matricule) {
        userService.deleteUserByMatricule(matricule);
    }

    @PutMapping
    public User saveAndFlushUser(@RequestBody User user) {
        return userService.saveAndFlushUser(user);
    }
}