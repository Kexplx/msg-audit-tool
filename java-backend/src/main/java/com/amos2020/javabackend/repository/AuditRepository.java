package com.amos2020.javabackend.repository;

import com.amos2020.javabackend.entity.Audit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditRepository extends JpaRepository<Integer, Audit> {
}

