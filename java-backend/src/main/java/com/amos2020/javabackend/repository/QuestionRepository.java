package com.amos2020.javabackend.repository;

import com.amos2020.javabackend.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
}

