package com.amos2020.javabackend.controller;

import com.amos2020.javabackend.controller.request.CreateAuditRequest;
import com.amos2020.javabackend.controller.request.UpdateAuditRequest;
import com.amos2020.javabackend.controller.response.BasicAuditResponse;
import com.amos2020.javabackend.entity.Audit;
import com.amos2020.javabackend.entity.AuditContactPerson;
import com.amos2020.javabackend.entity.ContactPerson;
import com.amos2020.javabackend.service.*;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AuditController {

    private final AuditService auditService;
    private final ScopeService scopeService;
    private final ContactPersonService contactPersonService;
    private final AuditContactPersonService auditContactPersonService;
    private final FacCritService facCritService;

    public AuditController(AuditService auditService, ScopeService scopeService, ContactPersonService contactPersonService, AuditContactPersonService auditContactPersonService, FacCritService facCritService) {
        this.auditService = auditService;
        this.scopeService = scopeService;
        this.contactPersonService = contactPersonService;
        this.auditContactPersonService = auditContactPersonService;
        this.facCritService = facCritService;
    }

    @PostMapping("/audit")
    public ResponseEntity<BasicAuditResponse> createAudit(@RequestBody CreateAuditRequest request) {
        BasicAuditResponse response;

        try {
            // Validate parameters for creating a audit
            request.isValid();
            // Create audit and save in database
            Audit audit = auditService.createAudit(request.getAuditName(), request.getStartDate(), request.getEndDate());
            // Create Scope
            if (!request.getScope().isEmpty()) {
                scopeService.createScopeByFactorCriteriaList(audit.getId(), request.getScope());
            }
            // Create AuditContactPerson if needed
            if (!request.getContactPeople().isEmpty()) {
                auditContactPersonService.createAuditContactPersons(audit.getId(), request.getContactPeople());
            }
            // Create Response object

            response = new BasicAuditResponse(audit, facCritService.getAllById(request.getScope()), contactPersonService.getAllByIds(request.getContactPeople()));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(response);
    }

    /**
     * PUT endpoint for changing the data (name, startDate, endDate) of an audit or the corresponding contact people
     *
     * @param auditID int
     * @param request UpdateAuditRequest
     * @return BasicAuditResponse
     */
    @PutMapping("/audit/{id}")
    public ResponseEntity<BasicAuditResponse> updateAudit(@PathVariable("id") int auditID, @RequestBody UpdateAuditRequest request) {
        BasicAuditResponse response;

        try {
            // Validate parameters for updating audit
            request.isValid();

            // check if audit exists
            Audit audit = auditService.getAuditById(auditID);
            // check if all contactPerson ids are valid
            List<ContactPerson> contactPeople = contactPersonService.getAllByIds(request.getContactPeople());
            List<AuditContactPerson> auditContactPeople = new ArrayList<>();
            // update audit
            for (ContactPerson contactPerson : contactPeople) {
                AuditContactPerson auditContactPerson = new AuditContactPerson();
                auditContactPerson.setContactPersonId(contactPerson.getId());
                auditContactPerson.setAuditId(audit.getId());
                auditContactPeople.add(auditContactPerson);
            }

            // delete all auditContactPersons
            auditContactPersonService.deleteAllForAuditId(audit.getId());

            audit.setAuditContactPeopleById(auditContactPeople);
            audit = auditService.updateAudit(audit);

            List<Integer> list = new ArrayList<>();
            audit.getScopesById().forEach(item -> list.add(item.getFaccritId()));

            // Create Response object
            response = new BasicAuditResponse(audit, facCritService.getAllById(list), contactPeople);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(response);
    }
}
