package com.amos2020.javabackend.rest_service.controller;

import com.amos2020.javabackend.entity.Scope;
import com.amos2020.javabackend.rest_service.response.BasicScopeResponse;
import com.amos2020.javabackend.service.AuditService;
import com.amos2020.javabackend.service.FacCritService;
import com.amos2020.javabackend.service.ScopeService;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.*;

@Service
public class ScopeController {

    final ScopeService scopeService;
    final AuditService auditService;
    final FacCritService facCritService;

    public ScopeController(ScopeService scopeService, AuditService auditService, FacCritService facCritService){
        this.scopeService = scopeService;
        this.auditService = auditService;
        this.facCritService = facCritService;
    }

    public BasicScopeResponse getScopeByIds(int auditId, int faccritId) throws NotFoundException {
        Scope scope = scopeService.findScopeItemByIds(auditId, faccritId);
        return new BasicScopeResponse(scope);
    }

    public List<BasicScopeResponse> getAllScopes(){
        List<BasicScopeResponse> responseList = new ArrayList<>();
        List<Scope> scopes = scopeService.getAll();
        scopes.forEach( scope -> responseList.add(new BasicScopeResponse(scope)));
        return responseList;
    }
}
