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

    /**
     * Create Scope objects by a given audit id and a given List of FacCrit ids
     *
     * @param auditId            int
     * @param factorCriteriaList List<Integer>
     */
    public void createScopeByFactorCriteriaList(int auditId, List<Integer> factorCriteriaList) {
        for (int factorCriteriaId : factorCriteriaList) {
            createScope(auditId, factorCriteriaId);
        }
    }

    /**
     * Create a single Scope obejct by audit id and facCrit id
     *
     * @param auditId          int
     * @param factorCriteriaId int
     */
    public void createScope(int auditId, int factorCriteriaId) {
        saveScope(auditId, factorCriteriaId, "", false, "");
    }

    /**
     * Method to save a new or existing Scope object
     *
     * @param auditId          int
     * @param factorCriteriaId int
     * @param changeNote       String
     * @param removed          bool
     * @param note             String
     * @return Scope
     */
    private Scope saveScope(int auditId, int factorCriteriaId, String changeNote, boolean removed, String note) {
        Scope scope = new Scope();
        scope.setAuditId(auditId);
        scope.setFaccritId(factorCriteriaId);
        scope.setChangeNote(changeNote);
        scope.setRemoved(removed);
        scope.setNote(note);
        return repository.save(scope);
    }

    /**
     * Find a Scope object by audit id and facCrit id
     *
     * @param auditId   int
     * @param facCritId int
     * @return Scope
     */
    @Transactional
    public Scope findScopeItemByIds(int auditId, int facCritId) {
        return repository.findFirstByAuditIdAndFaccritId(auditId, facCritId);
    }

    /**
     * Find Scope objects by audit id
     *
     * @param auditId int
     * @return List<Scope>
     */
    @Transactional
    public List<Scope> findScopeItemsByAuditId(int auditId) {
        return repository.findAllByAuditId(auditId);
    }

    /**
     * Update an existing Scope object
     *
     * @param auditId    int
     * @param facCritId  int
     * @param changeNote String
     * @param isRemoved  bool
     * @param note       String
     * @return Updated Scope object
     * @throws IllegalAccessException If Scope object has already been removed
     */
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