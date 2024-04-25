package com.example.managementsystem.config;

import com.example.managementsystem.DTO.*;
import com.example.managementsystem.models.entities.User;
import com.example.managementsystem.services.AuditService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import com.example.managementsystem.models.enums.TaskStatus;



@Aspect
@Component
public class AuditAspect {
    private final AuditService auditService;

    @Autowired
    public AuditAspect(AuditService auditService) {
        this.auditService = auditService;
    }

    @AfterReturning(value = "execution(* com.example.managementsystem.services.UserService.*(..))", returning = "result")
    public void logUserServiceCall(JoinPoint joinPoint, Object result) {
        String action = joinPoint.getSignature().getName();
        String description = getUserServiceDescription(joinPoint, result);

        AuditDTO auditDTO = createAuditDTO(action, description);
        auditService.saveAudit(auditDTO);
    }

    @AfterReturning(value = "execution(* com.example.managementsystem.services.CongeesService.*(..))", returning = "result")
    public void logCongeesServiceCall(JoinPoint joinPoint, Object result) {
        String action = joinPoint.getSignature().getName();
        String description = getCongeesServiceDescription(joinPoint, result);

        AuditDTO auditDTO = createAuditDTO(action, description);
        auditService.saveAudit(auditDTO);
    }

    @AfterReturning(value = "execution(* com.example.managementsystem.services.BacklogService.*(..))", returning = "result")
    public void logBacklogServiceCall(JoinPoint joinPoint, Object result) {
        String action = joinPoint.getSignature().getName();
        String description = getBacklogServiceDescription(joinPoint, result);

        AuditDTO auditDTO = createAuditDTO(action, description);
        auditService.saveAudit(auditDTO);
    }

    private String getUserServiceDescription(JoinPoint joinPoint, Object result) {
        String matricule = getLoggedInUserMatricule();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();

        StringBuilder description = new StringBuilder("User with matricule " + matricule);

        switch (methodName) {
            case "saveUser":
                UserDTO savedUserDTO = (UserDTO) result;
                description.append(" added a new user with matricule ").append(savedUserDTO.matricule());
                break;
            case "updateUser":
                Long updatedUserMatricule = (Long) arguments[0];
                description.append(" updated user with matricule ").append(updatedUserMatricule);
                break;
            case "deleteUserByMatricule":
                Long deletedUserMatricule = (Long) arguments[0];
                description.append(" deleted user with matricule ").append(deletedUserMatricule);
                break;
            default:
                description.append(" performed action: ").append(methodName);
                break;
        }

        return description.toString();
    }

    private String getCongeesServiceDescription(JoinPoint joinPoint, Object result) {
        String matricule = getLoggedInUserMatricule();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();

        StringBuilder description = new StringBuilder("User with matricule " + matricule);

        switch (methodName) {
            case "createCongees":
                CongeDTO createdCongeDTO = (CongeDTO) result;
                description.append(" created a new congees request with id ").append(createdCongeDTO.id());
                break;
            case "approveCongees":
                Long approvedCongeId = (Long) arguments[0];
                description.append(" approved congees request with id ").append(approvedCongeId);
                break;
            case "rejectCongees":
                Long rejectedCongeId = (Long) arguments[0];
                description.append(" rejected congees request with id ").append(rejectedCongeId);
                break;
            default:
                description.append(" performed action: ").append(methodName);
                break;
        }

        return description.toString();
    }

    private String getBacklogServiceDescription(JoinPoint joinPoint, Object result) {
        String matricule = getLoggedInUserMatricule();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();

        StringBuilder description = new StringBuilder("User with matricule " + matricule);

        switch (methodName) {
            case "createBacklog":
                BacklogDTO createdBacklogDTO = (BacklogDTO) result;
                description.append(" created a new backlog with id ").append(createdBacklogDTO.id());
                break;
            case "updateBacklog":
                Integer updatedBacklogId = (Integer) arguments[0];
                description.append(" updated backlog with id ").append(updatedBacklogId);
                break;
            case "deleteBacklogById":
                Integer deletedBacklogId = (Integer) arguments[0];
                description.append(" deleted backlog with id ").append(deletedBacklogId);
                break;
            default:
                description.append(" performed action: ").append(methodName);
                break;
        }

        return description.toString();
    }

    @AfterReturning(value = "execution(* com.example.managementsystem.services.PermissionService.*(..))", returning = "result")
    public void logPermissionServiceCall(JoinPoint joinPoint, Object result) {
        String action = joinPoint.getSignature().getName();
        String description = getPermissionServiceDescription(joinPoint, result);

        AuditDTO auditDTO = createAuditDTO(action, description);
        auditService.saveAudit(auditDTO);
    }

    private String getPermissionServiceDescription(JoinPoint joinPoint, Object result) {
        String matricule = getLoggedInUserMatricule();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();

        StringBuilder description = new StringBuilder("User with matricule " + matricule);

        switch (methodName) {
            case "createPermission":
                PermissionDTO createdPermissionDTO = (PermissionDTO) result;
                description.append(" created a new permission with name ").append(createdPermissionDTO.name());
                break;
            default:
                description.append(" performed action: ").append(methodName);
                break;
        }

        return description.toString();
    }

    @AfterReturning(value = "execution(* com.example.managementsystem.services.ProjetService.*(..))", returning = "result")
    public void logProjetServiceCall(JoinPoint joinPoint, Object result) {
        String action = joinPoint.getSignature().getName();
        String description = getProjetServiceDescription(joinPoint, result);

        AuditDTO auditDTO = createAuditDTO(action, description);
        auditService.saveAudit(auditDTO);
    }

