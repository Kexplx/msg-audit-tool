package com.amos2020.javabackend.repository;

import com.amos2020.javabackend.entity.AuditContactPerson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditContactPersonRepository extends JpaRepository<AuditContactPerson, Integer> {
}
