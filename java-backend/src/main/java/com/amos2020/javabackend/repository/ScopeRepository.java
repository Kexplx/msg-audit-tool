package com.amos2020.javabackend.repository;

import com.amos2020.javabackend.entity.Scope;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScopeRepository extends JpaRepository<Scope, Integer> {
    Scope findFirstByAuditIdAndFaccritId(int auditId, int facCritId);
}

