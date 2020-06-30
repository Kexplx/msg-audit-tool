package com.amos2020.javabackend.rest_service.controller;


import com.amos2020.javabackend.entity.Scope;
import com.amos2020.javabackend.rest_service.response.BasicScopeResponse;
import com.amos2020.javabackend.service.AuditService;
import com.amos2020.javabackend.service.ScopeService;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScopeController {

    final ScopeService scopeService;
    final AuditService auditService;

    public ScopeController(ScopeService scopeService, AuditService auditService) {
        this.scopeService = scopeService;
        this.auditService = auditService;
    }

    /**
     * Add new Scope for an existing Audit
     *
     * @param auditId int
     * @param scope   List<Integer>
     * @return New Scope
     * @throws NotFoundException If Audit id or facCrit id is invalid and can not be found
     */
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

    /**
     * Update an existing Scope object
     *
     * @param auditId     int
     * @param facCritId   int
     * @param removed     bool
     * @param note        String
     * @param change_note String
     * @return Updated Scope object
     * @throws IllegalAccessException If Scope object has already been removed
     * @throws NotFoundException      If one or more ids are invalid and the scope object can not be found
     */
    public BasicScopeResponse updateScope(int auditId, int facCritId, boolean removed, String note, String change_note) throws IllegalAccessException, NotFoundException {
        auditService.getAuditById(auditId);
        scopeService.updateScopeItem(auditId, facCritId, change_note, removed, note);
        return (new BasicScopeResponse(scopeService.findScopeItemByIds(auditId, facCritId)));
    }

    /**
     * Get a Scope object by audit id and facCrit id
     *
     * @param auditId   int
     * @param faccritId int
     * @return Scope object
     * @throws NotFoundException If the audit id or the facCrit id is invalid and can not be found
     */
    public BasicScopeResponse getScopeByIds(int auditId, int faccritId) throws NotFoundException {
        Scope scope = scopeService.findScopeItemByIds(auditId, faccritId);
        if (scope == null) {
            throw new NotFoundException("Scope can not be found");
        }
        return new BasicScopeResponse(scope);
    }

    /**
     * Get all Scope objects for an audit
     *
     * @param auditId int
     * @return List of Scope obejcts
     * @throws NotFoundException If the audit id is invalid and can not be found
     */
    public List<BasicScopeResponse> getScopesByAuditId(int auditId) throws NotFoundException {
        auditService.getAuditById(auditId);
        List<BasicScopeResponse> responseList = new ArrayList<>();
        List<Scope> scopes = scopeService.findScopeItemsByAuditId(auditId);
        scopes.forEach(scope -> responseList.add(new BasicScopeResponse(scope)));
        return responseList;
    }
}
