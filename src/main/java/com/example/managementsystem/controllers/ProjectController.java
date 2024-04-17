package com.example.managementsystem.controllers;

import com.example.managementsystem.exceptions.BadRequestException;
import com.example.managementsystem.models.Projet;
import com.example.managementsystem.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }


    @PostMapping("/projects")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Projet> createProject(@RequestBody Projet projet) {
        try {
            Projet createdProjet = projectService.createProject(projet);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProjet);
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/{id}")
    public Projet getProjectById(@PathVariable Long id) {
        return projectService.getProjectById(id);
    }

    @PutMapping("/{id}")
    public Projet updateProject(@PathVariable Long id, @RequestBody Projet updatedProject) {
        return projectService.updateProject(id, updatedProject);
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
    }

    @GetMapping
    public List<Projet> getAllProjects() {
        return projectService.getAllProjects();
    }
}