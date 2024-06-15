package com.example.managementsystem.controllers;

import com.example.managementsystem.DTO.CongeDTO;
import com.example.managementsystem.services.CongeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public Page<CongeDTO> getAllCongees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort) {

        Sort.Direction direction = Sort.Direction.fromString(sort[1]);
        Sort.Order order = new Sort.Order(direction, sort[0]);
        Pageable pageable = PageRequest.of(page, size, Sort.by(order));

        return congeesService.getAllCongees(pageable);
    }

    @GetMapping("/{id}")
    public CongeDTO getCongeesById(@PathVariable Long id) {
        return congeesService.getCongeesById(id);
    }

    @GetMapping("/user/{matricule}")
    public List<CongeDTO> getCongeesByMatricule(@PathVariable Long matricule) {
        return congeesService.getCongeesByMatricule(matricule);
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

    @PostMapping("/{id}/approve")
    public ResponseEntity<CongeDTO> approveCongees(@PathVariable Long id, @RequestParam Long approverMatricule) {
        CongeDTO congeDTO = congeesService.approveCongees(id, approverMatricule);
        return ResponseEntity.ok(congeDTO);
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<CongeDTO> rejectCongees(@PathVariable Long id, @RequestParam Long approverMatricule, @RequestParam String motif) {
        CongeDTO congeDTO = congeesService.rejectCongees(id, approverMatricule, motif);
        return ResponseEntity.ok(congeDTO);
    }
    @PreAuthorize("hasAuthority('MANAGE_REQUEST_LEAVE')")
    @GetMapping("/requestedBy/{matricule}")
    public ResponseEntity<List<CongeDTO>> getCongeesByRequestedByMatricule(@PathVariable Long matricule) {
        List<CongeDTO> congees = congeesService.getCongeesByRequestedByMatricule(matricule);
        return ResponseEntity.ok(congees);
    }


}
