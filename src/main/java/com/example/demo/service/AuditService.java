package com.example.demo.service;

import com.example.demo.model.AuditLog;
import com.example.demo.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AuditService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    public void logAction(String action) {
        if (action == null || action.isBlank()) {
            throw new IllegalArgumentException("Audit action must not be empty");
        }

        AuditLog log = new AuditLog();
        log.setAction(action);
        log.setTimestamp(new Date());
        log.setPerformedBy(getCurrentUser());

        auditLogRepository.save(log);
    }

    public List<AuditLog> getAllLogs() {
        return auditLogRepository.findAll();
    }

    private String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        return "SYSTEM";
    }
}
