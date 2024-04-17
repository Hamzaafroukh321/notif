package com.example.managementsystem.services;

import com.example.managementsystem.exceptions.NotFoundException;
import com.example.managementsystem.models.Backlog;
import com.example.managementsystem.repositories.BacklogRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BacklogService {
    private final BacklogRepository backlogRepository;

    public BacklogService(BacklogRepository backlogRepository) {
        this.backlogRepository = backlogRepository;
    }

    public List<Backlog> getAllBacklogs() {
        return backlogRepository.findAll();
    }

    public Backlog getBacklogById(Integer id) {
        return backlogRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Backlog non trouvé avec l'ID : " + id));
    }

    public Backlog createBacklog(Backlog backlog) {
        if (backlog.getTitre() == null || backlog.getTitre().isEmpty()) {
            throw new NotFoundException("Le titre du backlog est obligatoire");
        }
        return backlogRepository.save(backlog);
    }

    public Backlog updateBacklog(Integer id, Backlog backlogDetails) {
        Backlog backlog = getBacklogById(id);
        if (backlogDetails.getTitre() == null || backlogDetails.getTitre().isEmpty()) {
            throw new NotFoundException("Le titre du backlog est obligatoire");
        }
        backlog.setTitre(backlogDetails.getTitre());
        backlog.setDescription(backlogDetails.getDescription());
        backlog.setEtat(backlogDetails.getEtat());
        return backlogRepository.save(backlog);
    }

    public void deleteBacklog(Integer id) {
        Backlog backlog = getBacklogById(id);
        backlogRepository.delete(backlog);
    }
}