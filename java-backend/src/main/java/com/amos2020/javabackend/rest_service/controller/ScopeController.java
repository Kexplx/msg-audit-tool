package com.amos2020.javabackend.rest_service.controller;


import com.amos2020.javabackend.entity.Audit;
import com.amos2020.javabackend.entity.Scope;
import com.amos2020.javabackend.rest_service.response.BasicAuditResponse;
import com.amos2020.javabackend.rest_service.response.BasicScopeResponse;
import com.amos2020.javabackend.service.AuditService;
import com.amos2020.javabackend.service.ScopeService;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ScopeController {

    final ScopeService scopeService;
    final AuditService auditService;

    public ScopeController(ScopeService scopeService, AuditService auditService) {
        this.scopeService = scopeService;
        this.auditService = auditService;
    }

    public List<BasicScopeResponse> addScope(int auditId, List<Integer> scope) throws NotFoundException {
        auditService.getAuditById(auditId);
        scopeService.createScopeByFactorCriteriaList(auditId, scope);

        List<Scope> auditScope = scopeService.findScopeItemsByAuditId(auditId);
        List<BasicScopeResponse> result = new ArrayList<>();

        for (Scope s : auditScope) {
            result.add(new BasicScopeResponse(s));
        }
        return result;
    }

    public BasicScopeResponse updateScope(int auditId, int facCritId, boolean removed, String note, String change_note) throws IllegalAccessException, NotFoundException {
        auditService.getAuditById(auditId);
        scopeService.updateScopeItem(auditId, facCritId, change_note, removed, note);
        return (new BasicScopeResponse(scopeService.findScopeItemByIds(auditId,facCritId)));
    }

    public BasicScopeResponse getScopeByIds(int auditId, int faccritId) throws NotFoundException {
        Scope scope = scopeService.findScopeItemByIds(auditId, faccritId);
        return new BasicScopeResponse(scope);
    }

    public List<BasicScopeResponse> getScopesByAuditId(int auditId) throws NotFoundException {
        auditService.getAuditById(auditId);
        List<BasicScopeResponse> responseList = new ArrayList<>();
        List<Scope> scopes = scopeService.findScopeItemsByAuditId(auditId);
        scopes.forEach( scope -> responseList.add(new BasicScopeResponse(scope)));
        return responseList;
    }
}
