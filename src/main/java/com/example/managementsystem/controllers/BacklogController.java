package com.example.managementsystem.controllers;

import com.example.managementsystem.models.Backlog;
import com.example.managementsystem.services.BacklogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/backlogs")
public class BacklogController {

    private final BacklogService backlogService;

    @Autowired
    public BacklogController(BacklogService backlogService) {
        this.backlogService = backlogService;
    }

    @PostMapping
    public Backlog createBacklog(@RequestBody Backlog backlog) {
        return backlogService.createBacklog(backlog);
    }

    @GetMapping("/{id}")
    public Backlog getBacklogById(@PathVariable Integer id) {
        return backlogService.getBacklogById(id);
    }

    @PutMapping("/{id}")
    public Backlog updateBacklog(@PathVariable Integer id, @RequestBody Backlog updatedBacklog) {
        return backlogService.updateBacklog(id, updatedBacklog);
    }

    @DeleteMapping("/{id}")
    public void deleteBacklog(@PathVariable Integer id) {
        backlogService.deleteBacklog(id);
    }

    @GetMapping
    public List<Backlog> getAllBacklogs() {
        return backlogService.getAllBacklogs();
    }
}