package com.example.managementsystem.services;

import com.example.managementsystem.config.PasswordGenerator;
import com.example.managementsystem.exceptions.BadRequestException;
import com.example.managementsystem.models.User;
import com.example.managementsystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }


    public User getUserByMatricule(Long matricule) {
        return userRepository.findByMatricule(matricule);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public User saveUser(User user) {
        if (userRepository.findByEmailpersonnel(user.getEmailpersonnel()).isPresent()) {
            throw new DataIntegrityViolationException("Personal email already exists");
        }

        String password = PasswordGenerator.generatePassword();
        user.setPassword(passwordEncoder.encode(password));

        User savedUser = userRepository.save(user);

        String to = user.getEmailpersonnel();
        String subject = "Votre nouveau compte utilisateur";
        String text = "Matricule: " + savedUser.getMatricule() + "\n" +
                "Email professionnel: " + savedUser.getEmail() + "\n" +
                "Mot de passe: " + password + "\n";


        //emailService.sendEmail(to, subject, text);

        return savedUser;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public User updateUser(Long matricule, User updatedUser) {
        User existingUser = getUserByMatricule(matricule);

        if (!existingUser.getMatricule().equals(updatedUser.getMatricule())) {
            throw new BadRequestException("Cannot change user matricule");
        }

        existingUser.setNom(updatedUser.getNom());
        existingUser.setPrenom(updatedUser.getPrenom());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setTel(updatedUser.getTel());
        existingUser.setAdresse(updatedUser.getAdresse());
        existingUser.setRole(updatedUser.getRole());

        return userRepository.save(existingUser);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUserByMatricule(Long matricule) {
        User user = getUserByMatricule(matricule);
        userRepository.delete(user);
    }


    public User saveAndFlushUser(User user) {
        return userRepository.saveAndFlush(user);
    }




}