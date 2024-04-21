package com.example.managementsystem.mappers;

import com.example.managementsystem.DTO.BacklogDTO;
import com.example.managementsystem.models.entities.Backlog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BacklogMapper {
    @Mapping(source = "projet.id", target = "projetId")
    BacklogDTO toDTO(Backlog backlog);

    @Mapping(source = "projetId", target = "projet.id")
    Backlog toEntity(BacklogDTO backlogDTO);

    @Mapping(target = "id", ignore = true)
    void updateBacklogFromDTO(BacklogDTO backlogDTO, @MappingTarget Backlog backlog);

    List<BacklogDTO> toDTOs(List<Backlog> backlogs);
}