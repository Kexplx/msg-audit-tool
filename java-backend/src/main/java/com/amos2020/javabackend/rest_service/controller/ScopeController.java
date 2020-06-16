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
}
