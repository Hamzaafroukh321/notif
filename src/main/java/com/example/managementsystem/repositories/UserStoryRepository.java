package com.example.managementsystem.repositories;


import com.example.managementsystem.models.entities.UserStory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStoryRepository extends JpaRepository<UserStory, Long> {
    Page<UserStory> findAllByBacklogId(Integer backlogId, Pageable pageable);
}

