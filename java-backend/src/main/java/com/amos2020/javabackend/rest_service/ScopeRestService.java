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

    final ScopeController scopeController;


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
}
