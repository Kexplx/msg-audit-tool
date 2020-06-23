package com.amos2020.javabackend.rest_service;


import com.amos2020.javabackend.rest_service.controller.ScopeController;
import com.amos2020.javabackend.rest_service.request.scope.AddScopeRequest;
import com.amos2020.javabackend.rest_service.request.scope.UpdateScopeRequest;
import com.amos2020.javabackend.rest_service.response.BasicScopeResponse;
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


    @PutMapping("/audits/{id}/scope/{faccritid}")
    public ResponseEntity<BasicScopeResponse> addScope(@PathVariable("id") int auditId, @PathVariable("faccritid") int facCritId, @RequestBody UpdateScopeRequest request) {
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
     * GET Endpoint for fetching an specific Scope identified by its Audit id and Faccrit id
     * @param auditId
     * @param faccritId
     * @return ResponseEntity
     */
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
