package com.amos2020.javabackend.repository;

import com.amos2020.javabackend.entity.Interview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewRepository extends JpaRepository<Interview, Integer> {
}

