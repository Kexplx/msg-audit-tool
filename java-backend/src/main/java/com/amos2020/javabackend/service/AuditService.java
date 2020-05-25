package com.amos2020.javabackend.service;

import com.amos2020.javabackend.entity.Audit;
import com.amos2020.javabackend.entity.AuditStatus;
import com.amos2020.javabackend.repository.AuditRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;

public class AuditService {

    @Autowired
    AuditRepository repository;


    private static AuditService instance;

    public static AuditService getInstance() {
        if (instance == null) {
            instance = new AuditService();
        }
        return instance;
    }

    public Audit createAudit(String auditName, Date startDate, Date endDate)  {
        Audit audit = new Audit();
        audit.setName(auditName);
        audit.setStartDate(startDate);
        audit.setCreationDate(Timestamp.from(Instant.now()));
        audit.setStatus(AuditStatus.OPEN);
        if (endDate != null) {
            audit.setEndDate(endDate);
        }
        repository.save(audit);
        return audit;
    }
}
