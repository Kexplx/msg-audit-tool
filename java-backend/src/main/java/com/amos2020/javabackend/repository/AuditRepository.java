package com.amos2020.javabackend.repository;

import com.amos2020.javabackend.entity.Audit;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Interface to access the audit table in the database
 */
public interface AuditRepository extends JpaRepository<Audit, Integer> {
}

