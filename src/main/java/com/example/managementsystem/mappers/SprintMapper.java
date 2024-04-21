package com.example.managementsystem.mappers;

import com.example.managementsystem.DTO.SprintDTO;
import com.example.managementsystem.models.entities.Sprint;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SprintMapper {
    @Mapping(source = "projet.id", target = "projetId")
    SprintDTO toDTO(Sprint sprint);

    @Mapping(source = "projetId", target = "projet.id")
    Sprint toEntity(SprintDTO sprintDTO);

    @Mapping(target = "id", ignore = true)
    void updateSprintFromDTO(SprintDTO sprintDTO, @MappingTarget Sprint sprint);
}