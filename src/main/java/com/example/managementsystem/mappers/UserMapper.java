package com.example.managementsystem.mappers;

import com.example.managementsystem.DTO.UserDTO;
import com.example.managementsystem.models.entities.User;
import com.example.managementsystem.models.entities.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    @Mapping(target = "roles", source = "user.roles", qualifiedByName = "roleToString")
    UserDTO toDTO(User user);

    @Mapping(target = "roles", source = "userDTO.roles", qualifiedByName = "stringToRole")
    User toEntity(UserDTO userDTO);

    @Mapping(target = "nom", source = "userDTO.nom")
    @Mapping(target = "prenom", source = "userDTO.prenom")
    @Mapping(target = "emailpersonnel", source = "userDTO.emailpersonnel")
    @Mapping(target = "email", source = "userDTO.email")
    @Mapping(target = "tel", source = "userDTO.tel")
    @Mapping(target = "adresse", source = "userDTO.adresse")
    @Mapping(target = "departement", source = "userDTO.departement")
    @Mapping(target = "civilite", source = "userDTO.civilite")
    @Mapping(target = "roles", source = "userDTO.roles", qualifiedByName = "stringToRole")
    void updateUserFromDTO(@MappingTarget User user, UserDTO userDTO);

    //get all users
    List<UserDTO> toDTOs(List<User> users);

    @Named("roleToString")
    default Set<String> roleToString(Set<UserRole> roles) {
        if (roles == null) {
            return null;
        }
        return roles.stream()
                .map(UserRole::getName)
                .collect(Collectors.toSet());
    }

    @Named("stringToRole")
    default Set<UserRole> stringToRole(Set<String> roleNames) {
        if (roleNames == null) {
            return null;
        }
        return roleNames.stream()
                .map(roleName -> {
                    UserRole role = new UserRole();
                    role.setName(roleName);
                    return role;
                })
                .collect(Collectors.toSet());
    }
}