package com.amos2020.javabackend.repository;

import com.amos2020.javabackend.entity.FacCrit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
/**
 * Interface to access the fac_crit table in the database
 */
public interface FacCritRepository extends JpaRepository<FacCrit, Integer> {

    @Query("select f from FacCrit f where f.id in (select a.faccritId from Answer a where a.interviewId=:interviewId)")
    List<FacCrit> getFacCritsByInterviewId(@Param("interviewId") int interviewId);
}

