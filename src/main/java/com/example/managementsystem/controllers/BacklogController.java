package com.example.managementsystem.controllers;

import com.example.managementsystem.models.Backlog;
import com.example.managementsystem.services.BacklogService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/backlogs")
public class BacklogController {
    private final BacklogService backlogService;

    public BacklogController(BacklogService backlogService) {
        this.backlogService = backlogService;
    }

    @GetMapping
    @PreAuthorize("hasRole('PROJECT_MANAGER')")
    public List<Backlog> getAllBacklogs() {
        return backlogService.getAllBacklogs();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('PROJECT_MANAGER')")
    public Backlog getBacklogById(@PathVariable Integer id) {
        return backlogService.getBacklogById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('PROJECT_MANAGER')")
    public Backlog createBacklog(@RequestBody Backlog backlog) {
        return backlogService.createBacklog(backlog);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('PROJECT_MANAGER')")
    public Backlog updateBacklog(@PathVariable Integer id, @RequestBody Backlog backlogDetails) {
        return backlogService.updateBacklog(id, backlogDetails);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('PROJECT_MANAGER')")
    public void deleteBacklog(@PathVariable Integer id) {
        backlogService.deleteBacklog(id);
    }
}