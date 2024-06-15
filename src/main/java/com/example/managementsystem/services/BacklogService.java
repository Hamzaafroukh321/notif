package com.example.managementsystem.services;

import com.example.managementsystem.DTO.BacklogDTO;
import com.example.managementsystem.config.AuditUtil;
import com.example.managementsystem.exceptions.NotFoundException;
import com.example.managementsystem.mappers.BacklogMapper;
import com.example.managementsystem.models.entities.Backlog;
import com.example.managementsystem.repositories.BacklogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BacklogService {
    private final BacklogRepository backlogRepository;
    private final BacklogMapper backlogMapper;
    private final ProjetService projetService;
    private final AuditUtil auditUtil;

    @Autowired
    public BacklogService(BacklogRepository backlogRepository, BacklogMapper backlogMapper, ProjetService projetService, AuditUtil auditUtil) {
        this.backlogRepository = backlogRepository;
        this.backlogMapper = backlogMapper;
        this.projetService = projetService;
        this.auditUtil = auditUtil;
    }

    @PreAuthorize("hasAuthority('MANAGE_TASKS')")
    public Page<BacklogDTO> getAllBacklogs(Pageable pageable) {
        Page<Backlog> backlogs = backlogRepository.findAll(pageable);
        return backlogs.map(backlogMapper::toDTO);
    }

    @PreAuthorize("hasAuthority('MANAGE_TASKS')")
    public Page<BacklogDTO> getBacklogsByProjectId(Long projectId, Pageable pageable) {
        Page<Backlog> backlogs = backlogRepository.findAllByProjetId(projectId, pageable);
        return backlogs.map(backlogMapper::toDTO);
    }

    @PreAuthorize("hasAuthority('MANAGE_TASKS')")
    public BacklogDTO getBacklogById(Integer id) {
        Backlog backlog = backlogRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Backlog not found with id: " + id));
        return backlogMapper.toDTO(backlog);
    }

    @PreAuthorize("hasAuthority('MANAGE_TASKS') and hasAuthority('VIEW_PROJECT')")
    public BacklogDTO createBacklog(BacklogDTO backlogDTO) {
        if (backlogDTO.projetId() == null) {
            throw new IllegalArgumentException("Project ID must not be null");
        }

        projetService.getProjetById(backlogDTO.projetId());

        Backlog backlog = backlogMapper.toEntity(backlogDTO);
        Backlog savedBacklog = backlogRepository.save(backlog);
        auditUtil.logAudit("CREATE", "Created Backlog with details: " + savedBacklog.toString());
        return backlogMapper.toDTO(savedBacklog);
    }

    @PreAuthorize("hasAuthority('MANAGE_TASKS') and hasAuthority('VIEW_PROJECT')")
    public BacklogDTO updateBacklog(Integer id, BacklogDTO updatedBacklogDTO) {
        if (updatedBacklogDTO.projetId() == null) {
            throw new IllegalArgumentException("Project ID must not be null");
        }

        Backlog existingBacklog = backlogRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Backlog not found with id: " + id));

        projetService.getProjetById(updatedBacklogDTO.projetId());

        BacklogDTO oldBacklogDTO = backlogMapper.toDTO(existingBacklog);

        backlogMapper.updateBacklogFromDTO(updatedBacklogDTO, existingBacklog);
        Backlog savedBacklog = backlogRepository.save(existingBacklog);
        BacklogDTO newBacklogDTO = backlogMapper.toDTO(savedBacklog);
        auditUtil.logAudit("UPDATE", "Updated Backlog with ID: " + id + " from details: " + oldBacklogDTO.toString() + " to details: " + newBacklogDTO.toString());
        return newBacklogDTO;
    }

    @PreAuthorize("hasAuthority('MANAGE_TASKS')")
    public void deleteBacklogById(Integer id) {
        Backlog backlog = backlogRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Backlog not found with id: " + id));

        backlogRepository.delete(backlog);
        auditUtil.logAudit("DELETE", "Deleted Backlog with ID: " + id);
    }
}
