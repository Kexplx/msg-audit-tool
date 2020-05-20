package com.amos2020.javabackend.repository;

import com.amos2020.javabackend.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
}
