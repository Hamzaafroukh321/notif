package com.example.managementsystem.controllers;

import com.example.managementsystem.DTO.SprintDTO;
import com.example.managementsystem.services.SprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sprints")
public class SprintController {
    private final SprintService sprintService;

    @Autowired
    public SprintController(SprintService sprintService) {
        this.sprintService = sprintService;
    }

    @GetMapping
    public List<SprintDTO> getAllSprints() {
        return sprintService.getAllSprints();
    }

    @GetMapping("/{id}")
    public SprintDTO getSprintById(@PathVariable Long id) {
        return sprintService.getSprintById(id);
    }

    @PostMapping
    public SprintDTO createSprint(@RequestBody SprintDTO sprintDTO) {
        return sprintService.createSprint(sprintDTO);
    }

    @PutMapping("/{id}")
    public SprintDTO updateSprint(@PathVariable Long id, @RequestBody SprintDTO sprintDTO) {
        return sprintService.updateSprint(id, sprintDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteSprint(@PathVariable Long id) {
        sprintService.deleteSprintById(id);
    }

    @GetMapping("/project/{projectId}")
    public Page<SprintDTO> getSprintsByProjetId(@PathVariable Long projectId,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return sprintService.getSprintsByProjetId(projectId, pageable);
    }
}
