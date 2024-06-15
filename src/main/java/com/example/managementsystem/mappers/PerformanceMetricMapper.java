package com.example.managementsystem.mappers;

import com.example.managementsystem.DTO.PerformanceMetricDTO;
import com.example.managementsystem.models.entities.PerformanceMetric;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PerformanceMetricMapper {
    PerformanceMetricDTO toDTO(PerformanceMetric performanceMetric);
    PerformanceMetric toEntity(PerformanceMetricDTO performanceMetricDTO);
}