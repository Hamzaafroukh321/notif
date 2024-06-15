package com.example.managementsystem.controllers;

import com.example.managementsystem.DTO.BacklogDTO;
import com.example.managementsystem.services.BacklogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/backlogs")
public class BacklogController {
    private final BacklogService backlogService;

    @Autowired
    public BacklogController(BacklogService backlogService) {
        this.backlogService = backlogService;
    }

    @GetMapping
    public Page<BacklogDTO> getAllBacklogs(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return backlogService.getAllBacklogs(pageable);
    }

    @GetMapping("/project/{projectId}")
    public Page<BacklogDTO> getBacklogsByProjectId(@PathVariable Long projectId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return backlogService.getBacklogsByProjectId(projectId, pageable);
    }

    @GetMapping("/{id}")
    public BacklogDTO getBacklogById(@PathVariable Integer id) {
        return backlogService.getBacklogById(id);
    }

    @PostMapping
    public BacklogDTO createBacklog(@RequestBody BacklogDTO backlogDTO) {
        return backlogService.createBacklog(backlogDTO);
    }

    @PutMapping("/{id}")
    public BacklogDTO updateBacklog(@PathVariable Integer id, @RequestBody BacklogDTO backlogDTO) {
        return backlogService.updateBacklog(id, backlogDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteBacklog(@PathVariable Integer id) {
        backlogService.deleteBacklogById(id);
    }
}