package com.amos2020.javabackend.repository;

import com.amos2020.javabackend.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
/**
 * Interface to access the question table in the database
 */
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    @Query("select a from Question a where a.faccritId=:faccritId")
    List<Question> getQuestionsByFaccritId(@Param("faccritId") int faccritId);
}

