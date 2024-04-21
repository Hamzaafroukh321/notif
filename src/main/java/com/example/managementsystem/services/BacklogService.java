package com.example.managementsystem.services;

import com.example.managementsystem.DTO.BacklogDTO;
import com.example.managementsystem.exceptions.NotFoundException;
import com.example.managementsystem.mappers.BacklogMapper;
import com.example.managementsystem.models.entities.Backlog;
import com.example.managementsystem.repositories.BacklogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BacklogService {
    private final BacklogRepository backlogRepository;
    private final BacklogMapper backlogMapper;
    private final ProjetService projetService;

    @Autowired
    public BacklogService(BacklogRepository backlogRepository, BacklogMapper backlogMapper, ProjetService projetService) {
        this.backlogRepository = backlogRepository;
        this.backlogMapper = backlogMapper;
        this.projetService = projetService;
    }
    // Get all backlogs

    public List<BacklogDTO> getAllBacklogs() {
        List<Backlog> backlogs = backlogRepository.findAll();
        return backlogMapper.toDTOs(backlogs);
    }
    public BacklogDTO getBacklogById(Integer id) {
        Backlog backlog = backlogRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Backlog not found with id: " + id));
        return backlogMapper.toDTO(backlog);
    }

    public BacklogDTO createBacklog(BacklogDTO backlogDTO) {
        // Vérifier si le projet existe
        projetService.getProjetById(backlogDTO.projetId());

        Backlog backlog = backlogMapper.toEntity(backlogDTO);
        Backlog savedBacklog = backlogRepository.save(backlog);
        return backlogMapper.toDTO(savedBacklog);
    }

    public BacklogDTO updateBacklog(Integer id, BacklogDTO updatedBacklogDTO) {
        Backlog existingBacklog = backlogRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Backlog not found with id: " + id));

        // Vérifier si le projet existe
        projetService.getProjetById(updatedBacklogDTO.projetId());

        backlogMapper.updateBacklogFromDTO(updatedBacklogDTO, existingBacklog);
        Backlog savedBacklog = backlogRepository.save(existingBacklog);
        return backlogMapper.toDTO(savedBacklog);
    }

    public void deleteBacklogById(Integer id) {
        Backlog backlog = backlogRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Backlog not found with id: " + id));

        backlogRepository.delete(backlog);
    }
}