package com.example.managementsystem.mappers;

import com.example.managementsystem.DTO.UserStoryDTO;
import com.example.managementsystem.models.entities.UserStory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserStoryMapper {
    @Mapping(source = "backlog.id", target = "backlogId")
    UserStoryDTO toDTO(UserStory userStory);

    @Mapping(source = "backlogId", target = "backlog.id")
    UserStory toEntity(UserStoryDTO userStoryDTO);

    @Mapping(target = "id", ignore = true)
    void updateUserStoryFromDTO(UserStoryDTO userStoryDTO, @MappingTarget UserStory userStory);
}