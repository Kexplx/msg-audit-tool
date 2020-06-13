package com.amos2020.javabackend.repository;

import com.amos2020.javabackend.entity.InterviewContactPerson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewContactPersonRepository extends JpaRepository<InterviewContactPerson, Integer> {
    InterviewContactPerson findFirstByInterviewIdAndContactPersonId(int interviewId, int contactPersonId);
}

