package com.example.managementsystem.config;

import com.example.managementsystem.DTO.AuditDTO;
import com.example.managementsystem.DTO.TaskDTO;
import com.example.managementsystem.models.entities.User;
import com.example.managementsystem.models.enums.TaskStatus;
import com.example.managementsystem.services.AuditService;
import com.example.managementsystem.services.TaskService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

@Aspect
@Component
public class AuditAspect {

    private final AuditUtil auditUtil;

    @Autowired
    public AuditAspect(AuditUtil auditUtil) {
        this.auditUtil = auditUtil;
    }

    @AfterReturning(pointcut = "execution(* com.example.service.*.create*(..))", returning = "result")
    public void auditCreate(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        String entity = joinPoint.getTarget().getClass().getSimpleName();
        String description = "Created " + entity + " with details: " + result.toString();
        auditUtil.logAudit("CREATE", description);
    }

    @AfterReturning(pointcut = "execution(* com.example.service.*.update*(..))", returning = "result")
    public void auditUpdate(JoinPoint joinPoint, Object result) {
        Object[] args = joinPoint.getArgs();
        String methodName = joinPoint.getSignature().getName();
        String entity = joinPoint.getTarget().getClass().getSimpleName();
        String description = "Updated " + entity + " with ID: " + args[0] + " to details: " + result.toString();
        auditUtil.logAudit("UPDATE", description);
    }

    @After("execution(* com.example.service.*.delete*(..))")
    public void auditDelete(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String methodName = joinPoint.getSignature().getName();
        String entity = joinPoint.getTarget().getClass().getSimpleName();
        String description = "Deleted " + entity + " with ID: " + args[0];
        auditUtil.logAudit("DELETE", description);
    }
}
