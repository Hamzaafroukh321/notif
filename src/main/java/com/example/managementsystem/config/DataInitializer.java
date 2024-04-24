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
        Permission manageUsers = createPermissionIfNotExists("MANAGE_USERS");
        Permission resetPassword = createPermissionIfNotExists("RESET_PASSWORD");
        Permission manageProjects = createPermissionIfNotExists("MANAGE_PROJECTS");
        Permission manageTeams = createPermissionIfNotExists("MANAGE_TEAMS");
        Permission viewProjectProgress = createPermissionIfNotExists("VIEW_PROJECT_PROGRESS");
        Permission manageSprints = createPermissionIfNotExists("MANAGE_SPRINTS");
        Permission manageTasks = createPermissionIfNotExists("MANAGE_TASKS");
        Permission requestLeave = createPermissionIfNotExists("REQUEST_LEAVE");
        Permission approveLeave = createPermissionIfNotExists("APPROVE_LEAVE");
        Permission viewAssignedTasks = createPermissionIfNotExists("VIEW_ASSIGNED_TASKS");
        Permission updateTaskStatus = createPermissionIfNotExists("UPDATE_TASK_STATUS");
        Permission submitTask = createPermissionIfNotExists("SUBMIT_TASK");
        Permission viewActiveSprint = createPermissionIfNotExists("VIEW_ACTIVE_SPRINT");


        // Créer les rôles utilisateur avec leurs permissions par défaut
        createUserRoleIfNotExists("ADMIN", Set.of(manageUsers, resetPassword));
        createUserRoleIfNotExists("MANAGER", Set.of(manageProjects, manageTeams, viewProjectProgress, manageSprints, approveLeave));
        createUserRoleIfNotExists("PROJECT_MANAGER", Set.of(manageTasks, viewProjectProgress, manageSprints, requestLeave));
        createUserRoleIfNotExists("TEAM_MEMBER", Set.of(viewAssignedTasks, updateTaskStatus, submitTask, viewActiveSprint, requestLeave));

        createUserIfNotExists("admin", "password123", "admin@example.com", "adminpersonnel@example.com", "ADMIN");
        createUserIfNotExists("manager", "password123", "manager@example.com", "managerpersonnel@example.com", "MANAGER");
        createUserIfNotExists("project_manager", "password123", "project_manager@example.com", "projectmanagerpersonnel@example.com", "PROJECT_MANAGER");
        createUserIfNotExists("team_member", "password123", "team_member@example.com", "teammemberpersonnel@example.com", "TEAM_MEMBER");
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
        return userRepository.findByEmail(email)
                .orElseGet(() -> {
                    User user = new User();
                    user.setEmail(email);
                    user.setEmailpersonnel(emailPersonnel);
                    user.setPassword(passwordEncoder.encode(password));
                    UserRole userRole = userRoleRepository.findByName(roleName)
                            .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
                    user.setRoles(Set.of(userRole));
                    return userRepository.save(user);
                });
    }


}