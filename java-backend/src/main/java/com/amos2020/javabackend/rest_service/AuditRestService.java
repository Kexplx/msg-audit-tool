package com.amos2020.javabackend.rest_service;

import com.amos2020.javabackend.rest_service.controller.AuditController;
import com.amos2020.javabackend.rest_service.request.audit.CreateAuditRequest;
import com.amos2020.javabackend.rest_service.request.audit.DeleteAuditRequest;
import com.amos2020.javabackend.rest_service.request.audit.UpdateAuditRequest;
import com.amos2020.javabackend.rest_service.request.audit.UpdateAuditScopeRequest;
import com.amos2020.javabackend.rest_service.response.BasicAuditResponse;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuditRestService {

    final AuditController auditController;

    public AuditRestService(AuditController auditController) {
        this.auditController = auditController;
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
            response = auditController.createAudit(request.getAuditName(), request.getStartDate(), request.getEndDate(), request.getScope(), request.getContactPeople());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    /**
     * PUT endpoint for changing the data (name, startDate, endDate) of an audit
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
            response = auditController.updateAudit(auditId, request.getAuditName(), request.getStartDate(), request.getEndDate());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(response);
    }

    /**
     * PUT for adding a contactPerson to an audit
     *
     * @param auditId int
     * @return BasicAuditResponse
     */
    @PutMapping("/audits/{id}/contactpersons/{contactPersonId}")
    public ResponseEntity<BasicAuditResponse> addContactPersonToAudit(@PathVariable("id") int auditId, @PathVariable("contactPersonId") int contactPersonId) {
        BasicAuditResponse response;
        try {
            response = auditController.addContactPersonToAudit(auditId, contactPersonId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(response);
    }

    /**
     * DELETE for removing a contactPerson from an audit
     *
     * @param auditId int
     * @return BasicAuditResponse
     */
    @DeleteMapping("/audits/{id}/contactpersons/{contactPersonId}")
    public ResponseEntity<BasicAuditResponse> removeContactPersonFromAudit(@PathVariable("id") int auditId, @PathVariable("contactPersonId") int contactPersonId) {
        BasicAuditResponse response;

        try {
            response = auditController.removeContactPersonFromAudit(auditId, contactPersonId);
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
            response = auditController.updateAuditScope(auditId, request.getFacCritId(), request.getChangeNote(), request.isRemoved(), request.getNote());
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
            response = auditController.softDeleteAudit(auditId, request.getDate(), request.getReason(), request.getContactPerson());
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
            response = auditController.getAuditById(auditId);
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
        List<BasicAuditResponse> response;
        try {
            response = auditController.getAllAudits();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }


}
