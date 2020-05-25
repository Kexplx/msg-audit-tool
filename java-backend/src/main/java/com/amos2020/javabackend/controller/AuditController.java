package com.amos2020.javabackend.controller;

import com.amos2020.javabackend.controller.request.CreateAuditRequest;
import com.amos2020.javabackend.controller.response.CreateAuditResponse;
import com.amos2020.javabackend.entity.Audit;
import com.amos2020.javabackend.service.AuditContactPersonService;
import com.amos2020.javabackend.service.AuditService;
import com.amos2020.javabackend.service.ScopeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuditController {



    @PostMapping("/audit")
    public ResponseEntity<CreateAuditResponse> createAudit(@RequestBody CreateAuditRequest request) {
        CreateAuditResponse response;

        try {
            // Validate parameters for creating a audit
            request.isValid();
            // Create audit and save in database
            Audit audit = AuditService.getInstance().createAudit(request.getAuditName(), request.getStartDate(), request.getEndDate());
            // Create Scope
            ScopeService.getInstance().createScopeByFactorCriteriaList(audit.getId(), request.getScope());
            // Create AuditContactPerson if needed
            AuditContactPersonService.getInstance().createAuditContactPersons(audit.getId(), request.getContactPeople());
            // Create Response object
            response = new CreateAuditResponse(audit, request.getScope(), request.getContactPeople());

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }


        return ResponseEntity.ok(response);
    }
}
