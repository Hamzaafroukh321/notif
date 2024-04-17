package com.example.managementsystem.services;

import com.example.managementsystem.exceptions.BadRequestException;
import com.example.managementsystem.exceptions.NotFoundException;
import com.example.managementsystem.models.Sprint;
import com.example.managementsystem.repositories.SprintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SprintService {

    private final SprintRepository sprintRepository;

    @Autowired
    public SprintService(SprintRepository sprintRepository) {
        this.sprintRepository = sprintRepository;
    }

    public List<Sprint> getAllSprints() {
        return sprintRepository.findAll();
    }

    public Sprint getSprintById(Long id) {
        return sprintRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Sprint non trouvé avec l'ID : " + id));
    }

    public Sprint createSprint(Sprint sprint) {
        if (sprint.getNom() == null || sprint.getDateDebut() == null || sprint.getDateFin() == null || sprint.getProjet() == null) {
            throw new BadRequestException("Les informations du sprint sont incomplètes.");
        }
        return sprintRepository.save(sprint);
    }

    public Sprint updateSprint(Long id, Sprint sprintDetails) {
        Sprint sprint = getSprintById(id);
        sprint.setNom(sprintDetails.getNom());
        sprint.setDateDebut(sprintDetails.getDateDebut());
        sprint.setDateFin(sprintDetails.getDateFin());
        sprint.setProjet(sprintDetails.getProjet());
        return sprintRepository.save(sprint);
    }

    public void deleteSprint(Long id) {
        Sprint sprint = getSprintById(id);
        sprintRepository.delete(sprint);
    }
}