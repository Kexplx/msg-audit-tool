package com.amos2020.javabackend.repository;

import com.amos2020.javabackend.entity.Interview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
/**
 * Interface to access the interview table in the database
 */
public interface InterviewRepository extends JpaRepository<Interview, Integer> {
    List<Interview> findAllByAuditId(int auditId);
}

