package com.example.managementsystem.DTO;

import java.time.Instant;

import lombok.Data;


import java.time.Instant;

public record PerformanceMetricDTO(
        Long id,
        Instant timestamp,
        double cpuUsage,
        long usedMemory,
        long freeMemory,
        long totalMemory,
        int activeThreads,
        int totalThreads,
        int loadedClassesCount,
        long totalLoadedClassesCount,
        long unloadedClassesCount,
        long nonHeapMemoryUsage,
        long totalCompilationTime
) {}