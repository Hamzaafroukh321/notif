package com.example.managementsystem.controllers;

import com.example.managementsystem.DTO.UserRoleDTO;

import com.example.managementsystem.exceptions.NotFoundException;
import com.example.managementsystem.models.entities.Permission;
import com.example.managementsystem.models.entities.UserRole;
import com.example.managementsystem.services.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

import java.util.List;

import java.util.stream.Collectors;

import com.example.managementsystem.repositories.UserRoleRepository;
import com.example.managementsystem.mappers.UserRoleMapper;
import com.example.managementsystem.repositories.PermissionRepository;


@RestController
@RequestMapping("/api/user-roles")
public class UserRoleController {

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private PermissionRepository permissionRepository;

    @PostMapping
    public ResponseEntity<UserRoleDTO> createUserRole(@RequestBody UserRoleDTO userRoleDTO) {
        UserRoleDTO createdUserRole = userRoleService.createUserRole(userRoleDTO);
        return new ResponseEntity<>(createdUserRole, HttpStatus.CREATED);
    }

    @GetMapping("/{name}")
    public ResponseEntity<UserRoleDTO> getUserRoleByName(@PathVariable String name) {
        UserRoleDTO userRole = userRoleService.getUserRoleByName(name);
        return new ResponseEntity<>(userRole, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserRoleDTO>> getAllUserRoles() {
        List<UserRoleDTO> userRoles = userRoleService.getAllUserRoles();
        return new ResponseEntity<>(userRoles, HttpStatus.OK);
    }

    @PutMapping("/{name}/permissions")
    public ResponseEntity<UserRoleDTO> updateUserRolePermissions(@PathVariable String name, @RequestBody Set<String> permissionNames) {
        UserRoleDTO updatedUserRole = userRoleService.updateUserRolePermissions(name, permissionNames);
        return new ResponseEntity<>(updatedUserRole, HttpStatus.OK);
    }
}