package com.example.managementsystem.services;

import com.example.managementsystem.controllers.NotificationController;

import com.example.managementsystem.exceptions.BadRequestException;
import com.example.managementsystem.exceptions.NotFoundException;
import com.example.managementsystem.models.CongeStatus;
import com.example.managementsystem.models.Congees;
import com.example.managementsystem.models.Notification;
import com.example.managementsystem.models.User;
import com.example.managementsystem.repositories.CongeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CongeesService {

    private final CongeesRepository congeesRepository;
    private final NotificationController notificationController;


    @Autowired
    public CongeesService(CongeesRepository congeesRepository, NotificationController notificationController) {
        this.congeesRepository = congeesRepository;
        this.notificationController = notificationController;
    }

    public Congees createCongees(Congees congees) {
        congees.setStatus(CongeStatus.PENDING);
        Congees savedCongees = congeesRepository.save(congees);
        return savedCongees;
    }


    public Congees approveCongees(Long congeesId) {
        Optional<Congees> optionalCongees = congeesRepository.findById(congeesId);
        if (optionalCongees.isPresent()) {
            Congees congees = optionalCongees.get();
            if (congees.getStatus() != CongeStatus.PENDING) {
                throw new BadRequestException("Congees request is not in PENDING status.");
            }
            if (congees.getRemplacant() == null) {
                throw new BadRequestException("Remplacant is not assigned for the congees request.");
            }
            congees.setStatus(CongeStatus.APPROVED);
            Congees updatedCongees = congeesRepository.save(congees);
            sendNotification(congees.getRequestedBy(), "Congees request approved.");
            return updatedCongees;
        }
        throw new NotFoundException("Congees request not found.");
    }


    public Congees rejectCongees(Long congeesId, String motif) {
        Optional<Congees> optionalCongees = congeesRepository.findById(congeesId);
        if (optionalCongees.isPresent()) {
            Congees congees = optionalCongees.get();
            if (congees.getStatus() != CongeStatus.PENDING) {
                throw new BadRequestException("Congees request is not in PENDING status.");
            }
            congees.setStatus(CongeStatus.REJECTED);
            Congees updatedCongees = congeesRepository.save(congees);
            sendNotification(congees.getRequestedBy(), "Congees request rejected. Motif: " + motif);
            return updatedCongees;
        }
        throw new NotFoundException("Congees request not found.");
    }

    private void sendNotification(User user, String message) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setTimestamp(LocalDateTime.now());
        notification.setRecipient(user.getEmail());
        notificationController.sendNotification(notification);
    }

    public Congees getCongeesById(Long id) {
        Optional<Congees> congees = congeesRepository.findById(id);
        return congees.orElse(null);
    }

    public Congees updateCongees(Long id, Congees updatedCongees) {
        Optional<Congees> optionalCongees = congeesRepository.findById(id);
        if (optionalCongees.isPresent()) {
            Congees existingCongees = optionalCongees.get();
            existingCongees.setDateDebut(updatedCongees.getDateDebut());
            existingCongees.setDateFin(updatedCongees.getDateFin());
            existingCongees.setMotif(updatedCongees.getMotif());
            existingCongees.setStatus(updatedCongees.getStatus());
            existingCongees.setRequestedBy(updatedCongees.getRequestedBy());
            existingCongees.setRemplacant(updatedCongees.getRemplacant());
            return congeesRepository.save(existingCongees);
        }
        return null;
    }

    public void deleteCongees(Long id) {
        congeesRepository.deleteById(id);
    }

    public List<Congees> getAllCongees() {
        return congeesRepository.findAll();
    }
}