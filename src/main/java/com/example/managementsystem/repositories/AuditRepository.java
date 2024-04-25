package com.example.managementsystem.repositories;

import com.example.managementsystem.models.entities.Audit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Long> {
    List<Audit> findByTimestampBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);
}