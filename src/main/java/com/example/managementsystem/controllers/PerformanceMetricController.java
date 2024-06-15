package com.example.managementsystem.controllers;


import com.example.managementsystem.DTO.PerformanceMetricDTO;
import com.example.managementsystem.services.PerformanceMetricService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/metrics")
@Slf4j
public class PerformanceMetricController {
    private final PerformanceMetricService performanceMetricService;

    public PerformanceMetricController(PerformanceMetricService performanceMetricService) {
        this.performanceMetricService = performanceMetricService;
    }

    @GetMapping("/latest")
    public PerformanceMetricDTO getLatestMetric() {
        log.info("Received request to get latest performance metric");
        return performanceMetricService.getLatestMetric();
    }
}