package com.example.managementsystem.services;

import com.example.managementsystem.models.Backlog;
import com.example.managementsystem.repositories.BacklogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BacklogService {

    private final BacklogRepository backlogRepository;

    @Autowired
    public BacklogService(BacklogRepository backlogRepository) {
        this.backlogRepository = backlogRepository;
    }

    public Backlog createBacklog(Backlog backlog) {
        return backlogRepository.save(backlog);
    }

    public Backlog getBacklogById(Integer id) {
        Optional<Backlog> backlog = backlogRepository.findById(id);
        return backlog.orElse(null);
    }

    public Backlog updateBacklog(Integer id, Backlog updatedBacklog) {
        Optional<Backlog> optionalBacklog = backlogRepository.findById(id);
        if (optionalBacklog.isPresent()) {
            Backlog existingBacklog = optionalBacklog.get();
            existingBacklog.setTitre(updatedBacklog.getTitre());
            existingBacklog.setDescription(updatedBacklog.getDescription());
            existingBacklog.setEtat(updatedBacklog.getEtat());
            existingBacklog.setProjet(updatedBacklog.getProjet());
            existingBacklog.setUserStories(updatedBacklog.getUserStories());
            return backlogRepository.save(existingBacklog);
        }
        return null;
    }

    public void deleteBacklog(Integer id) {
        backlogRepository.deleteById(id);
    }

    public List<Backlog> getAllBacklogs() {
        return backlogRepository.findAll();
    }
}