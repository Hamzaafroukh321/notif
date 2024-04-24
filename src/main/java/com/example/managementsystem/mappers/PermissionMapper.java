package com.example.managementsystem.mappers;

import com.example.managementsystem.DTO.PermissionDTO;
import com.example.managementsystem.models.entities.Permission;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    PermissionDTO toDTO(Permission permission);
    Permission toEntity(PermissionDTO permissionDTO);
    List<PermissionDTO> toDTOs(List<Permission> permissions);
}