package com.example.managementsystem.services;

import com.example.managementsystem.DTO.UserRoleDTO;
import com.example.managementsystem.exceptions.NotFoundException;
import com.example.managementsystem.mappers.UserRoleMapper;
import com.example.managementsystem.models.entities.Permission;
import com.example.managementsystem.models.entities.UserRole;
import com.example.managementsystem.repositories.PermissionRepository;
import com.example.managementsystem.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private PermissionRepository permissionRepository;

    public UserRoleDTO createUserRole(UserRoleDTO userRoleDTO) {
        UserRole userRole = userRoleMapper.toEntity(userRoleDTO);
        Set<Permission> permissions = userRole.getPermissions().stream()
                .map(permission -> permissionRepository.findByName(permission.getName())
                        .orElseThrow(() -> new NotFoundException("Permission not found with name: " + permission.getName())))
                .collect(Collectors.toSet());
        userRole.setPermissions(permissions);
        UserRole savedUserRole = userRoleRepository.save(userRole);
        return userRoleMapper.toDTO(savedUserRole);
    }

    public UserRoleDTO getUserRoleByName(String name) {
        UserRole userRole = userRoleRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("UserRole not found with name: " + name));
        return userRoleMapper.toDTO(userRole);
    }

    public List<UserRoleDTO> getAllUserRoles() {
        List<UserRole> userRoles = userRoleRepository.findAll();
        return userRoleMapper.toDTOs(userRoles);
    }

    public UserRoleDTO updateUserRolePermissions(String name, Set<String> permissionNames) {
        UserRole userRole = userRoleRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("UserRole not found with name: " + name));

        Set<Permission> permissions = permissionNames.stream()
                .map(permissionName -> permissionRepository.findByName(permissionName)
                        .orElseThrow(() -> new NotFoundException("Permission not found with name: " + permissionName)))
                .collect(Collectors.toSet());

        userRole.setPermissions(permissions);
        UserRole updatedUserRole = userRoleRepository.save(userRole);
        return userRoleMapper.toDTO(updatedUserRole);
    }
}