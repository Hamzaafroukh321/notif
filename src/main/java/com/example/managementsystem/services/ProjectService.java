package com.example.managementsystem.services;

import com.example.managementsystem.controllers.NotificationController;
import com.example.managementsystem.exceptions.BadRequestException;
import com.example.managementsystem.models.Notification;
import com.example.managementsystem.models.Projet;
import com.example.managementsystem.models.User;
import com.example.managementsystem.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final NotificationController notificationController;


    @Autowired
    public ProjectService(ProjectRepository projectRepository, NotificationController notificationController) {
        this.projectRepository = projectRepository;
        this.notificationController = notificationController;
    }

    @PreAuthorize("hasRole('MANAGER')")
    public Projet createProject(Projet projet) {
        if (projet.getChef() == null || projet.getTeamMembers() == null || projet.getTeamMembers().isEmpty()) {
            throw new BadRequestException("L'équipe et le chef de projet doivent être définis.");
        }
        if (projet.getNom() == null || projet.getDescription() == null || projet.getDateDebut() == null || projet.getDateFin() == null || projet.getMode() == null) {
            throw new BadRequestException("Les informations du projet sont incomplètes.");
        }
        Projet savedProjet = projectRepository.save(projet);
        notifyTeamsAboutNewProject(savedProjet);
        return savedProjet;
    }

    private void notifyTeamsAboutNewProject(Projet projet) {

        List<User> teamMembers = projet.getTeamMembers();
        for (User member : teamMembers) {
            Notification notification = new Notification();
            notification.setMessage("Un nouveau projet a été créé : " + projet.getNom());
            notification.setTimestamp(LocalDateTime.now());
            notification.setRecipient(member.getEmail());
            notificationController.sendNotification(notification);
        }
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