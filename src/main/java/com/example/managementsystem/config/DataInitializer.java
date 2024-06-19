
package com.example.managementsystem.config;


import com.example.managementsystem.models.entities.Permission;
import com.example.managementsystem.models.entities.User;
import com.example.managementsystem.models.entities.UserRole;
import com.example.managementsystem.repositories.PermissionRepository;
import com.example.managementsystem.repositories.UserRepository;
import com.example.managementsystem.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) throws Exception {
        // Créer les permissions
        // Define the permissions
        Permission requestLeave = createPermissionIfNotExists("REQUEST_LEAVE");
        Permission manageRequestLeave = createPermissionIfNotExists("MANAGE_REQUEST_LEAVE");
        Permission managePermissions = createPermissionIfNotExists("MANAGE_PERMISSIONS");
        Permission manageProjects = createPermissionIfNotExists("MANAGE_PROJECTS");
        Permission viewProject = createPermissionIfNotExists("VIEW_PROJECT");
        Permission assignTasks = createPermissionIfNotExists("ASSIGN_TASKS");
        Permission manageTasks = createPermissionIfNotExists("MANAGE_TASKS");
        Permission manageAudit = createPermissionIfNotExists("MANAGE_AUDIT");
        Permission manageUsers = createPermissionIfNotExists("MANAGE_USERS");
        Permission viewUsers = createPermissionIfNotExists("VIEW_USERS");
        Permission chatMessage = createPermissionIfNotExists("CHAT_MESSAGE");

// Create roles with their default permissions
        createUserRoleIfNotExists("ADMIN", Set.of(manageUsers, managePermissions, manageAudit, viewUsers));
        createUserRoleIfNotExists("MANAGER", Set.of(manageRequestLeave, manageProjects, viewUsers));
        createUserRoleIfNotExists("PROJECT_MANAGER", Set.of(viewProject, assignTasks, requestLeave, viewUsers));
        createUserRoleIfNotExists("TEAM_MEMBER", Set.of(manageTasks, requestLeave, viewUsers));




        createUserIfNotExists("admin", "password123", "admin@dxc.com", "adminpersonnel@example.com", "ADMIN");

    }

    private Permission createPermissionIfNotExists(String name) {
        return permissionRepository.findByName(name)
                .orElseGet(() -> permissionRepository.save(new Permission(name)));
    }

    private UserRole createUserRoleIfNotExists(String name, Set<Permission> permissions) {
        return userRoleRepository.findByName(name)
                .orElseGet(() -> {
                    UserRole userRole = new UserRole();
                    userRole.setName(name);
                    userRole.setPermissions(permissions);
                    return userRoleRepository.save(userRole);
                });
    }







    private User createUserIfNotExists(String username, String password, String email, String emailPersonnel, String roleName) {
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            // update user details here if needed
            return user;
        } else {
            User user = new User();
            user.setEmail(email);
            user.setEmailpersonnel(emailPersonnel);
            user.setPassword(passwordEncoder.encode(password));
            UserRole userRole = userRoleRepository.findByName(roleName)
                    .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
            user.setRoles(Set.of(userRole));
            return userRepository.save(user);
        }
    }


}