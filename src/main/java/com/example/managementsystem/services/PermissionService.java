package com.example.managementsystem.services;

import com.example.managementsystem.DTO.PermissionDTO;
import com.example.managementsystem.exceptions.NotFoundException;
import com.example.managementsystem.mappers.PermissionMapper;
import com.example.managementsystem.models.entities.Permission;
import com.example.managementsystem.repositories.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PermissionMapper permissionMapper;

    public PermissionDTO createPermission(PermissionDTO permissionDTO) {
        Permission permission = permissionMapper.toEntity(permissionDTO);
        Permission savedPermission = permissionRepository.save(permission);
        return permissionMapper.toDTO(savedPermission);
    }

    public PermissionDTO getPermissionByName(String name) {
        Permission permission = permissionRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Permission not found with name: " + name));
        return permissionMapper.toDTO(permission);
    }

    public List<PermissionDTO> getAllPermissions() {
        List<Permission> permissions = permissionRepository.findAll();
        return permissionMapper.toDTOs(permissions);
    }
}