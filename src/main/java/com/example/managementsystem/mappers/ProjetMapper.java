package com.example.managementsystem.mappers;

import com.example.managementsystem.DTO.ProjetDTO;
import com.example.managementsystem.DTO.UserDTO;
import com.example.managementsystem.models.entities.Projet;
import com.example.managementsystem.models.entities.User;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProjetMapper {

    @Mapping(source = "chef.matricule", target = "chefMatricule")
    @Mapping(source = "teamMembers", target = "teamMembersMatricules", qualifiedByName = "usersToMatricules")
    ProjetDTO toDTO(Projet projet);

    @Mapping(target = "chef", source = "chefMatricule", qualifiedByName = "findUserByMatricule")
    @Mapping(target = "teamMembers", source = "teamMembersMatricules", qualifiedByName = "findUsersByMatricules")
    Projet toEntity(ProjetDTO projetDTO, @Context List<User> users);

    @Named("usersToMatricules")
    default List<Long> usersToMatricules(List<User> users) {
        return users.stream()
                .map(User::getMatricule)
                .collect(Collectors.toList());
    }

    @Named("findUserByMatricule")
    default User findUserByMatricule(Long matricule, @Context List<User> users) {
        return users.stream()
                .filter(user -> user.getMatricule().equals(matricule))
                .findFirst()
                .orElse(null);
    }

    @Named("findUsersByMatricules")
    default List<User> findUsersByMatricules(List<Long> matricules, @Context List<User> users) {
        return matricules.stream()
                .map(matricule -> findUserByMatricule(matricule, users))
                .filter(user -> user != null)
                .collect(Collectors.toList());
    }

    @Mapping(target = "id", ignore = true)
    void updateProjetFromDTO(ProjetDTO projetDTO, @MappingTarget Projet projet);

    List<ProjetDTO> toDTOs(List<Projet> projets);
}