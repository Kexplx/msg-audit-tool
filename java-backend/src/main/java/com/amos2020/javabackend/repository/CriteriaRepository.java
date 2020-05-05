package com.amos2020.javabackend.repository;

import com.amos2020.javabackend.entity.AuditProjectEntity;
import com.amos2020.javabackend.entity.CriteriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.criteria.CriteriaBuilder;

public interface CriteriaRepository extends JpaRepository<CriteriaEntity, Integer> {
}
