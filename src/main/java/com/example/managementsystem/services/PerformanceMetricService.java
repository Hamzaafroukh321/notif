package com.example.managementsystem.services;


import com.example.managementsystem.DTO.PerformanceMetricDTO;
import com.example.managementsystem.mappers.PerformanceMetricMapper;
import com.example.managementsystem.models.entities.PerformanceMetric;
import com.example.managementsystem.repositories.PerformanceMetricRepository;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Slf4j
public class PerformanceMetricService {
    private final PerformanceMetricRepository performanceMetricRepository;
    private final PerformanceMetricMapper performanceMetricMapper;
    private final MeterRegistry meterRegistry;

    public PerformanceMetricService(PerformanceMetricRepository performanceMetricRepository,
                                    PerformanceMetricMapper performanceMetricMapper,
                                    MeterRegistry meterRegistry) {
        this.performanceMetricRepository = performanceMetricRepository;
        this.performanceMetricMapper = performanceMetricMapper;
        this.meterRegistry = meterRegistry;
    }

    @Scheduled(fixedRate = 5000)
    public void collectPerformanceMetrics() {
        log.info("Collecting performance metrics...");

        double cpuUsage = meterRegistry.get("system.cpu.usage").gauge().value();
        long usedMemory = (long) meterRegistry.get("jvm.memory.used").gauge().value();
        long maxMemory = (long) meterRegistry.get("jvm.memory.max").gauge().value();
        long freeMemory = maxMemory - usedMemory;
        int threadCount = (int) meterRegistry.get("jvm.threads.live").gauge().value();
        int peakThreadCount = (int) meterRegistry.get("jvm.threads.peak").gauge().value();
        int loadedClassesCount = (int) meterRegistry.get("jvm.classes.loaded").gauge().value();
        long totalLoadedClassesCount = (long) meterRegistry.get("jvm.classes.loaded.total").counter().count();
        long unloadedClassesCount = (long) meterRegistry.get("jvm.classes.unloaded").counter().count();
        long nonHeapMemoryUsage = (long) meterRegistry.get("jvm.memory.used").tags("area", "nonheap").gauge().value();
        long totalCompilationTime = (long) meterRegistry.get("jvm.compilation.time.total").counter().count();

        PerformanceMetric metric = PerformanceMetric.builder()
                .timestamp(Instant.now())
                .cpuUsage(cpuUsage)
                .usedMemory(usedMemory)
                .freeMemory(freeMemory)
                .totalMemory(maxMemory)
                .activeThreads(threadCount)
                .totalThreads(peakThreadCount)
                .loadedClassesCount(loadedClassesCount)
                .totalLoadedClassesCount(totalLoadedClassesCount)
                .unloadedClassesCount(unloadedClassesCount)
                .nonHeapMemoryUsage(nonHeapMemoryUsage)
                .totalCompilationTime(totalCompilationTime)
                .build();

        performanceMetricRepository.save(metric);

        log.info("Performance metrics collected: {}", metric);
    }

    public PerformanceMetricDTO getLatestMetric() {
        log.info("Retrieving latest performance metric...");

        PerformanceMetric latestMetric = performanceMetricRepository.findTopByOrderByTimestampDesc();
        return performanceMetricMapper.toDTO(latestMetric);
    }
}