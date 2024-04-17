package com.example.managementsystem.services;

import com.example.managementsystem.controllers.NotificationController;
import com.example.managementsystem.exceptions.BadRequestException;
import com.example.managementsystem.exceptions.NotFoundException;
import com.example.managementsystem.models.Notification;
import com.example.managementsystem.models.Task;
import com.example.managementsystem.models.User;
import com.example.managementsystem.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final NotificationController notificationController;

    @Autowired
    public TaskService(TaskRepository taskRepository, NotificationController notificationController) {
        this.taskRepository = taskRepository;
        this.notificationController = notificationController;
    }

    public Task createTask(Task task) {
        if (task.getDescription() == null || task.getStatus() == null || task.getSprint() == null || task.getAssignedTo() == null) {
            throw new BadRequestException("Les informations de la tâche sont incomplètes.");
        }
        Task createdTask = taskRepository.save(task);
        sendNotificationToAssignedUser(createdTask, "Nouvelle tâche créée : " + task.getDescription());
        return createdTask;
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tâche non trouvée avec l'ID : " + id));
    }

    public Task updateTask(Long id, Task updatedTask) {
        Task existingTask = getTaskById(id);
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setStatus(updatedTask.getStatus());
        existingTask.setSprint(updatedTask.getSprint());
        existingTask.setAssignedTo(updatedTask.getAssignedTo());
        Task savedTask = taskRepository.save(existingTask);
        sendNotificationToAssignedUser(savedTask, "Tâche mise à jour : " + savedTask.getDescription());
        return savedTask;
    }

    // update only the status of the task and send a notification to the assigned user to inform him about the status change
    public Task updateTaskStatus(Long id, Task updatedTask) {
        Task existingTask = getTaskById(id);
        existingTask.setStatus(updatedTask.getStatus());
        Task savedTask = taskRepository.save(existingTask);
        sendNotificationToAssignedUser(savedTask, "Statut de la tâche mis à jour : " + savedTask.getDescription());
        return savedTask;
    }

    public void deleteTask(Long id) {
        Task task = getTaskById(id);
        taskRepository.delete(task);
        sendNotificationToAssignedUser(task, "Tâche supprimée : " + task.getDescription());
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    private void sendNotificationToAssignedUser(Task task, String message) {
        User assignedUser = task.getAssignedTo();
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setTimestamp(LocalDateTime.now());
        notification.setRecipient(assignedUser.getEmail());
        notificationController.sendNotification(notification);
    }


}