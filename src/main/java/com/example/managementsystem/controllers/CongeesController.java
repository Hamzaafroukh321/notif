package com.example.managementsystem.controllers;

import com.example.managementsystem.DTO.CongeDTO;
import com.example.managementsystem.services.CongeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/congees")
public class CongeesController {
    private final CongeesService congeesService;

    @Autowired
    public CongeesController(CongeesService congeesService) {
        this.congeesService = congeesService;
    }

    @GetMapping
    public List<CongeDTO> getAllCongees() {
        return congeesService.getAllCongees();
    }

    @GetMapping("/{id}")
    public CongeDTO getCongeesById(@PathVariable Long id) {
        return congeesService.getCongeesById(id);
    }

    @PostMapping
    public CongeDTO createCongees(@RequestBody CongeDTO congeDTO) {
        return congeesService.createCongees(congeDTO);
    }

    @PutMapping("/{id}")
    public CongeDTO updateCongees(@PathVariable Long id, @RequestBody CongeDTO congeDTO) {
        return congeesService.updateCongees(id, congeDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteCongees(@PathVariable Long id) {
        congeesService.deleteCongees(id);
    }
}