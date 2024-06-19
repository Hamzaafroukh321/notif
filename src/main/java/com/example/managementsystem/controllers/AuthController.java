package com.example.managementsystem.controllers;

import com.example.managementsystem.config.JwtTokenProvider;
import com.example.managementsystem.exceptions.NotFoundException;
import com.example.managementsystem.models.entities.User;
import com.example.managementsystem.models.entities.UserRole;
import com.example.managementsystem.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.example.managementsystem.models.entities.Permission;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider,
                          UserDetailsService userDetailsService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @PostMapping("/login")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Map<String, Object>> authenticateUser(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");

        if (email == null || email.isEmpty()) {
            throw new NotFoundException("Email cannot be null or empty");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found with email: " + email));

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        List<String> roles = user.getRoles().stream()
                .map(UserRole::getName)
                .collect(Collectors.toList());

        List<String> permissions = user.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .map(Permission::getName)
                .collect(Collectors.toList());



        Long matricule = user.getMatricule();

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("roles", roles);
        response.put("permissions", permissions);
        response.put("firstTime", user.isFirstTime());
        response.put("matricule", matricule);
        response.put("nom", user.getNom());
        response.put("prenom", user.getPrenom());
        response.put("email", user.getEmail());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/reset-password")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Map<String, String>> resetPassword(@RequestBody Map<String, String> resetPasswordRequest) {
        String email = resetPasswordRequest.get("email");
        String newPassword = resetPasswordRequest.get("newPassword");

        if (email == null || email.isEmpty()) {
            throw new NotFoundException("Email cannot be null or empty");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found with email: " + email));

        // Update the user's password
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setFirstTime(false);
        userRepository.save(user);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Password reset successfully");
        return ResponseEntity.ok(response);
    }
}