package com.example.managementsystem.mappers;

import com.example.managementsystem.DTO.CongeDTO;
import com.example.managementsystem.models.entities.Congees;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CongeMapper {
    @Mapping(source = "requestedBy.matricule", target = "requestedByMatricule")
    @Mapping(source = "remplacant.matricule", target = "remplacantMatricule")
    CongeDTO toDTO(Congees conge);

    @Mapping(source = "requestedByMatricule", target = "requestedBy.matricule")
    @Mapping(source = "remplacantMatricule", target = "remplacant.matricule")
    Congees toEntity(CongeDTO congeDTO);

    @Mapping(target = "id", ignore = true)
    void updateCongeesFromDTO(CongeDTO congeDTO, @MappingTarget Congees congees);
}