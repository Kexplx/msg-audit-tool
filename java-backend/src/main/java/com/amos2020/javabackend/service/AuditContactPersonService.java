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

    /**
     * Create multiple AuditContactPerson object by auditId and a list of contactPerson ids
     *
     * @param auditId        int
     * @param contactPersons List<Integer>
     */
    public void createAuditContactPersons(int auditId, List<Integer> contactPersons) {
        for (int contactPersonId : contactPersons) {
            createAuditContactPerson(auditId, contactPersonId);
        }
    }

    /**
     * Create a AuditContactPerson by audit id and contactPerson id
     *
     * @param auditId         int
     * @param contactPersonId int
     */
    public void createAuditContactPerson(int auditId, int contactPersonId) {
        AuditContactPerson auditContactPerson = new AuditContactPerson();
        auditContactPerson.setAuditId(auditId);
        auditContactPerson.setContactPersonId(contactPersonId);
        repository.save(auditContactPerson);
    }

    /**
     * Delete a specific AuditContactPerson by a given audit id and a given contactPerson id
     *
     * @param auditId         int
     * @param contactPersonId int
     */
    @Transactional
    public void deleteByAuditIdAndContactPersonId(int auditId, int contactPersonId) {
        repository.deleteByAuditIdAndContactPersonId(auditId, contactPersonId);
    }

    /**
     * Check if a specific AuditContactPerson exists
     *
     * @param auditId         int
     * @param contactPersonId int
     * @return AuditContactPerson or null (if not found)
     */
    @Transactional
    public AuditContactPerson exists(int auditId, int contactPersonId) {
        return repository.findFirstByAuditIdAndContactPersonId(auditId, contactPersonId);
    }
}
