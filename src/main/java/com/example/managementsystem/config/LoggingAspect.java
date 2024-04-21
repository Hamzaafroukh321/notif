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


@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * Pointcut that matches all repositories, services and Web REST endpoints.
     */
    @Pointcut("within(@org.springframework.stereotype.Repository *)" +
            " || within(@org.springframework.stereotype.Service *)" +
            " || within(@org.springframework.web.bind.annotation.RestController *)")
    public void springBeanPointcut() {
        // Méthode vide car c'est juste un Pointcut, les implémentations sont dans les advices.
    }

    @Pointcut("within(com.example.managementsystem.controllers..*)" +
            " || within(com.example.managementsystem.services..*)" +
            " || within(com.example.managementsystem.repositories..*)")
    public void applicationPackagePointcut() {
        // Méthode vide car c'est juste un Pointcut, les implémentations sont dans les advices.
    }
    /**
     * Advice that logs methods throwing exceptions.
     *
     * @param joinPoint join point for advice
     * @param e         exception
     */
    @AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        logger.error("Exception in {}.{}() with cause = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), e.getCause() != null? e.getCause() : "NULL");
    }






    @AfterReturning(pointcut = "execution(* com.example.managementsystem.services.UserService.saveUser(..))", returning = "userDTO")
    public void logUserCreation(UserDTO userDTO) {
        logger.info("User created successfully: {}", userDTO.email());
    }

    @AfterReturning(pointcut = "execution(* com.example.managementsystem.services.UserService.updateUser(..))", returning = "userDTO")
    public void logUserUpdate(UserDTO userDTO) {
        logger.info("User updated successfully: {}", userDTO.email());
    }

    @AfterReturning(pointcut = "execution(* com.example.managementsystem.services.UserService.deleteUserByMatricule(..))", argNames = "matricule")
    public void logUserDeletion(Long matricule) {
        logger.info("User deleted successfully with matricule: {}", matricule);
    }

    @AfterReturning(pointcut = "execution(* com.example.managementsystem.services.UserService.saveAndFlushUser(..))", returning = "userDTO")
    public void logUserSaveAndFlush(UserDTO userDTO) {
        logger.info("User saved and flushed successfully: {}", userDTO.email());
    }

    @AfterReturning(pointcut = "execution(* com.example.managementsystem.services.ProjetService.createProjet(..))", returning = "projetDTO")
    public void logProjetCreation(ProjetDTO projetDTO) {
        logger.info("Projet created successfully: {}", projetDTO.nom());
    }

    @AfterReturning(pointcut = "execution(* com.example.managementsystem.services.ProjetService.updateProjet(..))", returning = "projetDTO")
    public void logProjetUpdate(ProjetDTO projetDTO) {
        logger.info("Projet updated successfully: {}", projetDTO.nom());
    }

    @AfterReturning(pointcut = "execution(* com.example.managementsystem.services.ProjetService.deleteProjetById(..))", argNames = "id")
    public void logProjetDeletion(Long id) {
        logger.info("Projet deleted successfully with id: {}", id);
    }

    @AfterReturning(pointcut = "execution(* com.example.managementsystem.services.SprintService.createSprint(..))", returning = "sprintDTO")
    public void logSprintCreation(SprintDTO sprintDTO) {
        logger.info("Sprint created successfully: {}", sprintDTO.nom());
    }

    @AfterReturning(pointcut = "execution(* com.example.managementsystem.services.SprintService.updateSprint(..))", returning = "sprintDTO")
    public void logSprintUpdate(SprintDTO sprintDTO) {
        logger.info("Sprint updated successfully: {}", sprintDTO.nom());
    }

    @AfterReturning(pointcut = "execution(* com.example.managementsystem.services.SprintService.deleteSprintById(..))", argNames = "id")
    public void logSprintDeletion(Long id) {
        logger.info("Sprint deleted successfully with id: {}", id);
    }

    @AfterReturning(pointcut = "execution(* com.example.managementsystem.services.BacklogService.createBacklog(..))", returning = "backlogDTO")
    public void logBacklogCreation(BacklogDTO backlogDTO) {
        logger.info("Backlog created successfully: {}", backlogDTO.titre());
    }

    @AfterReturning(pointcut = "execution(* com.example.managementsystem.services.BacklogService.updateBacklog(..))", returning = "backlogDTO")
    public void logBacklogUpdate(BacklogDTO backlogDTO) {
        logger.info("Backlog updated successfully: {}", backlogDTO.titre());
    }

    @AfterReturning(pointcut = "execution(* com.example.managementsystem.services.BacklogService.deleteBacklogById(..))", argNames = "id")
    public void logBacklogDeletion(Integer id) {
        logger.info("Backlog deleted successfully with id: {}", id);
    }

    @AfterReturning(pointcut = "execution(* com.example.managementsystem.services.TaskService.createTask(..))", returning = "taskDTO")
    public void logTaskCreation(TaskDTO taskDTO) {
        logger.info("Tâche créée avec succès : {}", taskDTO.description());
    }

    @AfterReturning(pointcut = "execution(* com.example.managementsystem.services.TaskService.updateTask(..))", returning = "taskDTO")
    public void logTaskUpdate(TaskDTO taskDTO) {
        logger.info("Tâche mise à jour avec succès : {}", taskDTO.description());
    }

    @AfterReturning(pointcut = "execution(* com.example.managementsystem.services.TaskService.updateTaskStatus(..))", returning = "taskDTO")
    public void logTaskStatusUpdate(TaskDTO taskDTO) {
        logger.info("Statut de la tâche mis à jour avec succès : {}", taskDTO.description());
    }

    @AfterReturning(pointcut = "execution(* com.example.managementsystem.services.TaskService.deleteTask(..))", argNames = "id")
    public void logTaskDeletion(Long id) {
        logger.info("Tâche supprimée avec succès, ID : {}", id);
    }

    @AfterReturning(pointcut = "execution(* com.example.managementsystem.services.CongeesService.createCongees(..))", returning = "congeDTO")
    public void logCongeCreation(CongeDTO congeDTO) {
        logger.info("Congé créé avec succès par Matricule : {}", congeDTO.requestedByMatricule());
    }

    @AfterReturning(pointcut = "execution(* com.example.managementsystem.services.CongeesService.approveCongees(..))", returning = "congeDTO")
    public void logCongeApproval(CongeDTO congeDTO) {
        logger.info("Congé approuvé pour Matricule : {}",  congeDTO.requestedByMatricule());
    }

    @AfterReturning(pointcut = "execution(* com.example.managementsystem.services.CongeesService.rejectCongees(..))", returning = "congeDTO")
    public void logCongeRejection(CongeDTO congeDTO) {
        logger.info("Congé rejeté avec succès pour Matricule : {}",  congeDTO.requestedByMatricule());
    }

    @AfterReturning(pointcut = "execution(* com.example.managementsystem.services.CongeesService.updateCongees(..))", returning = "congeDTO")
    public void logCongeUpdate(CongeDTO congeDTO) {
        logger.info("Congé mis à jour avec succès. ID : {}",  congeDTO.id());
    }

    @AfterReturning(pointcut = "execution(* com.example.managementsystem.services.CongeesService.deleteCongees(..))", argNames = "id")
    public void logCongeDeletion(Long id) {
        logger.info("Congé supprimé avec succès. ID : {}", id);
    }

    @AfterReturning(pointcut = "execution(* com.example.managementsystem.services.UserStoryService.createUserStory(..))", returning = "userStoryDTO")
    public void logUserStoryCreation(UserStoryDTO userStoryDTO) {
        logger.info("User story created successfully. ID: {}", userStoryDTO.id());
    }

    @AfterReturning(pointcut = "execution(* com.example.managementsystem.services.UserStoryService.updateUserStory(..))", returning = "userStoryDTO")
    public void logUserStoryUpdate(UserStoryDTO userStoryDTO) {
        logger.info("User story updated successfully. ID: {}", userStoryDTO.id());
    }

    @AfterReturning(pointcut = "execution(* com.example.managementsystem.services.UserStoryService.deleteUserStory(..))", argNames = "id")
    public void logUserStoryDeletion(Long id) {
        logger.info("User story deleted successfully. ID: {}", id);
    }
}