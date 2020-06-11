package com.amos2020.javabackend.rest_service.controller;

import com.amos2020.javabackend.entity.*;
import com.amos2020.javabackend.rest_service.response.BasicAuditResponse;
import com.amos2020.javabackend.service.*;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuditController {

    final AuditService auditService;
    final ScopeService scopeService;
    final AuditContactPersonService auditContactPersonService;
    final FacCritService facCritService;
    final ContactPersonService contactPersonService;

    public AuditController(AuditService auditService,
                           ScopeService scopeService,
                           AuditContactPersonService auditContactPersonService,
                           FacCritService facCritService,
                           ContactPersonService contactPersonService) {
        this.auditService = auditService;
        this.scopeService = scopeService;
        this.auditContactPersonService = auditContactPersonService;
        this.facCritService = facCritService;
        this.contactPersonService = contactPersonService;
    }

    public BasicAuditResponse createAudit(String auditName, Date startDate, Date endDate, List<Integer> scope, List<Integer> contactPeople) throws NotFoundException {
        // Create audit and save in database
        Audit audit = auditService.createAudit(auditName, startDate, endDate);
        // Create Scope
        scopeService.createScopeByFactorCriteriaList(audit.getId(), scope);
        // Create AuditContactPerson if needed
        auditContactPersonService.createAuditContactPersons(audit.getId(), contactPeople);
        // Create Response object
        return new BasicAuditResponse(audit, facCritService.getAllById(scope), contactPersonService.getAllByIds(contactPeople));
    }

    public BasicAuditResponse updateAudit(int auditId, String auditName, Date startDate, Date endDate) throws NotFoundException {
        // check if audit exists
        Audit audit = auditService.getAuditById(auditId);
        audit.setName(auditName);
        audit.setStartDate(startDate);
        audit.setEndDate(endDate);
        audit = auditService.updateAudit(audit);

        return buildBasicResponse(audit);
    }

    public BasicAuditResponse addContactPersonToAudit(int auditId, int contactPersonId) throws NotFoundException {
        // check if audit exists
        Audit audit = auditService.getAuditById(auditId);
        ContactPerson contactPerson = contactPersonService.getContactPersonById(contactPersonId);
        if (auditContactPersonService.exists(auditId, contactPersonId) == null) {
            AuditContactPerson auditContactPerson = new AuditContactPerson();
            auditContactPerson.setContactPersonId(contactPerson.getId());
            auditContactPerson.setAuditId(audit.getId());
            audit.getAuditContactPeopleById().add(auditContactPerson);
            auditService.updateAudit(audit);
        }
        return buildBasicResponse(audit);
    }

    public BasicAuditResponse removeContactPersonFromAudit(int auditId, int contactPersonId) throws NotFoundException {
        // check if audit exists
        Audit audit = auditService.getAuditById(auditId);
        contactPersonService.getContactPersonById(contactPersonId);
        AuditContactPerson auditContactPerson = auditContactPersonService.exists(auditId, contactPersonId);
        if (auditContactPerson == null) {
            throw new NotFoundException("No Contact Person with " + contactPersonId + " found for audit!");
        }
        audit.getAuditContactPeopleById().remove(auditContactPerson);
        audit = auditService.updateAudit(audit);
        return buildBasicResponse(audit);
    }

    public BasicAuditResponse updateAuditScope(int auditId, int facCritId, String changeNote, boolean removed, String note) throws NotFoundException, IllegalAccessException {
        Audit audit = auditService.getAuditById(auditId);
        facCritService.exists(facCritId);
        Scope scopeItem = scopeService.updateScopeItem(auditId, facCritId, changeNote, removed, note);

        // update scope of audit
        audit.getScopesById().add(scopeItem);
        audit = auditService.updateAudit(audit);

        // create Response object
        return buildBasicResponse(audit);
    }

    public BasicAuditResponse softDeleteAudit(int auditId, Date cancellationDate, String reason, int contactPersonId) throws NotFoundException {
        Audit audit = auditService.getAuditById(auditId);
        if (audit.getStatus().equals(AuditStatus.CANCELED))
            throw new IllegalArgumentException("Audit has been already canceled");
        // Check if contact person exists
        contactPersonService.getContactPersonById(contactPersonId);

        audit.setStatus(AuditStatus.CANCELED);
        audit.setCancellationDate(cancellationDate);
        audit.setCancellationReason(reason);
        audit.setCancellationContactPerson(contactPersonId);

        // update audit
        audit = auditService.updateAudit(audit);
        // create Response object
        return buildBasicResponse(audit);
    }

    public BasicAuditResponse getAuditById(int auditId) throws NotFoundException {
        Audit audit = auditService.getAuditById(auditId);
        return buildBasicResponse(audit);
    }

    public List<BasicAuditResponse> getAllAudits() throws NotFoundException {
        List<BasicAuditResponse> response = new ArrayList<>();
        for (Audit a : auditService.getAll()) {
            response.add(buildBasicResponse(a));
        }
        return response;
    }

    private BasicAuditResponse buildBasicResponse(Audit audit) throws NotFoundException {
        List<Integer> facCritIds = new ArrayList<>();
        audit.getScopesById().stream().filter(item -> !item.getRemoved()).forEach(item -> facCritIds.add(item.getFaccritId()));

        List<Integer> contactPeopleIds = new ArrayList<>();
        audit.getAuditContactPeopleById().forEach(item -> contactPeopleIds.add(item.getContactPersonId()));

        return new BasicAuditResponse(audit, facCritService.getAllById(facCritIds), contactPersonService.getAllByIds(contactPeopleIds));
    }
}
