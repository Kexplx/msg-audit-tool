package com.amos2020.javabackend.service;

import com.amos2020.javabackend.entity.Scope;
import com.amos2020.javabackend.repository.ScopeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScopeService {

    final ScopeRepository repository;

    public ScopeService(ScopeRepository repository) {
        this.repository = repository;
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
