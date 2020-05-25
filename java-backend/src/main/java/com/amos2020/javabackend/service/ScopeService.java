package com.amos2020.javabackend.service;

import com.amos2020.javabackend.entity.Scope;
import com.amos2020.javabackend.repository.ScopeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ScopeService {
    private static ScopeService instance;

    @Autowired
    ScopeRepository repository;

    public static ScopeService getInstance() {
        if (instance == null) {
            instance = new ScopeService();
        }
        return instance;
    }

    public void createScopeByFactorCriteriaList(int auditId, List<Integer> factorCriteriaList) {
        for (int factorCriteriaId : factorCriteriaList) {
            createScope(auditId, factorCriteriaId);
        }
    }

    public void createScope(int auditId, int factorCriteriaId) {
        Scope scope = new Scope();
        scope.setAuditId(auditId);
        scope.setFaccritId(factorCriteriaId);
        scope.setRemoved(false);
        repository.save(scope);

    }


}
