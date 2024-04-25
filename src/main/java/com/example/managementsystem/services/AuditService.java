package com.example.managementsystem.services;

import com.example.managementsystem.DTO.AuditDTO;
import com.example.managementsystem.mappers.AuditMapper;
import com.example.managementsystem.models.entities.Audit;
import com.example.managementsystem.repositories.AuditRepository;
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

    public List<AuditDTO> getAuditsByDay(LocalDate day) {
        LocalDateTime startOfDay = day.atStartOfDay();
        LocalDateTime endOfDay = day.atTime(23, 59, 59);

        List<Audit> audits = auditRepository.findByTimestampBetween(startOfDay, endOfDay);
        return audits.stream()
                .map(auditMapper::auditToAuditDTO)
                .collect(Collectors.toList());
    }

    public List<AuditDTO> getAuditsByWeek(LocalDate week) {
        LocalDate startOfWeek = week.minusDays(week.getDayOfWeek().getValue() - 1);
        LocalDate endOfWeek = startOfWeek.plusDays(6);

        LocalDateTime startOfDay = startOfWeek.atStartOfDay();
        LocalDateTime endOfDay = endOfWeek.atTime(23, 59, 59);

        List<Audit> audits = auditRepository.findByTimestampBetween(startOfDay, endOfDay);
        return audits.stream()
                .map(auditMapper::auditToAuditDTO)
                .collect(Collectors.toList());
    }

    public List<AuditDTO> getAuditsByMonth(LocalDate month) {
        LocalDate firstDayOfMonth = month.withDayOfMonth(1);
        LocalDate lastDayOfMonth = firstDayOfMonth.plusMonths(1).minusDays(1);

        LocalDateTime startOfDay = firstDayOfMonth.atStartOfDay();
        LocalDateTime endOfDay = lastDayOfMonth.atTime(23, 59, 59);

        List<Audit> audits = auditRepository.findByTimestampBetween(startOfDay, endOfDay);
        return audits.stream()
                .map(auditMapper::auditToAuditDTO)
                .collect(Collectors.toList());
    }

    public void saveAudit(AuditDTO auditDTO) {
        Audit audit = auditMapper.auditDTOToAudit(auditDTO);
        auditRepository.save(audit);
    }
}
