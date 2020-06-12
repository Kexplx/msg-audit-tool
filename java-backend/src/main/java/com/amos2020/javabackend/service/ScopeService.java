package com.amos2020.javabackend.service;

import com.amos2020.javabackend.entity.Scope;
import com.amos2020.javabackend.repository.ScopeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        saveScope(auditId, factorCriteriaId, "", false, "");
    }

    private Scope saveScope(int auditId, int factorCriteriaId, String changeNote, boolean removed, String note) {
        Scope scope = new Scope();
        scope.setAuditId(auditId);
        scope.setFaccritId(factorCriteriaId);
        scope.setChangeNote(changeNote);
        scope.setRemoved(removed);
        scope.setNote(note);
        return repository.save(scope);
    }

    @Transactional
    public Scope findScopeItemByIds(int auditId, int facCritId) {
        return repository.findFirstByAuditIdAndFaccritId(auditId, facCritId);
    }

    public Scope updateScopeItem(int auditId, int facCritId, String changeNote, boolean isRemoved, String note) throws IllegalAccessException {
        Scope scopeItem = findScopeItemByIds(auditId, facCritId);
        if (scopeItem != null) {
            if (scopeItem.getRemoved()) {
                throw new IllegalAccessException("No rights to change a deleted scope item");
            }
        }
        scopeItem = saveScope(auditId, facCritId, changeNote, isRemoved, note);

        return scopeItem;
    }
}