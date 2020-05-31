package com.amos2020.javabackend.service;

import com.amos2020.javabackend.entity.Audit;
import com.amos2020.javabackend.entity.AuditStatus;
import com.amos2020.javabackend.repository.AuditRepository;
import javassist.NotFoundException;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
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
        return repository.save(audit);
    }

    public Audit getAuditById(int auditId) throws NotFoundException {
        Optional<Audit> audit = repository.findById(auditId);
        if (!audit.isPresent()) {
            throw new NotFoundException("No audit found with id " + audit);
        }
        return audit.get();
    }

    public Audit updateAudit(Audit audit) {
        return repository.save(audit);
    }


    public Audit cancelAudit(int auditId, Date cancelDate, int cancelPerson, String cancelReason) {
        Optional<Audit> auditOptional = repository.findById(auditId);
        if (!auditOptional.isPresent())
            return null;

        Audit audit = auditOptional.get();
        audit.setStatus(AuditStatus.CANCELED);
        audit.setCancellationDate(cancelDate);
        audit.setCancellationContactPerson(cancelPerson);
        audit.setCancellationReason(cancelReason);
        return repository.save(audit);
    }


}
