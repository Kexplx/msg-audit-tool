package com.amos2020.javabackend.controller;

import com.amos2020.javabackend.controller.request.CreateAuditRequest;
import com.amos2020.javabackend.controller.response.CreateAuditResponse;
import com.amos2020.javabackend.entity.Audit;
import com.amos2020.javabackend.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<CreateAuditResponse> createAudit(@RequestBody CreateAuditRequest request) {
        CreateAuditResponse response;

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

            response = new CreateAuditResponse(audit, facCritService.getAllById(request.getScope()), contactPersonService.getAllByIds(request.getContactPeople()));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }


        return ResponseEntity.ok(response);
    }
}
