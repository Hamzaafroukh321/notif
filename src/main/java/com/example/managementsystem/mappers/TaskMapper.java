package com.example.managementsystem.mappers;

import com.example.managementsystem.DTO.TaskDTO;
import com.example.managementsystem.models.entities.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskMapper {
    @Mapping(source = "sprint.id", target = "sprintId")
    @Mapping(source = "assignedTo.matricule", target = "assignedToMatricule")
    TaskDTO toDTO(Task task);

    @Mapping(source = "sprintId", target = "sprint.id")
    @Mapping(source = "assignedToMatricule", target = "assignedTo.matricule")
    Task toEntity(TaskDTO taskDTO);

    @Mapping(target = "id", ignore = true)
    void updateTaskFromDTO(TaskDTO taskDTO, @MappingTarget Task task);
}