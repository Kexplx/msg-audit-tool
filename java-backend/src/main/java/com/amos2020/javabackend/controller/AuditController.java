package com.amos2020.javabackend.controller;

import com.amos2020.javabackend.controller.request.CreateAuditRequest;
import com.amos2020.javabackend.controller.request.UpdateAuditRequest;
import com.amos2020.javabackend.controller.request.UpdateAuditScopeRequest;
import com.amos2020.javabackend.controller.response.BasicAuditResponse;
import com.amos2020.javabackend.entity.Audit;
import com.amos2020.javabackend.entity.AuditContactPerson;
import com.amos2020.javabackend.entity.ContactPerson;
import com.amos2020.javabackend.entity.Scope;
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

    /**
     * POST endpoint for creating an audit associated with a scope and audit_contact_person
     *
     * @param request CreateAuditRequest
     * @return BasicAuditResponse
     */
    @PostMapping("/audit")
    public ResponseEntity<BasicAuditResponse> createAudit(@RequestBody CreateAuditRequest request) {
        BasicAuditResponse response;

        try {
            // Validate parameters for creating a audit
            request.isValid();
            // Create audit and save in database
            Audit audit = auditService.createAudit(request.getAuditName(), request.getStartDate(), request.getEndDate());
            // Create Scope
            //if (!request.getScope().isEmpty()) {
            scopeService.createScopeByFactorCriteriaList(audit.getId(), request.getScope());
            //}
            // Create AuditContactPerson if needed
            //if (!request.getContactPeople().isEmpty()) {
            auditContactPersonService.createAuditContactPersons(audit.getId(), request.getContactPeople());
            //}
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
     * @param auditId int
     * @param request UpdateAuditRequest
     * @return BasicAuditResponse
     */
    @PutMapping("/audit/{id}")
    public ResponseEntity<BasicAuditResponse> updateAudit(@PathVariable("id") int auditId, @RequestBody UpdateAuditRequest request) {
        BasicAuditResponse response;

        try {
            // Validate parameters for updating audit
            request.isValid();

            // check if audit exists
            Audit audit = auditService.getAuditById(auditId);
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

            response = buildBasicResponse(audit);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(response);
    }

    /**
     * PUT endpoint for changing a faccrit in the scope of an audit or the corresponding contact people
     *
     * @param auditId int
     * @param request UpdateAuditScopeRequest
     * @return BasicAuditResponse
     */
    @PutMapping("/audit/{id}/scope")
    public ResponseEntity<BasicAuditResponse> updateAudit(@PathVariable("id") int auditId, @RequestBody UpdateAuditScopeRequest request) {
        BasicAuditResponse response;
        try {
            request.isValid();

            Audit audit = auditService.getAuditById(auditId);

            facCritService.exists(request.getFacCritId());

            Scope scopeItem = scopeService.updateScopeItem(auditId, request.getFacCritId(), request.getChangeNote(), request.isRemoved());

            // update scope of audit
            audit.getScopesById().add(scopeItem);
            audit = auditService.updateAudit(audit);

            // create Response object
            response = buildBasicResponse(audit);
        } catch (
                IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (
                NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(response);
    }


    private BasicAuditResponse buildBasicResponse(Audit audit) throws NotFoundException {
        List<Integer> facCritIds = new ArrayList<>();
        audit.getScopesById().stream().filter(item -> !item.getRemoved()).forEach(item -> facCritIds.add(item.getFaccritId()));

        List<Integer> contactPeopleIds = new ArrayList<>();
        audit.getAuditContactPeopleById().forEach(item -> contactPeopleIds.add(item.getContactPersonId()));

        return new BasicAuditResponse(audit, facCritService.getAllById(facCritIds), contactPersonService.getAllByIds(contactPeopleIds));
    }
}
