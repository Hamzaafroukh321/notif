package com.example.managementsystem.models.entities;



import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PerformanceMetric {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Instant timestamp;
    private double cpuUsage;
    private long usedMemory;
    private long freeMemory;
    private long totalMemory;
    private int activeThreads;
    private int totalThreads;
    private int loadedClassesCount;
    private long totalLoadedClassesCount;
    private long unloadedClassesCount;
    private long nonHeapMemoryUsage;
    private long totalCompilationTime;
}