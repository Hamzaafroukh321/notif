package com.example.managementsystem.config;

import com.example.managementsystem.controllers.AuthController;
import com.example.managementsystem.models.*;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;




@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);



    /**
     * Pointcut that matches all repositories, services and Web REST endpoints.
     */
    @Pointcut("within(@org.springframework.stereotype.Repository *)" +
            " || within(@org.springframework.stereotype.Service *)" +
            " || within(@org.springframework.web.bind.annotation.RestController *)")
    public void springBeanPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Pointcut that matches all Spring beans in the application's main packages.
     */
    @Pointcut("within(com.example.managementsystem.controllers..*)"+
            " || within(com.example.managementsystem.services..*)"+
            " || within(com.example.managementsystem.repositories..*)")
    public void applicationPackagePointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Advice that logs methods throwing exceptions.
     *
     * @param joinPoint join point for advice
     * @param e exception
     */
    @AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        logger.error("Exception in {}.{}() with cause = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), e.getCause() != null? e.getCause() : "NULL");
    }

    /**
     * Advice that logs when a method is entered and exited.
     *
     * @param joinPoint join point for advice
     * @return result
     * @throws Throwable throws IllegalArgumentException
     */
    @Around("applicationPackagePointcut() && springBeanPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Logger log = LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringTypeName());

        String username = null;
        if (joinPoint.getSignature().getName().equals("authenticateUser") &&
                joinPoint.getSignature().getDeclaringType().equals(AuthController.class)) {
            User loginRequest = (User) joinPoint.getArgs()[0];
            username = loginRequest.getEmail();
        }

        Object result = joinPoint.proceed();

        if (username != null) {
            // Vérifier si l'authentification a réussi
            if (result instanceof ResponseEntity) {
                ResponseEntity<?> responseEntity = (ResponseEntity<?>) result;
                if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                    log.info("User {} logged in successfully", username);
                }
            }
        }

        return result;
    }



    @AfterReturning(pointcut = "execution(* com.example.managementsystem.services.UserService.saveUser(..))", returning = "user")
    public void logUserCreation(User user) {
        logger.info("User Created successfully: {}", user.getEmail());
    }

    @AfterReturning(pointcut = "execution(* com.example.managementsystem.services.ProjectService.createProject(..))", returning = "projet")
    public void logProjectCreation(Projet projet) {
        logger.info("New project created by manager: {}", projet.getNom());
    }

    @AfterReturning(pointcut = "execution(* com.example.managementsystem.services.CongeesService.createCongeesRequest(..))", returning = "congees")
    public void logCongeesRequestCreation(Congees congees) {
        logger.info("Congees request created by employee: {}", congees.getRequestedBy().getEmail());
    }

    @AfterReturning(pointcut = "execution(* com.example.managementsystem.services.CongeesService.approveCongees(..))", returning = "congees")
    public void logCongeesApproval(Congees congees) {
        logger.info("Congees request approved by manager for employee: {}", congees.getRequestedBy().getEmail());
    }

    @AfterReturning(pointcut = "execution(* com.example.managementsystem.services.CongeesService.rejectCongees(..))", returning = "congees")
    public void logCongeesRejection(Congees congees) {
        logger.info("Congees request rejected by manager for employee: {}", congees.getRequestedBy().getEmail());
    }

    @AfterReturning(pointcut = "execution(* com.example.managementsystem.services.BacklogService.createBacklog(..))", returning = "backlog")
    public void logBacklogCreation(Backlog backlog) {
        logger.info("New backlog created by project manager: {}", backlog.getTitre());
    }

    @AfterReturning(pointcut = "execution(* com.example.managementsystem.services.BacklogService.updateBacklog(..))", returning = "backlog")
    public void logBacklogUpdate(Backlog backlog) {
        logger.info("Backlog updated by project manager: {}", backlog.getTitre());
    }

    @AfterReturning(pointcut = "execution(* com.example.managementsystem.services.BacklogService.deleteBacklog(..))")
    public void logBacklogDeletion(JoinPoint joinPoint) {
        Integer backlogId = (Integer) joinPoint.getArgs()[0];
        logger.info("Backlog deleted by project manager with ID: {}", backlogId);
    }

    @AfterReturning(pointcut = "execution(* com.example.managementsystem.services.SprintService.createSprint(..))", returning = "sprint")
    public void logSprintCreation(Sprint sprint) {
        logger.info("Nouveau sprint créé par le chef de projet : {}", sprint.getNom());
    }

    @AfterReturning(pointcut = "execution(* com.example.managementsystem.services.SprintService.updateSprint(..))", returning = "sprint")
    public void logSprintUpdate(Sprint sprint) {
        logger.info("Sprint mis à jour par le chef de projet : {}", sprint.getNom());
    }

    @AfterReturning(pointcut = "execution(* com.example.managementsystem.services.SprintService.deleteSprint(..))")
    public void logSprintDeletion(JoinPoint joinPoint) {
        Long sprintId = (Long) joinPoint.getArgs()[0];
        logger.info("Sprint supprimé par le chef de projet avec l'ID : {}", sprintId);
    }

    @AfterReturning(pointcut = "execution(* com.example.managementsystem.services.TaskService.createTask(..))", returning = "task")
    public void logTaskCreation(Task task) {
        logger.info("Nouvelle tâche créée : {}", task.getDescription());
    }

    @AfterReturning(pointcut = "execution(* com.example.managementsystem.services.TaskService.updateTask(..))", returning = "task")
    public void logTaskUpdate(Task task) {
        logger.info("Tâche mise à jour : {}", task.getDescription());
    }

    @AfterReturning(pointcut = "execution(* com.example.managementsystem.services.TaskService.deleteTask(..))")
    public void logTaskDeletion(JoinPoint joinPoint) {
        Long taskId = (Long) joinPoint.getArgs()[0];
        logger.info("Tâche supprimée avec l'ID : {}", taskId);
    }
}