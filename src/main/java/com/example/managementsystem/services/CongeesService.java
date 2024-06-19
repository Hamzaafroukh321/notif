package com.example.managementsystem.services;

import com.example.managementsystem.DTO.CongeDTO;

import com.example.managementsystem.DTO.UserDTO;
import com.example.managementsystem.config.AuditUtil;
import com.example.managementsystem.exceptions.BadRequestException;
import com.example.managementsystem.exceptions.NotFoundException;
import com.example.managementsystem.models.enums.CongeStatus;
import com.example.managementsystem.models.entities.Congees;
import com.example.managementsystem.notification.Notification;
import com.example.managementsystem.models.entities.User;
import com.example.managementsystem.notification.NotificationService;
import com.example.managementsystem.repositories.CongeesRepository;
import com.example.managementsystem.repositories.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.managementsystem.mappers.CongeMapper;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CongeesService {
    private final CongeesRepository congeesRepository;
    private final CongeMapper congeMapper;
    private final UserService userService;
    private final NotificationService notificationService;
    private final UserRepository userRepository;

    private  final AuditUtil auditUtil;

    @Autowired
    public CongeesService(CongeesRepository congeesRepository, CongeMapper congeMapper,
                          UserService userService, NotificationService notificationService, UserRepository userRepository, AuditUtil auditUtil) {
        this.congeesRepository = congeesRepository;
        this.congeMapper = congeMapper;
        this.userService = userService;
        this.notificationService = notificationService;
        this.userRepository = userRepository;
        this.auditUtil = auditUtil;
    }


    @PreAuthorize("hasAnyAuthority('REQUEST_LEAVE')")
    public CongeDTO createCongees(CongeDTO congeDTO) {
        User remplacant = userService.getUserEntityByMatricule(congeDTO.remplacantMatricule());
        User requestedBy = userService.getUserEntityByMatricule(congeDTO.requestedByMatricule());

        Congees congees = congeMapper.toEntity(congeDTO);
        congees.setRemplacant(remplacant);
        congees.setRequestedBy(requestedBy);
        congees.setStatus(CongeStatus.PENDING);

        Congees savedCongees = congeesRepository.save(congees);

        auditUtil.logAudit("CREATE", "Created Congees with details: " + savedCongees.toString());

        return congeMapper.toDTO(savedCongees);
    }

    @PreAuthorize("hasAuthority('MANAGE_REQUEST_LEAVE')")
    public CongeDTO approveCongees(Long congeesId, Long approverMatricule) {
        Congees congees = congeesRepository.findById(congeesId)
                .orElseThrow(() -> new NotFoundException("Congees request not found."));

        if (congees.getStatus() != CongeStatus.PENDING) {
            throw new BadRequestException("Congees request is not in PENDING status.");
        }

        if (congees.getRemplacant() == null) {
            throw new BadRequestException("Remplacant is not assigned for the congees request.");
        }

        User approver = userService.getUserEntityByMatricule(approverMatricule);
        congees.setStatus(CongeStatus.APPROVED);
        congees.setApprovedOrRejectedBy(approver);
        Congees updatedCongees = congeesRepository.save(congees);

        auditUtil.logAudit("APPROVE", "Approved Congees with ID: " + congeesId + " by user with matricule: " + approverMatricule);

        sendNotification(congees.getRequestedBy(), "Congees request approved.");

        return congeMapper.toDTO(updatedCongees);
    }

    @PreAuthorize("hasAuthority('MANAGE_REQUEST_LEAVE')")
    public CongeDTO rejectCongees(Long congeesId, Long approverMatricule, String motif) {
        Congees congees = congeesRepository.findById(congeesId)
                .orElseThrow(() -> new NotFoundException("Congees request not found."));

        if (congees.getStatus() != CongeStatus.PENDING) {
            throw new BadRequestException("Congees request is not in PENDING status.");
        }

        User approver = userService.getUserEntityByMatricule(approverMatricule);
        congees.setStatus(CongeStatus.REJECTED);
        congees.setMotif(motif);
        congees.setApprovedOrRejectedBy(approver);
        Congees updatedCongees = congeesRepository.save(congees);

        auditUtil.logAudit("REJECT", "Rejected Congees with ID: " + congeesId + " by user with matricule: " + approverMatricule + ". Motif: " + motif);

        sendNotification(congees.getRequestedBy(), "Congees request rejected. Motif: " + motif);

        return congeMapper.toDTO(updatedCongees);
    }



    @PreAuthorize("hasAuthority('MANAGE_REQUEST_LEAVE')")
    public List<CongeDTO> getCongeesByRequestedByMatricule(Long matricule) {
        List<Congees> congeesList = congeesRepository.findByRequestedByMatricule(matricule);
        return congeesList.stream()
                .map(congeMapper::toDTO)
                .collect(Collectors.toList());
    }


    private void sendNotification(User user, String message) {
        UserDTO userDTO = userService.getUserByMatricule(user.getMatricule());

        String recipientMatricule = userDTO.email(); // Utiliser directement le matricule
        notificationService.sendNotification(message, recipientMatricule);
    }


    public CongeDTO getCongeesById(Long id) {
        Congees congees = congeesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Congees request not found."));
        return congeMapper.toDTO(congees);
    }


    public CongeDTO updateCongees(Long id, CongeDTO updatedCongeDTO) {
        Congees existingCongees = congeesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Congees request not found."));

        congeMapper.updateCongeesFromDTO(updatedCongeDTO, existingCongees);

        Congees savedCongees = congeesRepository.save(existingCongees);
        return congeMapper.toDTO(savedCongees);
    }


    public void deleteCongees(Long id) {
        Congees congees = congeesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Congees request not found."));

        congeesRepository.delete(congees);
    }

    @PreAuthorize("hasAuthority('MANAGE_REQUEST_LEAVE')")
    public Page<CongeDTO> getAllCongees(Pageable pageable) {
        Page<Congees> congeesPage = congeesRepository.findAll(pageable);
        return congeesPage.map(congeMapper::toDTO);
    }


    @PreAuthorize("hasAnyAuthority('REQUEST_LEAVE')")
    public List<CongeDTO> getCongeesByMatricule(Long matricule) {
        User user = userRepository.findByMatricule(matricule)
                .orElseThrow(() -> new NotFoundException("User not found with matricule: " + matricule));

        List<Congees> congeesList = congeesRepository.findByRequestedBy(user);
        return congeesList.stream()
                .map(congeMapper::toDTO)
                .collect(Collectors.toList());
    }
}