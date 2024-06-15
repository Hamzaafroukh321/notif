package com.example.managementsystem.controllers;

import com.example.managementsystem.DTO.UserDTO;
import com.example.managementsystem.mappers.UserMapper;
import com.example.managementsystem.models.entities.User;
import com.example.managementsystem.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;


    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }
    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    @GetMapping("/page")
    public Page<UserDTO> getUsersPage(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userService.getAllUsers(pageable);
    }
    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    @GetMapping("/search")
    public ResponseEntity<Page<UserDTO>> searchUsers(
            @RequestParam(required = false) String nom,
            @RequestParam(required = false) String prenom,
            @RequestParam(required = false) Long matricule,
            @PageableDefault(size = 20) Pageable pageable) {

        Page<UserDTO> userDTOs = userService.searchUsers(nom, prenom, matricule, pageable);
        return ResponseEntity.ok(userDTOs);
    }

    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    @GetMapping
    public Page<UserDTO> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userService.getAllUsers(pageable);
    }


    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    @GetMapping("/count")
    public long getTotalUsers() {
        return userService.getTotalUsers();
    }
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        UserDTO savedUserDTO = userService.saveUser(userDTO);
        return ResponseEntity.ok(savedUserDTO);
    }

    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    @PutMapping("/{matricule}")
    public UserDTO updateUser(@PathVariable Long matricule, @RequestBody UserDTO updatedUserDTO) {
        return userService.updateUser(matricule, updatedUserDTO);
    }
    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    @DeleteMapping("/{matricule}")
    public void deleteUserByMatricule(@PathVariable Long matricule) {
        userService.deleteUserByMatricule(matricule);
    }

    @PutMapping
    public UserDTO saveUser(@RequestBody UserDTO userDTO) {
        return userService.saveAndFlushUser(userDTO);
    }
    @PreAuthorize("hasAuthority('VIEW_USERS')")
    @GetMapping("/matricules")
    public List<Long> getAllUsersMatricules() {
        System.out.println("getAllUsersMatricules called");  // Ligne de débogage
        return userService.getAllUsersMatricules();
    }

    @PreAuthorize("hasAuthority('VIEW_USERS')")
    @GetMapping("/roles/{roleName}")
    public List<UserDTO> getAllUsersByRoleName(@PathVariable String roleName) {
        return userService.getUserByRole(roleName);
    }


}