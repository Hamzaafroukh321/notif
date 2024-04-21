package com.example.managementsystem.services;

import com.example.managementsystem.DTO.TaskDTO;
import com.example.managementsystem.DTO.UserDTO;
import com.example.managementsystem.exceptions.NotFoundException;
import com.example.managementsystem.mappers.TaskMapper;
import com.example.managementsystem.models.entities.Task;
import com.example.managementsystem.models.enums.TaskStatus;
import com.example.managementsystem.notification.Notification;
import com.example.managementsystem.repositories.TaskRepository;
import com.example.managementsystem.notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper, UserService userService,
                       SprintService sprintService, NotificationService notificationService) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.userService = userService;
        this.sprintService = sprintService;
        this.notificationService = notificationService;
    }

    public TaskDTO createTask(TaskDTO taskDTO) {
        // Vérifier si le sprint et l'utilisateur assigné existent
        sprintService.getSprintById(taskDTO.sprintId());
        userService.getUserByMatricule(taskDTO.assignedToMatricule());

        Task task = taskMapper.toEntity(taskDTO);
        Task savedTask = taskRepository.save(task);
        TaskDTO savedTaskDTO = taskMapper.toDTO(savedTask);

        sendNotificationToAssignedUser(savedTaskDTO, "Nouvelle tâche créée : " + savedTaskDTO.description());

        return savedTaskDTO;
    }

    public TaskDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tâche non trouvée avec l'ID : " + id));
        return taskMapper.toDTO(task);
    }

    public TaskDTO updateTask(Long id, TaskDTO updatedTaskDTO) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tâche non trouvée avec l'ID : " + id));

        // Vérifier si le sprint et l'utilisateur assigné existent
        sprintService.getSprintById(updatedTaskDTO.sprintId());
        userService.getUserByMatricule(updatedTaskDTO.assignedToMatricule());

        taskMapper.updateTaskFromDTO(updatedTaskDTO, existingTask);
        Task savedTask = taskRepository.save(existingTask);
        TaskDTO savedTaskDTO = taskMapper.toDTO(savedTask);

        sendNotificationToAssignedUser(savedTaskDTO, "Tâche mise à jour : " + savedTaskDTO.description());

        return savedTaskDTO;
    }

    public TaskDTO updateTaskStatus(Long id, TaskStatus status) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tâche non trouvée avec l'ID : " + id));

        existingTask.setStatus(status);
        Task savedTask = taskRepository.save(existingTask);
        TaskDTO savedTaskDTO = taskMapper.toDTO(savedTask);

        sendNotificationToAssignedUser(savedTaskDTO, "Statut de la tâche mis à jour : " + savedTaskDTO.description());

        return savedTaskDTO;
    }

    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tâche non trouvée avec l'ID : " + id));

        taskRepository.delete(task);

        TaskDTO deletedTaskDTO = taskMapper.toDTO(task);
        sendNotificationToAssignedUser(deletedTaskDTO, "Tâche supprimée : " + deletedTaskDTO.description());
    }

    public List<TaskDTO> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(taskMapper::toDTO)
                .collect(Collectors.toList());
    }

    private void sendNotificationToAssignedUser(TaskDTO taskDTO, String message) {
        UserDTO assignedUser = userService.getUserByMatricule(taskDTO.assignedToMatricule());

        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setRecipient(assignedUser.email());

        notificationService.sendNotification(notification);
    }
}