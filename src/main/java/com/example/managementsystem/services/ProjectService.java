package com.example.managementsystem.services;

import com.example.managementsystem.models.Projet;
import com.example.managementsystem.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Projet createProject(Projet projet) {
        return projectRepository.save(projet);
    }

    public Projet getProjectById(Long id) {
        Optional<Projet> projet = projectRepository.findById(id);
        return projet.orElse(null);
    }

    public Projet updateProject(Long id, Projet updatedProject) {
        Optional<Projet> optionalProjet = projectRepository.findById(id);
        if (optionalProjet.isPresent()) {
            Projet existingProject = optionalProjet.get();
            existingProject.setNom(updatedProject.getNom());
            existingProject.setDescription(updatedProject.getDescription());
            existingProject.setDateDebut(updatedProject.getDateDebut());
            existingProject.setDateFin(updatedProject.getDateFin());
            existingProject.setMode(updatedProject.getMode());
            existingProject.setStatus(updatedProject.getStatus());
            existingProject.setChef(updatedProject.getChef());
            existingProject.setTeamMembers(updatedProject.getTeamMembers());
            existingProject.setBacklogs(updatedProject.getBacklogs());
            existingProject.setSprints(updatedProject.getSprints());
            return projectRepository.save(existingProject);
        }
        return null;
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    public List<Projet> getAllProjects() {
        return projectRepository.findAll();
    }
}