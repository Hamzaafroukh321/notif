package com.example.managementsystem.DTO;

import java.util.Set;

public record UserRoleDTO(
        Long id,
        String name,
        Set<PermissionDTO> permissions
) {
}