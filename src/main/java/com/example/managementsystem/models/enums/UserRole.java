package com.example.managementsystem.models.enums;

import java.util.Set;

public enum UserRole {
    ADMIN(Set.of(Permission.MANAGE_USERS, Permission.RESET_PASSWORD)),

    MANAGER(Set.of(Permission.MANAGE_PROJECTS, Permission.MANAGE_TEAMS,
            Permission.VIEW_PROJECT_PROGRESS, Permission.MANAGE_SPRINTS,
            Permission.APPROVE_LEAVE)),

    PROJECT_MANAGER(Set.of(Permission.MANAGE_TASKS, Permission.VIEW_PROJECT_PROGRESS,
            Permission.MANAGE_SPRINTS, Permission.REQUEST_LEAVE)),

    TEAM_MEMBER(Set.of(Permission.VIEW_ASSIGNED_TASKS, Permission.UPDATE_TASK_STATUS,
            Permission.SUBMIT_TASK, Permission.VIEW_ACTIVE_SPRINT,
            Permission.REQUEST_LEAVE));

    private final Set<Permission> permissions;

    UserRole(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }
}
