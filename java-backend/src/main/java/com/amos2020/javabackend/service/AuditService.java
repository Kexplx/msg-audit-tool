package com.amos2020.javabackend.service;

import com.amos2020.javabackend.entity.Audit;
import com.amos2020.javabackend.entity.AuditStatus;
import com.amos2020.javabackend.repository.AuditRepository;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class AuditService {

    final AuditRepository repository;

    public AuditService(AuditRepository repository) {
        this.repository = repository;
    }

    public Audit createAudit(String auditName, Date startDate, Date endDate) {
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

    public Audit getAuditById(int auditId) throws NotFoundException {
        Optional<Audit> audit = repository.findById(auditId);
        if (!audit.isPresent()) {
            throw new NotFoundException("No audit found with id " + audit);
        }
        return audit.get();
    }

    public List<Audit> getAll() {
        return repository.findAll();
    }

    public Audit updateAudit(Audit audit) {
        return repository.save(audit);
    }
}
