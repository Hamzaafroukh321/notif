package com.example.managementsystem.config;

import com.example.managementsystem.DTO.AuditDTO;
import com.example.managementsystem.models.entities.User;
import com.example.managementsystem.services.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AuditUtil {

    private final AuditService auditService;

    @Autowired
    public AuditUtil(AuditService auditService) {
        this.auditService = auditService;
    }

    public void logAudit(String action, String description) {
        String matricule = getCurrentUserMatricule();
        AuditDTO auditDTO = new AuditDTO();
        auditDTO.setTimestamp(LocalDateTime.now());
        auditDTO.setAction(action);
        auditDTO.setDescription(description + " performed by user with matricule: " + matricule);
        auditService.saveAudit(auditDTO);
    }

    private String getCurrentUserMatricule() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            return ((User) principal).getMatricule().toString();
        } else {
            // Handle case where principal is not an instance of UserDetails
            return "unknown";
        }
    }
}
