package com.example.managementsystem.mappers;

import com.example.managementsystem.DTO.UserDTO;
import com.example.managementsystem.models.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserDTO toDTO(User user);
    User toEntity(UserDTO userDTO);

    @Mapping(target = "nom", source = "userDTO.nom")
    @Mapping(target = "prenom", source = "userDTO.prenom")
    @Mapping(target = "emailpersonnel", source = "userDTO.emailpersonnel")
    @Mapping(target = "email", source = "userDTO.email")
    @Mapping(target = "tel", source = "userDTO.tel")
    @Mapping(target = "adresse", source = "userDTO.adresse")
    @Mapping(target = "departement", source = "userDTO.departement")
    @Mapping(target = "civilite", source = "userDTO.civilite")
    @Mapping(target = "role", source = "userDTO.role")
    void updateUserFromDTO(@MappingTarget User user, UserDTO userDTO);

    //get all users
    List<UserDTO> toDTOs(List<User> users);
    //get by matricule


}
