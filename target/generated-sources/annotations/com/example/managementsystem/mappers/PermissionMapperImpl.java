package com.example.managementsystem.mappers;

import com.example.managementsystem.DTO.PermissionDTO;
import com.example.managementsystem.models.entities.Permission;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-13T13:46:44+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class PermissionMapperImpl implements PermissionMapper {

    @Override
    public PermissionDTO toDTO(Permission permission) {
        if ( permission == null ) {
            return null;
        }

        Long id = null;
        String name = null;

        id = permission.getId();
        name = permission.getName();

        PermissionDTO permissionDTO = new PermissionDTO( id, name );

        return permissionDTO;
    }

    @Override
    public Permission toEntity(PermissionDTO permissionDTO) {
        if ( permissionDTO == null ) {
            return null;
        }

        Permission permission = new Permission();

        permission.setId( permissionDTO.id() );
        permission.setName( permissionDTO.name() );

        return permission;
    }

    @Override
    public List<PermissionDTO> toDTOs(List<Permission> permissions) {
        if ( permissions == null ) {
            return null;
        }

        List<PermissionDTO> list = new ArrayList<PermissionDTO>( permissions.size() );
        for ( Permission permission : permissions ) {
            list.add( toDTO( permission ) );
        }

        return list;
    }
}
