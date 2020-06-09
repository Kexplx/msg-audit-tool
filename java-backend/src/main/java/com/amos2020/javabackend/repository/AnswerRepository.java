package com.amos2020.javabackend.repository;

import com.amos2020.javabackend.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {

    @Modifying
    @Query("select * from Answer a where a.interview_id=:interviewId")
    List<Answer> getAnswersByInterviewId(@Param("interviewId") int interviewId);

    Answer findFirstByQuestionIdAndInterviewId(int questionId, int interviewId);
}
