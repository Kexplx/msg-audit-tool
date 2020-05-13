package com.amos2020.javabackend.repository;

import com.amos2020.javabackend.entity.AuditProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditProjectRepository extends JpaRepository<AuditProjectEntity, Integer> {
}
