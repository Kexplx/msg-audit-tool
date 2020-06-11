package com.amos2020.javabackend.service;

import com.amos2020.javabackend.entity.AuditContactPerson;
import com.amos2020.javabackend.repository.AuditContactPersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuditContactPersonService {

    private final AuditContactPersonRepository repository;

    public AuditContactPersonService(AuditContactPersonRepository repository) {
        this.repository = repository;
    }

    public void createAuditContactPersons(int auditId, List<Integer> contactPersons) {
        for (int contactPersonId : contactPersons) {
            createAuditContactPerson(auditId, contactPersonId);
        }
    }

    public void createAuditContactPerson(int auditId, int contactPersonId) {
        AuditContactPerson auditContactPerson = new AuditContactPerson();
        auditContactPerson.setAuditId(auditId);
        auditContactPerson.setContactPersonId(contactPersonId);
        repository.save(auditContactPerson);
    }

    @Transactional
    public void deleteByAuditIdAndContactPersonId(int auditId, int contactPersonId) {
        repository.deleteByAuditIdAndContactPersonId(auditId, contactPersonId);
    }

    @Transactional
    public AuditContactPerson exists(int auditId, int contactPersonId) {
        return repository.findFirstByAuditIdAndContactPersonId(auditId, contactPersonId);
    }
}
