package com.example.managementsystem.controllers;

import com.example.managementsystem.DTO.UserDTO;
import com.example.managementsystem.services.UserService;
import jakarta.validation.Valid;
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
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        UserDTO savedUserDTO = userService.saveUser(userDTO);
        return ResponseEntity.ok(savedUserDTO);
    }

    @GetMapping("/{matricule}")
    public UserDTO getUserByMatricule(@PathVariable Long matricule) {
        return userService.getUserByMatricule(matricule);
    }

    // update user

    @PutMapping("/{matricule}")
    public UserDTO updateUser(@PathVariable Long matricule, @RequestBody UserDTO updatedUserDTO) {
        return userService.updateUser(matricule, updatedUserDTO);
    }

    @DeleteMapping("/{matricule}")
    public void deleteUserByMatricule(@PathVariable Long matricule) {
        userService.deleteUserByMatricule(matricule);
    }

    @PutMapping
    public UserDTO saveAndFlushUser(@RequestBody UserDTO userDTO) {
        return userService.saveAndFlushUser(userDTO);
    }
}