package com.example.managementsystem.controllers;

import com.example.managementsystem.config.JwtTokenProvider;
import com.example.managementsystem.exceptions.NotFoundException;
import com.example.managementsystem.models.entities.User;
import com.example.managementsystem.models.enums.UserRole;
import com.example.managementsystem.repositories.UserRepository;
import com.example.managementsystem.services.UserDetailsServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider,
                          UserDetailsService userDetailsService, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
    }


    @PostMapping("/login")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Map<String, Object>> authenticateUser(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String matricule = loginRequest.get("matricule");
        String password = loginRequest.get("password");

        if ((email == null || email.isEmpty()) && (matricule == null || matricule.isEmpty())) {
            throw new NotFoundException("Email or matricule cannot be null or empty");
        }

        User user;
        if (email != null && !email.isEmpty()) {
            user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new NotFoundException("User not found with email: " + email));
        } else {
            try {
                Long matriculeValue = Long.parseLong(matricule);
                user = userRepository.findByMatricule(matriculeValue)
                        .orElseThrow(() -> new NotFoundException("User not found with matricule: " + matricule));
            } catch (NumberFormatException e) {
                throw new NotFoundException("Invalid matricule format");
            }
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        List<String> roles = user.getRoles().stream()
                .map(UserRole::name)
                .collect(Collectors.toList());

        List<String> permissions = user.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .map(Enum::name)
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("roles", roles);
        response.put("permissions", permissions);

        return ResponseEntity.ok(response);
    }
}