    private String getProjetServiceDescription(JoinPoint joinPoint, Object result) {
        String matricule = getLoggedInUserMatricule();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();

        StringBuilder description = new StringBuilder("User with matricule " + matricule);

        switch (methodName) {
            case "createProjet":
                ProjetDTO createdProjetDTO = (ProjetDTO) result;
                description.append(" created a new projet with id ").append(createdProjetDTO.id());
                break;
            case "updateProjet":
                Long updatedProjetId = (Long) arguments[0];
                description.append(" updated projet with id ").append(updatedProjetId);
                break;
            case "deleteProjetById":
                Long deletedProjetId = (Long) arguments[0];
                description.append(" deleted projet with id ").append(deletedProjetId);
                break;
            default:
                description.append(" performed action: ").append(methodName);
                break;
        }

        return description.toString();
    }

    @AfterReturning(value = "execution(* com.example.managementsystem.services.SprintService.*(..))", returning = "result")
    public void logSprintServiceCall(JoinPoint joinPoint, Object result) {
        String action = joinPoint.getSignature().getName();
        String description = getSprintServiceDescription(joinPoint, result);

        AuditDTO auditDTO = createAuditDTO(action, description);
        auditService.saveAudit(auditDTO);
    }

    private String getSprintServiceDescription(JoinPoint joinPoint, Object result) {
        String matricule = getLoggedInUserMatricule();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();

        StringBuilder description = new StringBuilder("User with matricule " + matricule);

        switch (methodName) {
            case "createSprint":
                SprintDTO createdSprintDTO = (SprintDTO) result;
                description.append(" created a new sprint with id ").append(createdSprintDTO.id());
                break;
            case "updateSprint":
                Long updatedSprintId = (Long) arguments[0];
                description.append(" updated sprint with id ").append(updatedSprintId);
                break;
            case "deleteSprintById":
                Long deletedSprintId = (Long) arguments[0];
                description.append(" deleted sprint with id ").append(deletedSprintId);
                break;
            default:
                description.append(" performed action: ").append(methodName);
                break;
        }

        return description.toString();
    }
    @AfterReturning(value = "execution(* com.example.managementsystem.services.TaskService.*(..))", returning = "result")
    public void logTaskServiceCall(JoinPoint joinPoint, Object result) {
        String action = joinPoint.getSignature().getName();
        String description = getTaskServiceDescription(joinPoint, result);

        AuditDTO auditDTO = createAuditDTO(action, description);
        auditService.saveAudit(auditDTO);
    }

    private String getTaskServiceDescription(JoinPoint joinPoint, Object result) {
        String matricule = getLoggedInUserMatricule();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();

        StringBuilder description = new StringBuilder("User with matricule " + matricule);

        switch (methodName) {
            case "createTask":
                TaskDTO createdTaskDTO = (TaskDTO) result;
                description.append(" created a new task with id ").append(createdTaskDTO.id());
                break;
            case "updateTask":
                Long updatedTaskId = (Long) arguments[0];
                description.append(" updated task with id ").append(updatedTaskId);
                break;
            case "updateTaskStatus":
                Long updatedTaskStatusId = (Long) arguments[0];
                TaskStatus newStatus = (TaskStatus) arguments[1];
                description.append(" updated task status with id ").append(updatedTaskStatusId)
                        .append(" to ").append(newStatus);
                break;
            case "deleteTask":
                Long deletedTaskId = (Long) arguments[0];
                description.append(" deleted task with id ").append(deletedTaskId);
                break;
            default:
                description.append(" performed action: ").append(methodName);
                break;
        }

        return description.toString();
    }

    @AfterReturning(value = "execution(* com.example.managementsystem.services.UserStoryService.*(..))", returning = "result")
    public void logUserStoryServiceCall(JoinPoint joinPoint, Object result) {
        String action = joinPoint.getSignature().getName();
        String description = getUserStoryServiceDescription(joinPoint, result);

        AuditDTO auditDTO = createAuditDTO(action, description);
        auditService.saveAudit(auditDTO);
    }

    private String getUserStoryServiceDescription(JoinPoint joinPoint, Object result) {
        String matricule = getLoggedInUserMatricule();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();

        StringBuilder description = new StringBuilder("User with matricule " + matricule);

        switch (methodName) {
            case "createUserStory":
                UserStoryDTO createdUserStoryDTO = (UserStoryDTO) result;
                description.append(" created a new user story with id ").append(createdUserStoryDTO.id());
                break;
            case "updateUserStory":
                Long updatedUserStoryId = (Long) arguments[0];
                description.append(" updated user story with id ").append(updatedUserStoryId);
                break;
            case "deleteUserStory":
                Long deletedUserStoryId = (Long) arguments[0];
                description.append(" deleted user story with id ").append(deletedUserStoryId);
                break;
            default:
                description.append(" performed action: ").append(methodName);
                break;
        }

        return description.toString();
    }
    private AuditDTO createAuditDTO(String action, String description) {
        AuditDTO auditDTO = new AuditDTO();
        auditDTO.setTimestamp(LocalDateTime.now());
        auditDTO.setAction(action);
        auditDTO.setDescription(description);
        return auditDTO;
    }

    private String getLoggedInUserMatricule() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof User) {
                User user = (User) principal;
                return user.getMatricule().toString();
            }
        }
        return null;
    }
}