package com.example.managementsystem.services;

import com.example.managementsystem.DTO.CongeDTO;

import com.example.managementsystem.DTO.UserDTO;
import com.example.managementsystem.exceptions.BadRequestException;
import com.example.managementsystem.exceptions.NotFoundException;
import com.example.managementsystem.models.enums.CongeStatus;
import com.example.managementsystem.models.entities.Congees;
import com.example.managementsystem.notification.Notification;
import com.example.managementsystem.models.entities.User;
import com.example.managementsystem.notification.NotificationService;
import com.example.managementsystem.repositories.CongeesRepository;
import com.example.managementsystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.managementsystem.mappers.CongeMapper;


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

    @Autowired
    public CongeesService(CongeesRepository congeesRepository, CongeMapper congeMapper,
                          UserService userService, NotificationService notificationService, UserRepository userRepository) {
        this.congeesRepository = congeesRepository;
        this.congeMapper = congeMapper;
        this.userService = userService;
        this.notificationService = notificationService;
        this.userRepository = userRepository;
    }


    @PreAuthorize("hasAnyAuthority('REQUEST_LEAVE')")
    public CongeDTO createCongees(CongeDTO congeDTO) {
        User remplacant = userService.getUserEntityByMatricule(congeDTO.remplacantMatricule());
        if (remplacant.getMatricule() == null) {
            remplacant = userRepository.save(remplacant);
        }
        Congees congees = congeMapper.toEntity(congeDTO);
        congees.setRemplacant(remplacant);
        congees.setStatus(CongeStatus.PENDING);
        Congees savedCongees = congeesRepository.save(congees);
        return congeMapper.toDTO(savedCongees);
    }
    @PreAuthorize("hasAuthority('APPROVE_LEAVE')")
    public CongeDTO approveCongees(Long congeesId) {
        Congees congees = congeesRepository.findById(congeesId)
                .orElseThrow(() -> new NotFoundException("Congees request not found."));

        if (congees.getStatus() != CongeStatus.PENDING) {
            throw new BadRequestException("Congees request is not in PENDING status.");
        }

        if (congees.getRemplacant() == null) {
            throw new BadRequestException("Remplacant is not assigned for the congees request.");
        }

        congees.setStatus(CongeStatus.APPROVED);
        Congees updatedCongees = congeesRepository.save(congees);

        sendNotification(congees.getRequestedBy(), "Congees request approved.");

        return congeMapper.toDTO(updatedCongees);
    }

    @PreAuthorize("hasAuthority('APPROVE_LEAVE')")
    public CongeDTO rejectCongees(Long congeesId, String motif) {
        Congees congees = congeesRepository.findById(congeesId)
                .orElseThrow(() -> new NotFoundException("Congees request not found."));

        if (congees.getStatus() != CongeStatus.PENDING) {
            throw new BadRequestException("Congees request is not in PENDING status.");
        }

        congees.setStatus(CongeStatus.REJECTED);
        Congees updatedCongees = congeesRepository.save(congees);

        sendNotification(congees.getRequestedBy(), "Congees request rejected. Motif: " + motif);

        return congeMapper.toDTO(updatedCongees);
    }

    private void sendNotification(User user, String message) {
        UserDTO userDTO = userService.getUserByMatricule(user.getMatricule());

        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setRecipient(userDTO.email());

        notificationService.sendNotification(notification);
    }

    @PreAuthorize("hasAnyAuthority('REQUEST_LEAVE', 'APPROVE_LEAVE')")
    public CongeDTO getCongeesById(Long id) {
        Congees congees = congeesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Congees request not found."));
        return congeMapper.toDTO(congees);
    }

    @PreAuthorize("hasAnyAuthority('REQUEST_LEAVE', 'APPROVE_LEAVE')")
    public CongeDTO updateCongees(Long id, CongeDTO updatedCongeDTO) {
        Congees existingCongees = congeesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Congees request not found."));

        congeMapper.updateCongeesFromDTO(updatedCongeDTO, existingCongees);

        Congees savedCongees = congeesRepository.save(existingCongees);
        return congeMapper.toDTO(savedCongees);
    }

    @PreAuthorize("hasAnyAuthority('REQUEST_LEAVE', 'APPROVE_LEAVE')")
    public void deleteCongees(Long id) {
        Congees congees = congeesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Congees request not found."));

        congeesRepository.delete(congees);
    }

    @PreAuthorize("hasAnyAuthority('REQUEST_LEAVE', 'APPROVE_LEAVE')")
    public List<CongeDTO> getAllCongees() {
        List<Congees> congeesList = congeesRepository.findAll();
        return congeesList.stream()
                .map(congeMapper::toDTO)
                .collect(Collectors.toList());
    }
}