package com.example.managementsystem.controllers;

import com.example.managementsystem.DTO.PermissionDTO;
import com.example.managementsystem.exceptions.NotFoundException;
import com.example.managementsystem.services.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @PostMapping
    public ResponseEntity<PermissionDTO> createPermission(@RequestBody PermissionDTO permissionDTO) {
        PermissionDTO createdPermission = permissionService.createPermission(permissionDTO);
        return new ResponseEntity<>(createdPermission, HttpStatus.CREATED);
    }

    @GetMapping("/{name}")
    public ResponseEntity<PermissionDTO> getPermissionByName(@PathVariable String name) {
        PermissionDTO permission = permissionService.getPermissionByName(name);
        return new ResponseEntity<>(permission, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PermissionDTO>> getAllPermissions() {
        List<PermissionDTO> permissions = permissionService.getAllPermissions();
        return new ResponseEntity<>(permissions, HttpStatus.OK);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handlePermissionNotFoundException(NotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
