package com.example.managementsystem.controllers;

import com.example.managementsystem.models.Congees;
import com.example.managementsystem.services.CongeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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


    @PreAuthorize("hasRole('MANAGER') or hasRole('TEAM_MEMBER')")
    @PostMapping("/requests")
    public Congees createCongees(@RequestBody Congees congees) {
        return congeesService.createCongees(congees);
    }
    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/requests/{congeesId}/approve")
    public ResponseEntity<Congees> approveCongees(@PathVariable Long congeesId) {
        Congees approvedCongees = congeesService.approveCongees(congeesId);
        return ResponseEntity.ok(approvedCongees);
    }
    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/requests/{congeesId}/reject")
    public ResponseEntity<Congees> rejectCongees(@PathVariable Long congeesId, @RequestBody String motif) {
        Congees rejectedCongees = congeesService.rejectCongees(congeesId, motif);
        return ResponseEntity.ok(rejectedCongees);
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