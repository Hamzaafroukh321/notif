package com.example.managementsystem.services;

import com.example.managementsystem.exceptions.NotFoundException;
import com.example.managementsystem.models.entities.Sprint;
import com.example.managementsystem.repositories.SprintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.managementsystem.DTO.SprintDTO;
import com.example.managementsystem.mappers.SprintMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SprintService {
    private final SprintRepository sprintRepository;
    private final SprintMapper sprintMapper;
    private final ProjetService projetService;

    @Autowired
    public SprintService(SprintRepository sprintRepository, SprintMapper sprintMapper, ProjetService projetService) {
        this.sprintRepository = sprintRepository;
        this.sprintMapper = sprintMapper;
        this.projetService = projetService;
    }

    @PreAuthorize("hasAnyAuthority('VIEW_PROJECT_PROGRESS', 'MANAGE_SPRINTS')")
    public List<SprintDTO> getAllSprints() {
        List<Sprint> sprints = sprintRepository.findAll();
        return sprints.stream()
                .map(sprintMapper::toDTO)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasAnyAuthority('VIEW_PROJECT_PROGRESS', 'MANAGE_SPRINTS')")
    public SprintDTO getSprintById(Long id) {
        Sprint sprint = sprintRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Sprint not found with id: " + id));
        return sprintMapper.toDTO(sprint);
    }

    @PreAuthorize("hasAuthority('MANAGE_SPRINTS')")
    public SprintDTO createSprint(SprintDTO sprintDTO) {
        projetService.getProjetById(sprintDTO.projetId());

        Sprint sprint = sprintMapper.toEntity(sprintDTO);
        Sprint savedSprint = sprintRepository.save(sprint);
        return sprintMapper.toDTO(savedSprint);
    }

    @PreAuthorize("hasAuthority('MANAGE_SPRINTS')")
    public SprintDTO updateSprint(Long id, SprintDTO updatedSprintDTO) {
        Sprint existingSprint = sprintRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Sprint not found with id: " + id));

        projetService.getProjetById(updatedSprintDTO.projetId());

        sprintMapper.updateSprintFromDTO(updatedSprintDTO, existingSprint);
        Sprint savedSprint = sprintRepository.save(existingSprint);
        return sprintMapper.toDTO(savedSprint);
    }

    @PreAuthorize("hasAuthority('MANAGE_SPRINTS')")
    public void deleteSprintById(Long id) {
        Sprint sprint = sprintRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Sprint not found with id: " + id));

        sprintRepository.delete(sprint);
    }
}