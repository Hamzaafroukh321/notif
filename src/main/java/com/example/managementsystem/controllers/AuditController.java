package com.example.managementsystem.controllers;

import com.example.managementsystem.DTO.AuditDTO;
import com.example.managementsystem.services.AuditService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/audits")
public class AuditController {
    private final AuditService auditService;

    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    @GetMapping("/day/{date}")
    public ResponseEntity<List<AuditDTO>> getAuditsByDay(@PathVariable String date) {
        LocalDate day = LocalDate.parse(date);
        List<AuditDTO> audits = auditService.getAuditsByDay(day);
        return ResponseEntity.ok(audits);
    }

    @GetMapping("/week/{date}")
    public ResponseEntity<List<AuditDTO>> getAuditsByWeek(@PathVariable String date) {
        LocalDate week = LocalDate.parse(date);
        List<AuditDTO> audits = auditService.getAuditsByWeek(week);
        return ResponseEntity.ok(audits);
    }

    @GetMapping("/month/{date}")
    public ResponseEntity<List<AuditDTO>> getAuditsByMonth(@PathVariable String date) {
        LocalDate month = LocalDate.parse(date);
        List<AuditDTO> audits = auditService.getAuditsByMonth(month);
        return ResponseEntity.ok(audits);
    }
}
