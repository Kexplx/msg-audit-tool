package com.amos2020.javabackend.rest_service;

import com.amos2020.javabackend.rest_service.controller.AuditController;
import com.amos2020.javabackend.rest_service.request.audit.CreateAuditRequest;
import com.amos2020.javabackend.rest_service.request.audit.DeleteAuditRequest;
import com.amos2020.javabackend.rest_service.request.audit.UpdateAuditRequest;
import com.amos2020.javabackend.rest_service.request.audit.UpdateAuditScopeRequest;
import com.amos2020.javabackend.rest_service.response.BasicAuditResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@Validated
@Tag(name = "Audit", description = "The endpoints for the audit resource")
@CrossOrigin
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
    @Operation(summary = "Create a new Audit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created a new audit"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    @PostMapping("/audits")
    public ResponseEntity<BasicAuditResponse> createAudit(@RequestBody @Valid CreateAuditRequest request) {
        BasicAuditResponse response;

        try {
            // Validate parameters for creating a audit
            request.isValid();
            response = auditController.createAudit(request.getName(), request.getStartDate(), request.getEndDate(), request.getScope(), request.getContactPersons());
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
    @Operation(summary = "Update an existing Audit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the audit"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    @PutMapping("/audits/{id}")
    public ResponseEntity<BasicAuditResponse> updateAudit(@PathVariable("id") @Parameter(name = "id", example = "1") @Min(1) int auditId, @RequestBody @Valid UpdateAuditRequest request) {
        BasicAuditResponse response;
        try {
            // Validate parameters for updating audit
            request.isValid();
            response = auditController.updateAudit(auditId, request.getName(), request.getStartDate(), request.getEndDate());
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(response);
    }

    /**
     * PUT for adding a contactPerson to an audit
     *
     * @param auditId         int
     * @param contactPersonId int
     * @return BasicAuditResponse
     */
    @Operation(summary = "Add a contact person to an existing Audit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully added a person to the audit"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    @PutMapping("/audits/{id}/contactpersons/{contactPersonId}")
    public ResponseEntity<BasicAuditResponse> addContactPersonToAudit(@PathVariable("id") @Parameter(name = "id", example = "1") @Min(1) int auditId, @PathVariable("contactPersonId") @Parameter(name = "contactPersonId", example = "1") @Min(1) int contactPersonId) {
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
     * @param auditId         int
     * @param contactPersonId int
     * @return BasicAuditResponse
     */
    @Operation(summary = "Remove a contact person from an existing Audit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully removed the contact person from the audit"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    @DeleteMapping("/audits/{id}/contactpersons/{contactPersonId}")
    public ResponseEntity<BasicAuditResponse> removeContactPersonFromAudit(@PathVariable("id") @Parameter(name = "id", example = "1") @Min(1) int auditId, @PathVariable("contactPersonId") @Parameter(name = "contactPersonId", example = "1") @Min(1) int contactPersonId) {
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
    @Operation(summary = "Update the scope of an existing Audit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the scope of the audit"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    @PutMapping("/audits/{id}/scope")
    public ResponseEntity<BasicAuditResponse> updateAuditScope(@PathVariable("id") @Parameter(name = "id", example = "1") @Min(1) int auditId, @RequestBody @Valid UpdateAuditScopeRequest request) {
        BasicAuditResponse response;
        try {
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
    @Operation(summary = "Delete an existing Audit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted the audit"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    @DeleteMapping("/audits/{id}")
    public ResponseEntity<BasicAuditResponse> deleteAudit(@PathVariable("id") @Parameter(name = "id", example = "1") @Min(1) int auditId, @RequestBody @Valid DeleteAuditRequest request) {
        BasicAuditResponse response;
        try {
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
    @Operation(summary = "Get Audit by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Receive audit with specific id"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    @GetMapping("/audits/{id}")
    public ResponseEntity<BasicAuditResponse> getAuditById(@PathVariable("id") @Parameter(name = "id", example = "1") @Min(1) int auditId) {
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
    @Operation(summary = "Get all existing Audits")
    @ApiResponse(responseCode = "200", description = "Received all existing audit")
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
