package com.example.managementsystem.services;

import com.example.managementsystem.DTO.PermissionDTO;
import com.example.managementsystem.exceptions.NotFoundException;
import com.example.managementsystem.mappers.PermissionMapper;
import com.example.managementsystem.models.entities.Permission;
import com.example.managementsystem.repositories.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
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
    @PreAuthorize("hasAuthority('MANAGE_PERMISSIONS')")
    public PermissionDTO getPermissionByName(String name) {
        Permission permission = permissionRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Permission not found with name: " + name));
        return permissionMapper.toDTO(permission);
    }
    @PreAuthorize("hasAuthority('MANAGE_PERMISSIONS')")
    public List<PermissionDTO> getAllPermissions() {
        List<Permission> permissions = permissionRepository.findAll();
        return permissionMapper.toDTOs(permissions);
    }
}