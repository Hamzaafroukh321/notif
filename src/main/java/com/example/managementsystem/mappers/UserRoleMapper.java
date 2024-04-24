package com.example.managementsystem.mappers;

import com.example.managementsystem.DTO.UserRoleDTO;
import com.example.managementsystem.models.entities.UserRole;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {PermissionMapper.class})
public interface UserRoleMapper {
    UserRoleDTO toDTO(UserRole userRole);
    UserRole toEntity(UserRoleDTO userRoleDTO);
    List<UserRoleDTO> toDTOs(List<UserRole> userRoles);
}