package com.example.managementsystem.services;

import com.example.managementsystem.models.Congees;
import com.example.managementsystem.repositories.CongeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CongeesService {

    private final CongeesRepository congeesRepository;

    @Autowired
    public CongeesService(CongeesRepository congeesRepository) {
        this.congeesRepository = congeesRepository;
    }

    public Congees createCongees(Congees congees) {
        return congeesRepository.save(congees);
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