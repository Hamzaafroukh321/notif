package com.example.managementsystem.services;

import com.example.managementsystem.DTO.TaskDTO;
import com.example.managementsystem.DTO.UserDTO;
import com.example.managementsystem.config.AuditUtil;
import com.example.managementsystem.exceptions.NotFoundException;
import com.example.managementsystem.mappers.TaskMapper;
import com.example.managementsystem.mappers.UserMapper;
import com.example.managementsystem.models.entities.Task;
import com.example.managementsystem.models.enums.TaskStatus;
import com.example.managementsystem.notification.Notification;
import com.example.managementsystem.repositories.TaskRepository;
import com.example.managementsystem.notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final UserService userService;
    private final SprintService sprintService;
    private final NotificationService notificationService;

    private final AuditUtil auditUtil;

    private final UserMapper userMapper;

    @Autowired
    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper, UserService userService,
                       SprintService sprintService, NotificationService notificationService, AuditUtil auditUtil, UserMapper userMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.userService = userService;
        this.sprintService = sprintService;
        this.notificationService = notificationService;
        this.auditUtil = auditUtil;
        this.userMapper = userMapper;
    }

    @PreAuthorize("hasAuthority('ASSIGN_TASKS')")
    public TaskDTO createTask(TaskDTO taskDTO) {
        if (taskDTO.sprintId() == null || taskDTO.assignedToMatricule() == null) {
            throw new IllegalArgumentException("Sprint ID and User Matricule must not be null");
        }

        sprintService.getSprintById(taskDTO.sprintId());
        userService.getUserByMatricule(taskDTO.assignedToMatricule());

        Task task = taskMapper.toEntity(taskDTO);
        Task savedTask = taskRepository.save(task);
        TaskDTO savedTaskDTO = taskMapper.toDTO(savedTask);

        auditUtil.logAudit("CREATE", "Created Task with details: " + savedTask.toString());
        sendNotificationToAssignedUser(savedTaskDTO, "Nouvelle tâche créée : " + savedTaskDTO.description());

        return savedTaskDTO;
    }

    @PreAuthorize("hasAuthority('MANAGE_TASKS')")
    public TaskDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tâche non trouvée avec l'ID : " + id));
        return taskMapper.toDTO(task);
    }

    @PreAuthorize("hasAuthority('MANAGE_TASKS')")
    public TaskDTO updateTask(Long id, TaskDTO updatedTaskDTO) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tâche non trouvée avec l'ID : " + id));

        sprintService.getSprintById(updatedTaskDTO.sprintId());
        UserDTO userDTO = userService.getUserByMatricule(updatedTaskDTO.assignedToMatricule());

        TaskDTO oldTaskDTO = taskMapper.toDTO(existingTask);

        existingTask.setAssignedTo(userMapper.toEntity(userDTO));
        taskMapper.updateTaskFromDTO(updatedTaskDTO, existingTask);
        Task savedTask = taskRepository.save(existingTask);
        TaskDTO savedTaskDTO = taskMapper.toDTO(savedTask);

        auditUtil.logAudit("UPDATE", "Updated Task with ID: " + id + " from details: " + oldTaskDTO.toString() + " to details: " + savedTaskDTO.toString());
        sendNotificationToAssignedUser(savedTaskDTO, "Tâche mise à jour : " + savedTaskDTO.description());

        return savedTaskDTO;
    }


    @PreAuthorize("hasAuthority('MANAGE_TASKS')")
    public TaskDTO updateTaskStatus(Long id, TaskStatus status) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tâche non trouvée avec l'ID : " + id));

        TaskDTO oldTaskDTO = taskMapper.toDTO(existingTask);

        existingTask.setStatus(status);
        Task savedTask = taskRepository.save(existingTask);
        TaskDTO savedTaskDTO = taskMapper.toDTO(savedTask);

        auditUtil.logAudit("UPDATE_STATUS", "Updated Task Status with ID: " + id + " from status: " + oldTaskDTO.status() + " to status: " + status);
        sendNotificationToAssignedUser(savedTaskDTO, "Statut de la tâche mis à jour : " + savedTaskDTO.description());

        return savedTaskDTO;
    }


    @PreAuthorize("hasAuthority('MANAGE_TASKS')")
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tâche non trouvée avec l'ID : " + id));

        taskRepository.delete(task);

        auditUtil.logAudit("DELETE", "Deleted Task with ID: " + id + " with details: " + task.toString());
        TaskDTO deletedTaskDTO = taskMapper.toDTO(task);
        sendNotificationToAssignedUser(deletedTaskDTO, "Tâche supprimée : " + deletedTaskDTO.description());
    }


    @PreAuthorize("hasAuthority('MANAGE_TASKS')")
    public List<TaskDTO> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(taskMapper::toDTO)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasAuthority('MANAGE_TASKS')")
    public Page<TaskDTO> getTasksBySprintId(Long sprintId, Pageable pageable) {
        Page<Task> tasks = taskRepository.findAllBySprintId(sprintId, pageable);
        return tasks.map(taskMapper::toDTO);
    }

    @PreAuthorize("hasAuthority('MANAGE_TASKS')")
    public Page<TaskDTO> getTasksByMatricule(Long matricule, Pageable pageable) {
        Page<Task> tasks = taskRepository.findAllByAssignedToMatricule(matricule, pageable);
        return tasks.map(taskMapper::toDTO);
    }

    private void sendNotificationToAssignedUser(TaskDTO taskDTO, String message) {
        UserDTO assignedUser = userService.getUserByMatricule(taskDTO.assignedToMatricule());

        String recipientMatricule = assignedUser.email(); // Utiliser directement le matricule
        notificationService.sendNotification(message, recipientMatricule);
    }

}
