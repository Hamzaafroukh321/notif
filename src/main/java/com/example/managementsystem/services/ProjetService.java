package com.example.managementsystem.services;

import com.example.managementsystem.DTO.ProjetDTO;
import com.example.managementsystem.DTO.UserDTO;
import com.example.managementsystem.exceptions.NotFoundException;
import com.example.managementsystem.models.entities.Projet;
import com.example.managementsystem.models.entities.User;
import com.example.managementsystem.repositories.ProjetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.managementsystem.mappers.ProjetMapper;
import com.example.managementsystem.repositories.UserRepository;

import java.util.List;


@Service
@Transactional
public class ProjetService {
    private final ProjetRepository projetRepository;
    private final UserRepository userRepository;
    private final ProjetMapper projetMapper;
    private final UserService userService;

    @Autowired
    public ProjetService(ProjetRepository projetRepository, UserRepository userRepository, ProjetMapper projetMapper, UserService userService) {
        this.projetRepository = projetRepository;
        this.userRepository = userRepository;
        this.projetMapper = projetMapper;
        this.userService = userService;
    }

    @PreAuthorize("hasAnyAuthority('VIEW_PROJECT_PROGRESS', 'MANAGE_PROJECTS')")
    public List<ProjetDTO> getAllProjets() {
        List<Projet> projets = projetRepository.findAll();
        return projetMapper.toDTOs(projets);
    }

    @PreAuthorize("hasAnyAuthority('VIEW_PROJECT_PROGRESS', 'MANAGE_PROJECTS')")
    public ProjetDTO getProjetById(Long id) {
        Projet projet = projetRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Projet not found with id: " + id));
        return projetMapper.toDTO(projet);
    }

    @PreAuthorize("hasAuthority('MANAGE_PROJECTS')")
    public ProjetDTO createProjet(ProjetDTO projetDTO) {
        userService.getUserByMatricule(projetDTO.chefMatricule());

        // Récupérer la liste de tous les utilisateurs
        List<User> users = userRepository.findAll();

        Projet projet = projetMapper.toEntity(projetDTO, users);
        Projet savedProjet = projetRepository.save(projet);
        return projetMapper.toDTO(savedProjet);
    }

    @PreAuthorize("hasAuthority('MANAGE_PROJECTS')")
    public ProjetDTO updateProjet(Long id, ProjetDTO updatedProjetDTO) {
        Projet existingProjet = projetRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Projet not found with id: " + id));

        // Vérifier si le chef de projet existe
        userService.getUserByMatricule(updatedProjetDTO.chefMatricule());

        projetMapper.updateProjetFromDTO(updatedProjetDTO, existingProjet);
        Projet savedProjet = projetRepository.save(existingProjet);
        return projetMapper.toDTO(savedProjet);
    }

    @PreAuthorize("hasAuthority('MANAGE_PROJECTS')")
    public void deleteProjetById(Long id) {
        Projet projet = projetRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Projet not found with id: " + id));

        projetRepository.delete(projet);
    }
}