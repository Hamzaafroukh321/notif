package com.example.managementsystem.mappers;

import com.example.managementsystem.DTO.CongeDTO;
import com.example.managementsystem.models.entities.Congees;
import com.example.managementsystem.models.entities.User;
import com.example.managementsystem.services.UserService;
import org.mapstruct.*;

import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CongeMapper {
    @Mapping(source = "requestedBy.matricule", target = "requestedByMatricule")
    @Mapping(source = "remplacant.matricule", target = "remplacantMatricule")
    @Mapping(source = "approvedOrRejectedBy.matricule", target = "approvedOrRejectedByMatricule")
    CongeDTO toDTO(Congees conge);

    @Mapping(target = "requestedBy", ignore = true)
    @Mapping(target = "remplacant", ignore = true)
    @Mapping(target = "approvedOrRejectedBy", ignore = true)
    Congees toEntity(CongeDTO congeDTO);

    @AfterMapping
    default void mapRequestedByAndRemplacant(CongeDTO congeDTO, @MappingTarget Congees congees, @Context UserService userService) {
        if (congeDTO.requestedByMatricule() != null) {
            User requestedBy = userService.getUserEntityByMatricule(congeDTO.requestedByMatricule());
            congees.setRequestedBy(requestedBy);
        }
        if (congeDTO.remplacantMatricule() != null) {
            User remplacant = userService.getUserEntityByMatricule(congeDTO.remplacantMatricule());
            congees.setRemplacant(remplacant);
        }
        if (congeDTO.approvedOrRejectedByMatricule() != null) {
            User approvedOrRejectedBy = userService.getUserEntityByMatricule(congeDTO.approvedOrRejectedByMatricule());
            congees.setApprovedOrRejectedBy(approvedOrRejectedBy);
        }
    }

    @Mapping(target = "id", ignore = true)
    void updateCongeesFromDTO(CongeDTO congeDTO, @MappingTarget Congees congees);
}
