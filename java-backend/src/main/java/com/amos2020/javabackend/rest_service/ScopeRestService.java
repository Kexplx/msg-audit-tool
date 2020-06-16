package com.amos2020.javabackend.rest_service;

import com.amos2020.javabackend.rest_service.controller.ScopeController;
import com.amos2020.javabackend.rest_service.response.BasicScopeResponse;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class ScopeRestService {

    private final ScopeController scopeController;

    public ScopeRestService(ScopeController scopeController) {
        this.scopeController = scopeController;
    }

    /**
     * GET Endpoint for fetching an specific Scope identified by its Audit id and Faccrit id
     * @param auditId
     * @param faccritId
     * @return ResponseEntity
     */
    @GetMapping("/scopes/audit/{id1}/faccrit/{id2}")
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
     * @return ResponseEntity
     */
    @GetMapping("/scopes")
    public ResponseEntity<List<BasicScopeResponse>> getAllScopes(){
        List<BasicScopeResponse> responses;
        try {
            responses = scopeController.getAllScopes();
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(responses);
    }
}
