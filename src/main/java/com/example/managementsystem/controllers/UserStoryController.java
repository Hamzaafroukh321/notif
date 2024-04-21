package com.example.managementsystem.controllers;

import com.example.managementsystem.DTO.UserStoryDTO;
import com.example.managementsystem.services.UserStoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/userstories")
public class UserStoryController {
    private final UserStoryService userStoryService;

    @Autowired
    public UserStoryController(UserStoryService userStoryService) {
        this.userStoryService = userStoryService;
    }

    @GetMapping
    public List<UserStoryDTO> getAllUserStories() {
        return userStoryService.getAllUserStories();
    }

    @GetMapping("/{id}")
    public UserStoryDTO getUserStoryById(@PathVariable Long id) {
        return userStoryService.getUserStoryById(id);
    }

    @PostMapping
    public UserStoryDTO createUserStory(@RequestBody UserStoryDTO userStoryDTO) {
        return userStoryService.createUserStory(userStoryDTO);
    }

    @PutMapping("/{id}")
    public UserStoryDTO updateUserStory(@PathVariable Long id, @RequestBody UserStoryDTO userStoryDTO) {
        return userStoryService.updateUserStory(id, userStoryDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteUserStory(@PathVariable Long id) {
        userStoryService.deleteUserStory(id);
    }
}