package com.example.managementsystem.controllers;

import com.example.managementsystem.models.Sprint;
import com.example.managementsystem.services.SprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sprints")
public class SprintController {
    private final SprintService sprintService;

    public SprintController(SprintService sprintService) {
        this.sprintService = sprintService;
    }

    @GetMapping
    @PreAuthorize("hasRole('PROJECT_MANAGER')")
    public List<Sprint> getAllSprints() {
        return sprintService.getAllSprints();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('PROJECT_MANAGER')")
    public Sprint getSprintById(@PathVariable Long id) {
        return sprintService.getSprintById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('PROJECT_MANAGER')")
    public Sprint createSprint(@RequestBody Sprint sprint) {
        return sprintService.createSprint(sprint);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('PROJECT_MANAGER')")
    public Sprint updateSprint(@PathVariable Long id, @RequestBody Sprint sprintDetails) {
        return sprintService.updateSprint(id, sprintDetails);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('PROJECT_MANAGER')")
    public void deleteSprint(@PathVariable Long id) {
        sprintService.deleteSprint(id);
    }
}