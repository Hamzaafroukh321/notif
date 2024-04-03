package com.example.managementsystem.controllers;

import com.example.managementsystem.models.Projet;
import com.example.managementsystem.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping
    public Projet createProject(@RequestBody Projet projet) {
        return projectService.createProject(projet);
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