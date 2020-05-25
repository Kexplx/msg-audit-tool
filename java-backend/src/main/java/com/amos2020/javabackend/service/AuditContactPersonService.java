package com.amos2020.javabackend.service;

import com.amos2020.javabackend.entity.AuditContactPerson;
import com.amos2020.javabackend.repository.AuditContactPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AuditContactPersonService {

    private static AuditContactPersonService instance;

    @Autowired
    private AuditContactPersonRepository repository;

    public static AuditContactPersonService getInstance() {
        if (instance == null) {
            instance = new AuditContactPersonService();
        }
        return instance;
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
}
