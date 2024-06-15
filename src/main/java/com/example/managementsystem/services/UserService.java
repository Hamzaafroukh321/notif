package com.example.managementsystem.services;

import com.example.managementsystem.DTO.UserDTO;
import com.example.managementsystem.config.PasswordGenerator;
import com.example.managementsystem.exceptions.NotFoundException;
import com.example.managementsystem.mappers.UserMapper;
import com.example.managementsystem.models.entities.User;
import com.example.managementsystem.models.entities.UserRole;
import com.example.managementsystem.repositories.UserRepository;
import com.example.managementsystem.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder, EmailService emailService, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.userMapper = userMapper;
    }

   @PreAuthorize("hasAuthority('VIEW_USERS')")
    public UserDTO getUserByMatricule(Long matricule) {
        User user = userRepository.findByMatricule(matricule)
                .orElseThrow(() -> new NotFoundException("User not found with matricule: " + matricule));
        return userMapper.toDTO(user);
    }


    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toDTO).collect(Collectors.toList());
    }



    public UserDTO saveUser(UserDTO userDTO) {
        String password = PasswordGenerator.generatePassword();
        User user = userMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(password));

        Set<UserRole> userRoles = new HashSet<>();
        for (String roleName : userDTO.roles()) {
            UserRole role = userRoleRepository.findByName(roleName)
                    .orElseThrow(() -> new NotFoundException("Role not found with name: " + roleName));
            userRoles.add(role);
        }
        user.setRoles(userRoles);

        User savedUser = userRepository.save(user);
        UserDTO savedUserDTO = userMapper.toDTO(savedUser);

        sendNewUserEmail(savedUserDTO, password);

        return savedUserDTO;
    }


    public UserDTO updateUser(Long matricule, UserDTO updatedUserDTO) {
        User existingUser = userRepository.findByMatricule(matricule)
                .orElseThrow(() -> new NotFoundException("User not found with matricule: " + matricule));

        existingUser.setNom(updatedUserDTO.nom());
        existingUser.setPrenom(updatedUserDTO.prenom());
        existingUser.setDepartement(updatedUserDTO.departement());
        existingUser.setEmailpersonnel(updatedUserDTO.emailpersonnel());
        existingUser.setEmail(updatedUserDTO.email());
        existingUser.setTel(updatedUserDTO.tel());

        Set<UserRole> userRoles = new HashSet<>();
        for (String roleName : updatedUserDTO.roles()) {
            UserRole role = userRoleRepository.findByName(roleName)
                    .orElseThrow(() -> new NotFoundException("Role not found with name: " + roleName));
            userRoles.add(role);
        }
        existingUser.setRoles(userRoles);

        User savedUser = userRepository.save(existingUser);
        return userMapper.toDTO(savedUser);
    }


    public void deleteUserByMatricule(Long matricule) {
        User user = userRepository.findByMatricule(matricule)
                .orElseThrow(() -> new NotFoundException("User not found with matricule: " + matricule));

        userRepository.delete(user);
    }


    public UserDTO saveAndFlushUser(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        User savedUser = userRepository.saveAndFlush(user);
        return userMapper.toDTO(savedUser);
    }

    private void sendNewUserEmail(UserDTO userDTO, String password) {
        String to = userDTO.emailpersonnel();
        String subject = "Votre nouveau compte utilisateur";
        String text = "Matricule: " + userDTO.matricule() + "\n" +
                "Email professionnel: " + userDTO.email() + "\n" +
                "Mot de passe: " + password + "\n";

        emailService.sendEmail(to, subject, text);
    }


    public Page<UserDTO> getAllUsers(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users.map(userMapper::toDTO);
    }


    public long getTotalUsers() {
        return userRepository.count();
    }


    public User getUserEntityByMatricule(Long matricule) {
        return userRepository.findByMatricule(matricule)
                .orElseThrow(() -> new NotFoundException("User not found with matricule: " + matricule));
    }


    public Page<UserDTO> searchUsers(String nom, String prenom, Long matricule, Pageable pageable) {
        Specification<User> spec = Specification.where(null);

        if (nom != null && !nom.isEmpty()) {
            spec = spec.and(UserSpecification.searchByNom(nom));
        }

        if (prenom != null && !prenom.isEmpty()) {
            spec = spec.and(UserSpecification.searchByPrenom(prenom));
        }

        if (matricule != null) {
            spec = spec.and(UserSpecification.searchByMatricule(matricule));
        }

        Page<User> users = userRepository.findAll(spec, pageable);
        return users.map(userMapper::toDTO);
    }


    public List<Long> getAllUsersMatricules() {
        List<Long> users = userRepository.findAllMatricule();
        return users;
    }

    //get user with role : PROJECT_MANAGER

    public List<UserDTO> getUserByRole(String roleName) {
        UserRole role = userRoleRepository.findByName(roleName)
                .orElseThrow(() -> new NotFoundException("Role not found with name: " + roleName));
        List<User> users = userRepository.findAllByRoleName(roleName);
        return users.stream().map(userMapper::toDTO).collect(Collectors.toList());
    }


    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }


}