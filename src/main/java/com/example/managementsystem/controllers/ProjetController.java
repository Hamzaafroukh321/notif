package com.example.managementsystem.controllers;

import com.example.managementsystem.DTO.ProjetDTO;
import com.example.managementsystem.DTO.TaskDTO;
import com.example.managementsystem.DTO.UserDTO;
import com.example.managementsystem.models.entities.Task;
import com.example.managementsystem.services.ProjetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/projets")
public class ProjetController {
    private final ProjetService projetService;

    @Autowired
    public ProjetController(ProjetService projetService) {
        this.projetService = projetService;
    }

    @GetMapping
    public List<ProjetDTO> getAllProjets() {
        return projetService.getAllProjets();
    }

    @GetMapping("/{id}")
    public ProjetDTO getProjetById(@PathVariable Long id) {
        return projetService.getProjetById(id);
    }

    @PutMapping
    public ProjetDTO createProjet(@RequestBody ProjetDTO projetDTO) {
        return projetService.createProjet(projetDTO);
    }

    @PutMapping("/{id}/partial")
    public ProjetDTO updateProjetPartially(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        return projetService.updateProjetPartially(id, updates);
    }

    @GetMapping("/chef/{chefMatricule}")
    public ResponseEntity<List<ProjetDTO>> getProjetsByChefMatricule(@PathVariable Long chefMatricule) {
        List<ProjetDTO> projets = projetService.getProjetsByChefMatricule(chefMatricule);
        return ResponseEntity.ok(projets);
    }
    @GetMapping("/{projetId}/team-members")
    public ResponseEntity<List<UserDTO>> getTeamMembersByProjetId(@PathVariable Long projetId) {
        List<UserDTO> teamMembers = projetService.getTeamMembersByProjetId(projetId);
        return ResponseEntity.ok(teamMembers);
    }

    @GetMapping("/{projetId}/tasks")
    public ResponseEntity<List<TaskDTO>> getTasksByProjetId(@PathVariable Long projetId) {
        List<TaskDTO> tasks = projetService.getTasksByProjetId(projetId);
        return ResponseEntity.ok(tasks);
    }

}