package com.example.managementsystem.controllers;

import com.example.managementsystem.models.Congees;
import com.example.managementsystem.services.CongeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/congees")
public class CongeesController {

    private final CongeesService congeesService;

    @Autowired
    public CongeesController(CongeesService congeesService) {
        this.congeesService = congeesService;
    }

    @PostMapping
    public Congees createCongees(@RequestBody Congees congees) {
        return congeesService.createCongees(congees);
    }

    @GetMapping("/{id}")
    public Congees getCongeesById(@PathVariable Long id) {
        return congeesService.getCongeesById(id);
    }

    @PutMapping("/{id}")
    public Congees updateCongees(@PathVariable Long id, @RequestBody Congees updatedCongees) {
        return congeesService.updateCongees(id, updatedCongees);
    }

    @DeleteMapping("/{id}")
    public void deleteCongees(@PathVariable Long id) {
        congeesService.deleteCongees(id);
    }

    @GetMapping
    public List<Congees> getAllCongees() {
        return congeesService.getAllCongees();
    }
}