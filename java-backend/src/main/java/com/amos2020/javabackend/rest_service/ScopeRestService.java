package com.amos2020.javabackend.rest_service;


import com.amos2020.javabackend.rest_service.controller.ScopeController;
import com.amos2020.javabackend.rest_service.request.scope.AddScopeRequest;
import com.amos2020.javabackend.rest_service.request.scope.UpdateScopeRequest;
import com.amos2020.javabackend.rest_service.response.BasicScopeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ScopeRestService {

    private final ScopeController scopeController;


    public ScopeRestService(ScopeController scopeController) {
        this.scopeController = scopeController;
    }

    /**
     *
     * @param auditId
     * @param request
     * @return
     */
    @Operation(summary = "Create new Scopes belonging to an Audit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful created new Scopes"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    @PostMapping("/audits/{id}/scope")
    public ResponseEntity<List<BasicScopeResponse>> addScope(@PathVariable("id") int auditId, @RequestBody AddScopeRequest request) {
        List<BasicScopeResponse> response;
        try {
            request.isValid();
            response = scopeController.addScope(auditId, request.getScope());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    /**
     *
     * @param auditId
     * @param facCritId
     * @param request
     * @return
     */
    @Operation(summary = "Update a Scope by its Audit and Faccrit Id ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated Scope"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    @PutMapping("/audits/{id}/scope/{faccritid}")
    public ResponseEntity<BasicScopeResponse> addScope(@PathVariable("id") int auditId,
                                                       @PathVariable("faccritid") int facCritId,
                                                       @RequestBody UpdateScopeRequest request) {
        BasicScopeResponse response;
        try {
            request.isValid();
            response = scopeController.updateScope(auditId, facCritId, request.isRemoved(), request.getNote(), request.getChange_note());
        } catch (IllegalArgumentException | IllegalAccessException e) {
            return ResponseEntity.badRequest().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    /**
     * GET Endpoint for fetching a specific Scope identified by its Audit id and Faccrit id
     * @param auditId
     * @param faccritId
     * @return ResponseEntity
     */
    @Operation(summary = "Get a Scope by its Audit and Faccrit id ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Received Scope"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    @GetMapping("/audits/{id1}/scope/{id2}")
    public ResponseEntity<BasicScopeResponse> getScopeByIds(@PathVariable("id1") int auditId,
                                                            @PathVariable("id2") int faccritId){
        BasicScopeResponse response;
        try {
            response = scopeController.getScopeByIds(auditId,faccritId);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(response);
    }

    /**
     * GET Endpoint for fetching all existing Scopes
     * @param auditId
     * @return ResponseEntity
     */
    @Operation(summary = "Get all Scopes by Audit id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Received all Scopes by auditId"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
    })
    @GetMapping("/audits/{id}/scope")
    public ResponseEntity<List<BasicScopeResponse>> getAllScopes(@PathVariable("id") int auditId){
        List<BasicScopeResponse> responses;
        try {
            responses = scopeController.getScopesByAuditId(auditId);
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(responses);
    }
}
