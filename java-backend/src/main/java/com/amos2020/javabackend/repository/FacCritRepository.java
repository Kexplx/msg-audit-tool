package com.amos2020.javabackend.repository;

import com.amos2020.javabackend.entity.FacCrit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FacCritRepository extends JpaRepository<FacCrit, Integer> {

    @Query("select f from FacCrit f join Answer a on f.id= a.faccritId where a.interviewId=:interviewId")
    List<FacCrit> getFacCritsByInterviewId(@Param("interviewId") int interviewId);

}

