package com.amos2020.javabackend.rest_service;

import com.amos2020.javabackend.rest_service.request.CreateAuditRequest;
import com.amos2020.javabackend.rest_service.request.DeleteAuditRequest;
import com.amos2020.javabackend.rest_service.request.UpdateAuditRequest;
import com.amos2020.javabackend.rest_service.request.UpdateAuditScopeRequest;
import com.amos2020.javabackend.rest_service.response.BasicAuditResponse;
import com.amos2020.javabackend.entity.*;
import com.amos2020.javabackend.service.*;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AuditRestService {

    private final AuditService auditService;
    private final ScopeService scopeService;
    private final ContactPersonService contactPersonService;
    private final AuditContactPersonService auditContactPersonService;
    private final FacCritService facCritService;

    public AuditRestService(AuditService auditService, ScopeService scopeService, ContactPersonService contactPersonService, AuditContactPersonService auditContactPersonService, FacCritService facCritService) {
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
    @PostMapping("/audits")
    public ResponseEntity<BasicAuditResponse> createAudit(@RequestBody CreateAuditRequest request) {
        BasicAuditResponse response;

        try {
            // Validate parameters for creating a audit
            request.isValid();
            // Create audit and save in database
            Audit audit = auditService.createAudit(request.getAuditName(), request.getStartDate(), request.getEndDate());
            // Create Scope
            scopeService.createScopeByFactorCriteriaList(audit.getId(), request.getScope());
            // Create AuditContactPerson if needed
            auditContactPersonService.createAuditContactPersons(audit.getId(), request.getContactPeople());
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
    @PutMapping("/audits/{id}")
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
    @PutMapping("/audits/{id}/scope")
    public ResponseEntity<BasicAuditResponse> updateAuditScope(@PathVariable("id") int auditId, @RequestBody UpdateAuditScopeRequest request) {
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
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(response);
    }

    /**
     * DELETE endpoint for deleting an audit
     *
     * @param auditId int
     * @param request DeleteAuditRequest
     * @return BasicAuditResponse
     */
    @DeleteMapping("/audits/{id}")
    public ResponseEntity<BasicAuditResponse> deleteAudit(@PathVariable("id") int auditId, @RequestBody DeleteAuditRequest request) {
        BasicAuditResponse response;
        try {
            request.isValid();
            Audit audit = auditService.getAuditById(auditId);
            if (audit.getStatus().equals(AuditStatus.CANCELED))
                throw new IllegalArgumentException("Audit has been already canceled");
            // set Status to canceled and add reason, date and contactperson
            audit.setStatus(request.getStatus());
            audit.setCancellationDate(request.getDate());
            audit.setCancellationReason(request.getReason());
            audit.setCancellationContactPerson(request.getContactPerson());

            // update audit
            audit = auditService.updateAudit(audit);


            // create Response object
            response = buildBasicResponse(audit);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    /**
     * GET endpoint for fetching a specific audit by id
     *
     * @param auditId int
     * @return BasicAuditResponse
     */
    @GetMapping("/audits/{id}")
    public ResponseEntity<BasicAuditResponse> getAuditById(@PathVariable("id") int auditId) {
        BasicAuditResponse response;
        try {
            Audit audit = auditService.getAuditById(auditId);
            response = buildBasicResponse(audit);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }


    /**
     * GET endpoint for fetching a list of all audits
     *
     * @return List<BasicAuditResponse>
     */
    @GetMapping("/audits")
    public ResponseEntity<List<BasicAuditResponse>> getAuditAll() {
        List<BasicAuditResponse> response = new ArrayList<>();
        try {
            for (Audit a : auditService.getAll()) {
                response.add(buildBasicResponse(a));
            }
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
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
