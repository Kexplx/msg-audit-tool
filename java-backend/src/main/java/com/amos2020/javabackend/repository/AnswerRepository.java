package com.amos2020.javabackend.repository;

import com.amos2020.javabackend.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {

    @Query("select a from Answer a where a.interviewId=:interviewId")
    List<Answer> getAnswersByInterviewId(@Param("interviewId") int interviewId);

    @Query("select a from Answer a where a.interviewId=:interviewId and a.questionId=:questionId")
    Answer findFirstByQuestionIdAndInterviewId(@Param("questionId") int questionId, @Param("interviewId") int interviewId);
}
