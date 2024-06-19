package com.example.managementsystem.mappers;

import com.example.managementsystem.DTO.PermissionDTO;
import com.example.managementsystem.DTO.UserRoleDTO;
import com.example.managementsystem.models.entities.Permission;
import com.example.managementsystem.models.entities.UserRole;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-13T13:46:43+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class UserRoleMapperImpl implements UserRoleMapper {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public UserRoleDTO toDTO(UserRole userRole) {
        if ( userRole == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        Set<PermissionDTO> permissions = null;

        id = userRole.getId();
        name = userRole.getName();
        permissions = permissionSetToPermissionDTOSet( userRole.getPermissions() );

        UserRoleDTO userRoleDTO = new UserRoleDTO( id, name, permissions );

        return userRoleDTO;
    }

    @Override
    public UserRole toEntity(UserRoleDTO userRoleDTO) {
        if ( userRoleDTO == null ) {
            return null;
        }

        UserRole userRole = new UserRole();

        userRole.setId( userRoleDTO.id() );
        userRole.setName( userRoleDTO.name() );
        userRole.setPermissions( permissionDTOSetToPermissionSet( userRoleDTO.permissions() ) );

        return userRole;
    }

    @Override
    public List<UserRoleDTO> toDTOs(List<UserRole> userRoles) {
        if ( userRoles == null ) {
            return null;
        }

        List<UserRoleDTO> list = new ArrayList<UserRoleDTO>( userRoles.size() );
        for ( UserRole userRole : userRoles ) {
            list.add( toDTO( userRole ) );
        }

        return list;
    }

    protected Set<PermissionDTO> permissionSetToPermissionDTOSet(Set<Permission> set) {
        if ( set == null ) {
            return null;
        }

        Set<PermissionDTO> set1 = new LinkedHashSet<PermissionDTO>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Permission permission : set ) {
            set1.add( permissionMapper.toDTO( permission ) );
        }

        return set1;
    }

    protected Set<Permission> permissionDTOSetToPermissionSet(Set<PermissionDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<Permission> set1 = new LinkedHashSet<Permission>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( PermissionDTO permissionDTO : set ) {
            set1.add( permissionMapper.toEntity( permissionDTO ) );
        }

        return set1;
    }
}
