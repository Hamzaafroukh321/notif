package com.example.managementsystem.controllers;

import com.example.managementsystem.DTO.AuditDTO;
import com.example.managementsystem.services.AuditService;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
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
    public ResponseEntity<Page<AuditDTO>> getAuditsByDay(
            @PathVariable String date,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        LocalDate day = LocalDate.parse(date);
        Page<AuditDTO> audits = auditService.getAuditsByDay(day, page, size);
        return ResponseEntity.ok(audits);
    }

    @GetMapping("/week/{date}")
    public ResponseEntity<Page<AuditDTO>> getAuditsByWeek(
            @PathVariable String date,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        LocalDate week = LocalDate.parse(date);
        Page<AuditDTO> audits = auditService.getAuditsByWeek(week, page, size);
        return ResponseEntity.ok(audits);
    }

    @GetMapping("/month/{date}")
    public ResponseEntity<Page<AuditDTO>> getAuditsByMonth(
            @PathVariable String date,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        LocalDate month = LocalDate.parse(date);
        Page<AuditDTO> audits = auditService.getAuditsByMonth(month, page, size);
        return ResponseEntity.ok(audits);
    }

    @GetMapping("/range")
    public ResponseEntity<Page<AuditDTO>> getAuditsByDateRange(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<AuditDTO> audits = auditService.getAuditsByDateRange(startDate, endDate, page, size);
        return ResponseEntity.ok(audits);
    }
}