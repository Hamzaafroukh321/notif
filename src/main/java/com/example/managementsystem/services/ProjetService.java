package com.example.managementsystem.services;

import com.example.managementsystem.DTO.ProjetDTO;
import com.example.managementsystem.DTO.TaskDTO;
import com.example.managementsystem.DTO.UserDTO;
import com.example.managementsystem.config.AuditUtil;
import com.example.managementsystem.exceptions.NotFoundException;
import com.example.managementsystem.mappers.TaskMapper;
import com.example.managementsystem.mappers.UserMapper;
import com.example.managementsystem.models.entities.Projet;
import com.example.managementsystem.models.entities.Task;
import com.example.managementsystem.models.entities.User;
import com.example.managementsystem.repositories.ProjetRepository;
import com.example.managementsystem.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.managementsystem.mappers.ProjetMapper;
import com.example.managementsystem.repositories.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@Transactional
public class ProjetService {
    private final ProjetRepository projetRepository;
    private final UserRepository userRepository;
    private final ProjetMapper projetMapper;

    private final TaskRepository taskRepository;
    private final UserService userService;

    private final UserMapper userMapper;  // Add UserMapper

    private final TaskMapper taskMapper;

    private final AuditUtil auditUtil;

    @Autowired
    public ProjetService(ProjetRepository projetRepository, UserRepository userRepository, ProjetMapper projetMapper, TaskRepository taskRepository, UserMapper userMapper, UserService userService, TaskMapper taskMapper, AuditUtil auditUtil) {
        this.projetRepository = projetRepository;
        this.userRepository = userRepository;
        this.projetMapper = projetMapper;
        this.taskRepository = taskRepository;
        this.userMapper = userMapper;  // Initialize UserMapper
        this.userService = userService;
        this.taskMapper = taskMapper;
        this.auditUtil = auditUtil;
    }

    @PreAuthorize("hasAnyAuthority('MANAGE_PROJECTS')")
    public List<ProjetDTO> getAllProjets() {
        List<Projet> projets = projetRepository.findAll();
        return projetMapper.toDTOs(projets);
    }

    @PreAuthorize("hasAnyAuthority('VIEW_PROJECT')")
    public ProjetDTO getProjetById(Long id) {
        Projet projet = projetRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Projet not found with id: " + id));
        return projetMapper.toDTO(projet);
    }

    @PreAuthorize("hasAuthority('MANAGE_PROJECTS')")
    public ProjetDTO createProjet(ProjetDTO projetDTO) {
        userService.getUserByMatricule(projetDTO.chefMatricule());

        List<User> users = userRepository.findAll();

        Projet projet = projetMapper.toEntity(projetDTO, users);
        Projet savedProjet = projetRepository.save(projet);

        auditUtil.logAudit("CREATE", "Created Projet with details: " + savedProjet.toString());

        return projetMapper.toDTO(savedProjet);
    }


    @PreAuthorize("hasAuthority('MANAGE_PROJECTS')")
    public ProjetDTO updateProjetPartially(Long id, Map<String, Object> updates) {
        Projet existingProjet = projetRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Projet not found with id: " + id));

        ProjetDTO oldProjetDTO = projetMapper.toDTO(existingProjet);

        if (updates.containsKey("status")) {
            existingProjet.setStatus((String) updates.get("status"));
        }
        if (updates.containsKey("dateFin")) {
            existingProjet.setDateFin(LocalDate.parse((String) updates.get("dateFin")));
        }
        if (updates.containsKey("mode")) {
            existingProjet.setMode((String) updates.get("mode"));
        }
        if (updates.containsKey("chefMatricule")) {
            Number chefMatriculeNum = (Number) updates.get("chefMatricule");
            Long chefMatricule = chefMatriculeNum.longValue();
            User chef = userRepository.findByMatricule(chefMatricule)
                    .orElseThrow(() -> new NotFoundException("User not found with matricule: " + chefMatricule));
            existingProjet.setChef(chef);
        }
        if (updates.containsKey("teamMembersMatricules")) {
            Object teamMembersMatriculesObj = updates.get("teamMembersMatricules");
            if (teamMembersMatriculesObj instanceof List<?>) {
                List<?> teamMembersMatriculesRaw = (List<?>) teamMembersMatriculesObj;
                List<Long> teamMembersMatricules = new ArrayList<>();
                for (Object matriculeObj : teamMembersMatriculesRaw) {
                    if (matriculeObj instanceof Integer) {
                        teamMembersMatricules.add(((Integer) matriculeObj).longValue());
                    } else if (matriculeObj instanceof Long) {
                        teamMembersMatricules.add((Long) matriculeObj);
                    } else {
                        throw new IllegalArgumentException("Invalid type in teamMembersMatricules list");
                    }
                }
                List<User> teamMembers = userRepository.findAllById(teamMembersMatricules);
                existingProjet.setTeamMembers(teamMembers);
            } else {
                throw new IllegalArgumentException("teamMembersMatricules must be a list");
            }
        }

        Projet savedProjet = projetRepository.save(existingProjet);
        ProjetDTO newProjetDTO = projetMapper.toDTO(savedProjet);

        auditUtil.logAudit("UPDATE", "Updated Projet with ID: " + id + " from details: " + oldProjetDTO.toString() + " to details: " + newProjetDTO.toString());

        return newProjetDTO;
    }


    @PreAuthorize("hasAnyAuthority('VIEW_PROJECT')")
    public List<ProjetDTO> getProjetsByChefMatricule(Long chefMatricule) {
        List<Projet> projets = projetRepository.findByChef_Matricule(chefMatricule);
        return projetMapper.toDTOs(projets);
    }

    @PreAuthorize("hasAnyAuthority('VIEW_PROJECT')")
    public List<UserDTO> getTeamMembersByProjetId(Long projetId) {
        Projet projet = projetRepository.findById(projetId)
                .orElseThrow(() -> new NotFoundException("Projet not found with id: " + projetId));
        List<User> teamMembers = projet.getTeamMembers();
        return userMapper.toDTOs(teamMembers);  // Use UserMapper to convert to DTOs
    }

    @PreAuthorize("hasAnyAuthority('VIEW_PROJECT')")
    public List<TaskDTO> getTasksByProjetId(Long projetId) {
        List<Task> tasks = taskRepository.findTasksByProjetId(projetId);
        return tasks.stream()
                .map(taskMapper::toDTO)
                .collect(Collectors.toList());
    }
}