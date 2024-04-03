package com.example.managementsystem.services;

import com.example.managementsystem.models.Sprint;
import com.example.managementsystem.repositories.SprintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SprintService {

    private final SprintRepository sprintRepository;

    @Autowired
    public SprintService(SprintRepository sprintRepository) {
        this.sprintRepository = sprintRepository;
    }

    public Sprint createSprint(Sprint sprint) {
        return sprintRepository.save(sprint);
    }

    public Sprint getSprintById(Long id) {
        Optional<Sprint> sprint = sprintRepository.findById(id);
        return sprint.orElse(null);
    }

    public Sprint updateSprint(Long id, Sprint updatedSprint) {
        Optional<Sprint> optionalSprint = sprintRepository.findById(id);
        if (optionalSprint.isPresent()) {
            Sprint existingSprint = optionalSprint.get();
            existingSprint.setNom(updatedSprint.getNom());
            existingSprint.setDateDebut(updatedSprint.getDateDebut());
            existingSprint.setDateFin(updatedSprint.getDateFin());
            existingSprint.setProjet(updatedSprint.getProjet());
            existingSprint.setTasks(updatedSprint.getTasks());
            return sprintRepository.save(existingSprint);
        }
        return null;
    }

    public void deleteSprint(Long id) {
        sprintRepository.deleteById(id);
    }

    public List<Sprint> getAllSprints() {
        return sprintRepository.findAll();
    }
}