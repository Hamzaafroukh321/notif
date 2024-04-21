package com.example.managementsystem.controllers;

import com.example.managementsystem.DTO.ProjetDTO;
import com.example.managementsystem.services.ProjetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping
    public ProjetDTO createProjet(@RequestBody ProjetDTO projetDTO) {
        return projetService.createProjet(projetDTO);
    }

    @PutMapping("/{id}")
    public ProjetDTO updateProjet(@PathVariable Long id, @RequestBody ProjetDTO projetDTO) {
        return projetService.updateProjet(id, projetDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteProjet(@PathVariable Long id) {
        projetService.deleteProjetById(id);
    }
}