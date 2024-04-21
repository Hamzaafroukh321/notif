package com.example.managementsystem.services;

import com.example.managementsystem.DTO.UserDTO;
import com.example.managementsystem.config.PasswordGenerator;
import com.example.managementsystem.exceptions.NotFoundException;
import com.example.managementsystem.mappers.UserMapper;
import com.example.managementsystem.models.entities.User;
import com.example.managementsystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailService emailService, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.userMapper = userMapper;
    }

    public UserDTO getUserByMatricule(Long matricule) {
        User user = userRepository.findByMatricule(matricule)
                .orElseThrow(() -> new NotFoundException("User not found with matricule: " + matricule));
        return userMapper.toDTO(user);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserDTO saveUser(UserDTO userDTO) {
        String password = PasswordGenerator.generatePassword();
        User user = userMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(password));

        User savedUser = userRepository.save(user);
        UserDTO savedUserDTO = userMapper.toDTO(savedUser);

        //sendNewUserEmail(savedUserDTO, password);

        return savedUserDTO;
    }

    public UserDTO updateUser(Long matricule, UserDTO updatedUserDTO) {
        User existingUser = userRepository.findByMatricule(matricule)
                .orElseThrow(() -> new NotFoundException("User not found with matricule: " + matricule));

        userMapper.updateUserFromDTO(existingUser, updatedUserDTO);
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

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.toDTOs(users);
    }
}