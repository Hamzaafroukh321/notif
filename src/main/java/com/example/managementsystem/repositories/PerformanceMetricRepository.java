package com.example.managementsystem.repositories;

import com.example.managementsystem.models.entities.PerformanceMetric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;





@Repository
public interface PerformanceMetricRepository extends JpaRepository<PerformanceMetric, Long> {
    PerformanceMetric findTopByOrderByTimestampDesc();
}