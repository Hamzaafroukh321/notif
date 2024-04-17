package com.example.managementsystem.controllers;

import com.example.managementsystem.models.Task;
import com.example.managementsystem.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;




@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    @PreAuthorize("hasRole('PROJECT_MANAGER') or hasRole('TEAM_MEMBER')")
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('PROJECT_MANAGER') or hasRole('TEAM_MEMBER')")
    public Task getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('PROJECT_MANAGER') or hasRole('TEAM_MEMBER')")
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('PROJECT_MANAGER') or hasRole('TEAM_MEMBER')")
    public Task updateTask(@PathVariable Long id, @RequestBody Task taskDetails) {
        return taskService.updateTask(id, taskDetails);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('PROJECT_MANAGER') or hasRole('TEAM_MEMBER')")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('PROJECT_MANAGER') or hasRole('TEAM_MEMBER')")
    public Task updateTaskStatus(@PathVariable Long id, @RequestBody Task taskDetails) {
        return taskService.updateTaskStatus(id, taskDetails);
    }
}