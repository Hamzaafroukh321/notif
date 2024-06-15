package com.example.managementsystem.services;

import com.example.managementsystem.DTO.AuditDTO;
import com.example.managementsystem.mappers.AuditMapper;
import com.example.managementsystem.models.entities.Audit;
import com.example.managementsystem.repositories.AuditRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuditService {
    private final AuditRepository auditRepository;
    private final AuditMapper auditMapper;

    public AuditService(AuditRepository auditRepository, AuditMapper auditMapper) {
        this.auditRepository = auditRepository;
        this.auditMapper = auditMapper;
    }

    public Page<AuditDTO> getAuditsByDay(LocalDate day, int page, int size) {
        System.out.println("getAuditsByDay called with day: " + day);  // Ligne de débogage
        LocalDateTime startOfDay = day.atStartOfDay();
        LocalDateTime endOfDay = day.atTime(23, 59, 59);
        Pageable pageable = PageRequest.of(page, size);

        Page<Audit> audits = auditRepository.findByTimestampBetween(startOfDay, endOfDay, pageable);
        return audits.map(auditMapper::auditToAuditDTO);
    }

    public Page<AuditDTO> getAuditsByWeek(LocalDate week, int page, int size) {
        System.out.println("getAuditsByWeek called with week: " + week);  // Ligne de débogage
        LocalDate startOfWeek = week.minusDays(week.getDayOfWeek().getValue() - 1);
        LocalDate endOfWeek = startOfWeek.plusDays(6);

        LocalDateTime startOfDay = startOfWeek.atStartOfDay();
        LocalDateTime endOfDay = endOfWeek.atTime(23, 59, 59);
        Pageable pageable = PageRequest.of(page, size);

        Page<Audit> audits = auditRepository.findByTimestampBetween(startOfDay, endOfDay, pageable);
        return audits.map(auditMapper::auditToAuditDTO);
    }

    public Page<AuditDTO> getAuditsByMonth(LocalDate month, int page, int size) {
        System.out.println("getAuditsByMonth called with month: " + month);  // Ligne de débogage
        LocalDate firstDayOfMonth = month.withDayOfMonth(1);
        LocalDate lastDayOfMonth = firstDayOfMonth.plusMonths(1).minusDays(1);

        LocalDateTime startOfDay = firstDayOfMonth.atStartOfDay();
        LocalDateTime endOfDay = lastDayOfMonth.atTime(23, 59, 59);
        Pageable pageable = PageRequest.of(page, size);

        Page<Audit> audits = auditRepository.findByTimestampBetween(startOfDay, endOfDay, pageable);
        return audits.map(auditMapper::auditToAuditDTO);
    }

    @PreAuthorize("hasAuthority('VIEW_USERS')")
    public void saveAudit(AuditDTO auditDTO) {
        System.out.println("saveAudit called with auditDTO: " + auditDTO);  // Ligne de débogage
        Audit audit = auditMapper.auditDTOToAudit(auditDTO);
        auditRepository.save(audit);
    }

    public Page<AuditDTO> getAuditsByDateRange(LocalDate startDate, LocalDate endDate, int page, int size) {
        System.out.println("getAuditsByDateRange called with startDate: " + startDate + ", endDate: " + endDate);  // Ligne de débogage
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);
        Pageable pageable = PageRequest.of(page, size);

        Page<Audit> audits = auditRepository.findByTimestampBetween(startDateTime, endDateTime, pageable);
        return audits.map(auditMapper::auditToAuditDTO);
    }
}
