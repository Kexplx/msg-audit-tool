package com.amos2020.javabackend.repository;

import com.amos2020.javabackend.entity.AuditProjectEntity;
import com.amos2020.javabackend.entity.ContactPersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactPersonRepository extends JpaRepository<ContactPersonEntity, Integer> {
}
