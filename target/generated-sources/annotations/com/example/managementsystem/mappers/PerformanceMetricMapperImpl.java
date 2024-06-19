package com.example.managementsystem.mappers;

import com.example.managementsystem.DTO.PerformanceMetricDTO;
import com.example.managementsystem.models.entities.PerformanceMetric;
import java.time.Instant;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-13T13:46:43+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class PerformanceMetricMapperImpl implements PerformanceMetricMapper {

    @Override
    public PerformanceMetricDTO toDTO(PerformanceMetric performanceMetric) {
        if ( performanceMetric == null ) {
            return null;
        }

        Long id = null;
        Instant timestamp = null;
        double cpuUsage = 0.0d;
        long usedMemory = 0L;
        long freeMemory = 0L;
        long totalMemory = 0L;
        int activeThreads = 0;
        int totalThreads = 0;
        int loadedClassesCount = 0;
        long totalLoadedClassesCount = 0L;
        long unloadedClassesCount = 0L;
        long nonHeapMemoryUsage = 0L;
        long totalCompilationTime = 0L;

        id = performanceMetric.getId();
        timestamp = performanceMetric.getTimestamp();
        cpuUsage = performanceMetric.getCpuUsage();
        usedMemory = performanceMetric.getUsedMemory();
        freeMemory = performanceMetric.getFreeMemory();
        totalMemory = performanceMetric.getTotalMemory();
        activeThreads = performanceMetric.getActiveThreads();
        totalThreads = performanceMetric.getTotalThreads();
        loadedClassesCount = performanceMetric.getLoadedClassesCount();
        totalLoadedClassesCount = performanceMetric.getTotalLoadedClassesCount();
        unloadedClassesCount = performanceMetric.getUnloadedClassesCount();
        nonHeapMemoryUsage = performanceMetric.getNonHeapMemoryUsage();
        totalCompilationTime = performanceMetric.getTotalCompilationTime();

        PerformanceMetricDTO performanceMetricDTO = new PerformanceMetricDTO( id, timestamp, cpuUsage, usedMemory, freeMemory, totalMemory, activeThreads, totalThreads, loadedClassesCount, totalLoadedClassesCount, unloadedClassesCount, nonHeapMemoryUsage, totalCompilationTime );

        return performanceMetricDTO;
    }

    @Override
    public PerformanceMetric toEntity(PerformanceMetricDTO performanceMetricDTO) {
        if ( performanceMetricDTO == null ) {
            return null;
        }

        PerformanceMetric.PerformanceMetricBuilder performanceMetric = PerformanceMetric.builder();

        performanceMetric.id( performanceMetricDTO.id() );
        performanceMetric.timestamp( performanceMetricDTO.timestamp() );
        performanceMetric.cpuUsage( performanceMetricDTO.cpuUsage() );
        performanceMetric.usedMemory( performanceMetricDTO.usedMemory() );
        performanceMetric.freeMemory( performanceMetricDTO.freeMemory() );
        performanceMetric.totalMemory( performanceMetricDTO.totalMemory() );
        performanceMetric.activeThreads( performanceMetricDTO.activeThreads() );
        performanceMetric.totalThreads( performanceMetricDTO.totalThreads() );
        performanceMetric.loadedClassesCount( performanceMetricDTO.loadedClassesCount() );
        performanceMetric.totalLoadedClassesCount( performanceMetricDTO.totalLoadedClassesCount() );
        performanceMetric.unloadedClassesCount( performanceMetricDTO.unloadedClassesCount() );
        performanceMetric.nonHeapMemoryUsage( performanceMetricDTO.nonHeapMemoryUsage() );
        performanceMetric.totalCompilationTime( performanceMetricDTO.totalCompilationTime() );

        return performanceMetric.build();
    }
}
