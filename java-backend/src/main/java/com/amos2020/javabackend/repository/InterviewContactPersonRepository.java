package com.amos2020.javabackend.repository;

import com.amos2020.javabackend.entity.InterviewContactPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
/**
 * Interface to access the interview_contact_person table in the database
 */
public interface InterviewContactPersonRepository extends JpaRepository<InterviewContactPerson, Integer> {
    @Modifying
    @Query("delete from InterviewContactPerson a where a.interviewId=:interviewId and a.contactPersonId=:contactPersonId")
    void deleteByInterviewIdAndContactPersonId(@Param("interviewId") int interviewId, @Param("contactPersonId") int contactPersonId);

    InterviewContactPerson findFirstByInterviewIdAndContactPersonId(int interviewId, int contactPersonId);
}

