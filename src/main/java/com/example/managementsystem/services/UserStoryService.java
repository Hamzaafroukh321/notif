package com.example.managementsystem.services;

import com.example.managementsystem.DTO.UserStoryDTO;
import com.example.managementsystem.config.AuditUtil;
import com.example.managementsystem.exceptions.NotFoundException;
import com.example.managementsystem.mappers.UserStoryMapper;
import com.example.managementsystem.models.entities.UserStory;
import com.example.managementsystem.repositories.UserStoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    private final AuditUtil auditUtil;

    @Autowired
    public UserStoryService(UserStoryRepository userStoryRepository, UserStoryMapper userStoryMapper,
                            BacklogService backlogService, AuditUtil auditUtil) {
        this.userStoryRepository = userStoryRepository;
        this.userStoryMapper = userStoryMapper;
        this.backlogService = backlogService;
        this.auditUtil = auditUtil;
    }

    @PreAuthorize("hasAuthority('MANAGE_TASKS')")
    public UserStoryDTO createUserStory(UserStoryDTO userStoryDTO) {
        // Vérifier si le backlog existe
        backlogService.getBacklogById(userStoryDTO.backlogId());

        UserStory userStory = userStoryMapper.toEntity(userStoryDTO);
        UserStory savedUserStory = userStoryRepository.save(userStory);
        UserStoryDTO savedUserStoryDTO = userStoryMapper.toDTO(savedUserStory);

        auditUtil.logAudit("CREATE", "Created User Story with details: " + savedUserStory.toString());

        return savedUserStoryDTO;
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

        UserStoryDTO oldUserStoryDTO = userStoryMapper.toDTO(existingUserStory);

        userStoryMapper.updateUserStoryFromDTO(updatedUserStoryDTO, existingUserStory);
        UserStory savedUserStory = userStoryRepository.save(existingUserStory);
        UserStoryDTO savedUserStoryDTO = userStoryMapper.toDTO(savedUserStory);

        auditUtil.logAudit("UPDATE", "Updated User Story with ID: " + id + " from details: " + oldUserStoryDTO.toString() + " to details: " + savedUserStoryDTO.toString());

        return savedUserStoryDTO;
    }

    @PreAuthorize("hasAuthority('MANAGE_TASKS')")
    public void deleteUserStory(Long id) {
        UserStory userStory = userStoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User story not found with id: " + id));

        userStoryRepository.delete(userStory);

        auditUtil.logAudit("DELETE", "Deleted User Story with ID: " + id + " with details: " + userStory.toString());
    }


    @PreAuthorize("hasAnyAuthority('VIEW_ASSIGNED_TASKS', 'MANAGE_TASKS')")
    public List<UserStoryDTO> getAllUserStories() {
        List<UserStory> userStories = userStoryRepository.findAll();
        return userStories.stream()
                .map(userStoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasAnyAuthority('VIEW_ASSIGNED_TASKS', 'MANAGE_TASKS')")
    public Page<UserStoryDTO> getUserStoriesByBacklogId(Integer backlogId, Pageable pageable) {
        Page<UserStory> userStories = userStoryRepository.findAllByBacklogId(backlogId, pageable);
        return userStories.map(userStoryMapper::toDTO);
    }
}
