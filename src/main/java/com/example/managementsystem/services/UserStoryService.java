package com.example.managementsystem.services;

import com.example.managementsystem.DTO.UserStoryDTO;
import com.example.managementsystem.exceptions.NotFoundException;
import com.example.managementsystem.mappers.UserStoryMapper;
import com.example.managementsystem.models.entities.UserStory;
import com.example.managementsystem.repositories.UserStoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserStoryService {
    private final UserStoryRepository userStoryRepository;
    private final UserStoryMapper userStoryMapper;
    private final BacklogService backlogService;

    @Autowired
    public UserStoryService(UserStoryRepository userStoryRepository, UserStoryMapper userStoryMapper,
                            BacklogService backlogService) {
        this.userStoryRepository = userStoryRepository;
        this.userStoryMapper = userStoryMapper;
        this.backlogService = backlogService;
    }

    @PreAuthorize("hasAuthority('MANAGE_TASKS')")
    public UserStoryDTO createUserStory(UserStoryDTO userStoryDTO) {
        // Vérifier si le backlog existe
        backlogService.getBacklogById(userStoryDTO.backlogId());

        UserStory userStory = userStoryMapper.toEntity(userStoryDTO);
        UserStory savedUserStory = userStoryRepository.save(userStory);
        return userStoryMapper.toDTO(savedUserStory);
    }

    @PreAuthorize("hasAnyAuthority('VIEW_ASSIGNED_TASKS', 'MANAGE_TASKS')")
    public UserStoryDTO getUserStoryById(Long id) {
        UserStory userStory = userStoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User story not found with id: " + id));
        return userStoryMapper.toDTO(userStory);
    }

    @PreAuthorize("hasAuthority('MANAGE_TASKS')")
    public UserStoryDTO updateUserStory(Long id, UserStoryDTO updatedUserStoryDTO) {
        UserStory existingUserStory = userStoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User story not found with id: " + id));

        // Vérifier si le backlog existe
        backlogService.getBacklogById(updatedUserStoryDTO.backlogId());

        userStoryMapper.updateUserStoryFromDTO(updatedUserStoryDTO, existingUserStory);

        UserStory savedUserStory = userStoryRepository.save(existingUserStory);
        return userStoryMapper.toDTO(savedUserStory);
    }

    @PreAuthorize("hasAuthority('MANAGE_TASKS')")
    public void deleteUserStory(Long id) {
        UserStory userStory = userStoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User story not found with id: " + id));

        userStoryRepository.delete(userStory);
    }

    @PreAuthorize("hasAnyAuthority('VIEW_ASSIGNED_TASKS', 'MANAGE_TASKS')")
    public List<UserStoryDTO> getAllUserStories() {
        List<UserStory> userStories = userStoryRepository.findAll();
        return userStories.stream()
                .map(userStoryMapper::toDTO)
                .collect(Collectors.toList());
    }
}