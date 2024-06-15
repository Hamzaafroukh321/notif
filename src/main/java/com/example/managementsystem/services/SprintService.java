package com.example.managementsystem.services;

import com.example.managementsystem.config.AuditUtil;
import com.example.managementsystem.exceptions.NotFoundException;
import com.example.managementsystem.models.entities.Sprint;
import com.example.managementsystem.repositories.SprintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    private final AuditUtil auditUtil;

    @Autowired
    public SprintService(SprintRepository sprintRepository, SprintMapper sprintMapper, ProjetService projetService, AuditUtil auditUtil) {
        this.sprintRepository = sprintRepository;
        this.sprintMapper = sprintMapper;
        this.projetService = projetService;
        this.auditUtil = auditUtil;
    }

    @PreAuthorize("hasAnyAuthority('VIEW_PROJECT')")
    public List<SprintDTO> getAllSprints() {
        List<Sprint> sprints = sprintRepository.findAll();
        return sprints.stream()
                .map(sprintMapper::toDTO)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasAnyAuthority('VIEW_PROJECT')")
    public SprintDTO getSprintById(Long id) {
        Sprint sprint = sprintRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Sprint not found with id: " + id));
        return sprintMapper.toDTO(sprint);
    }

    @PreAuthorize("hasAnyAuthority('VIEW_PROJECT')")
    public SprintDTO createSprint(SprintDTO sprintDTO) {
        projetService.getProjetById(sprintDTO.projetId());

        Sprint sprint = sprintMapper.toEntity(sprintDTO);
        Sprint savedSprint = sprintRepository.save(sprint);

        auditUtil.logAudit("CREATE", "Created Sprint with details: " + savedSprint.toString());

        return sprintMapper.toDTO(savedSprint);
    }

    @PreAuthorize("hasAnyAuthority('VIEW_PROJECT')")
    public SprintDTO updateSprint(Long id, SprintDTO updatedSprintDTO) {
        Sprint existingSprint = sprintRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Sprint not found with id: " + id));

        projetService.getProjetById(updatedSprintDTO.projetId());

        SprintDTO oldSprintDTO = sprintMapper.toDTO(existingSprint);

        sprintMapper.updateSprintFromDTO(updatedSprintDTO, existingSprint);
        Sprint savedSprint = sprintRepository.save(existingSprint);

        SprintDTO newSprintDTO = sprintMapper.toDTO(savedSprint);
        auditUtil.logAudit("UPDATE", "Updated Sprint with ID: " + id + " from details: " + oldSprintDTO.toString() + " to details: " + newSprintDTO.toString());

        return newSprintDTO;
    }

    @PreAuthorize("hasAnyAuthority('VIEW_PROJECT')")
    public void deleteSprintById(Long id) {
        Sprint sprint = sprintRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Sprint not found with id: " + id));

        sprintRepository.delete(sprint);

        auditUtil.logAudit("DELETE", "Deleted Sprint with ID: " + id);
    }

    @PreAuthorize("hasAnyAuthority('VIEW_PROJECT')")
    public Page<SprintDTO> getSprintsByProjetId(Long projetId, Pageable pageable) {
        Page<Sprint> sprints = sprintRepository.findAllByProjetId(projetId, pageable);
        return sprints.map(sprintMapper::toDTO);
    }
}
