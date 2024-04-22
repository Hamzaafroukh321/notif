package com.example.managementsystem.config;

import com.example.managementsystem.DTO.*;
import com.example.managementsystem.controllers.AuthController;
import com.example.managementsystem.models.entities.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.example.managementsystem.controllers.AuthController.authenticateUser(..))")
    public void authenticationMethod() {}

    @Pointcut("execution(* com.example.managementsystem.services.UserService.*(..))")
    public void userServiceMethods() {}

    @Pointcut("execution(* com.example.managementsystem.services.ProjetService.*(..))")
    public void projetServiceMethods() {}

    @Pointcut("execution(* com.example.managementsystem.services.SprintService.*(..))")
    public void sprintServiceMethods() {}

    @Pointcut("execution(* com.example.managementsystem.services.BacklogService.*(..))")
    public void backlogServiceMethods() {}

    @Pointcut("execution(* com.example.managementsystem.services.TaskService.*(..))")
    public void taskServiceMethods() {}

    @Pointcut("execution(* com.example.managementsystem.services.CongeesService.*(..))")
    public void congeesServiceMethods() {}

    @Pointcut("execution(* com.example.managementsystem.services.UserStoryService.*(..))")
    public void userStoryServiceMethods() {}

    @AfterReturning(pointcut = "authenticationMethod()", returning = "result")
    public void logAuthentication(JoinPoint joinPoint, Object result) {
        ResponseEntity<?> response = (ResponseEntity<?>) result;
        if (response.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> body = (Map<String, Object>) response.getBody();
            String email = (String) body.get("email");
            String matricule = (String) body.get("matricule");
            List<String> roles = (List<String>) body.get("roles");

            if (email != null && !email.isEmpty()) {
                logger.info("Utilisateur connecté avec l'e-mail : {} et les rôles : {}", email, roles);
            } else if (matricule != null && !matricule.isEmpty()) {
                logger.info("Utilisateur connecté avec le matricule : {} et les rôles : {}", matricule, roles);
            }
        }
    }

    @AfterReturning(pointcut = "userServiceMethods()", returning = "result")
    public void logUserServiceMethod(JoinPoint joinPoint, Object result) {
        logger.info("Méthode {} appelée avec succès dans UserService. Résultat : {}", joinPoint.getSignature().getName(), result);
    }

    @AfterReturning(pointcut = "projetServiceMethods()", returning = "result")
    public void logProjetServiceMethod(JoinPoint joinPoint, Object result) {
        logger.info("Méthode {} appelée avec succès dans ProjetService. Résultat : {}", joinPoint.getSignature().getName(), result);
    }

    @AfterReturning(pointcut = "sprintServiceMethods()", returning = "result")
    public void logSprintServiceMethod(JoinPoint joinPoint, Object result) {
        logger.info("Méthode {} appelée avec succès dans SprintService. Résultat : {}", joinPoint.getSignature().getName(), result);
    }

    @AfterReturning(pointcut = "backlogServiceMethods()", returning = "result")
    public void logBacklogServiceMethod(JoinPoint joinPoint, Object result) {
        logger.info("Méthode {} appelée avec succès dans BacklogService. Résultat : {}", joinPoint.getSignature().getName(), result);
    }

    @AfterReturning(pointcut = "taskServiceMethods()", returning = "result")
    public void logTaskServiceMethod(JoinPoint joinPoint, Object result) {
        logger.info("Méthode {} appelée avec succès dans TaskService. Résultat : {}", joinPoint.getSignature().getName(), result);
    }

    @AfterReturning(pointcut = "congeesServiceMethods()", returning = "result")
    public void logCongeesServiceMethod(JoinPoint joinPoint, Object result) {
        logger.info("Méthode {} appelée avec succès dans CongeesService. Résultat : {}", joinPoint.getSignature().getName(), result);
    }

    @AfterReturning(pointcut = "userStoryServiceMethods()", returning = "result")
    public void logUserStoryServiceMethod(JoinPoint joinPoint, Object result) {
        logger.info("Méthode {} appelée avec succès dans UserStoryService. Résultat : {}", joinPoint.getSignature().getName(), result);
    }
}