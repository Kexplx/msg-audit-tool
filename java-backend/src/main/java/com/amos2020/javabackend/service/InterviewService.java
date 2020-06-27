package com.amos2020.javabackend.service;

import com.amos2020.javabackend.entity.Interview;
import com.amos2020.javabackend.entity.InterviewStatus;
import com.amos2020.javabackend.repository.InterviewRepository;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class InterviewService {
    final InterviewRepository repository;


    public InterviewService(InterviewRepository repository) {
        this.repository = repository;
    }

    /**
     * Returns a interview for a specific id or throws NotFoundException
     *
     * @param interviewId int
     * @return Interview
     * @throws NotFoundException If interviewId is invalid and no interview can be found
     */
    public Interview getInterviewById(int interviewId) throws NotFoundException {
        Optional<Interview> interview = repository.findById(interviewId);
        if (!interview.isPresent()) {
            throw new NotFoundException("No interview found with id " + interview);
        }
        return interview.get();
    }

    /**
     * Returns all existing interviews
     *
     * @return List<Interview>
     */
    public List<Interview> getAllInterviews() {
        return repository.findAll();
    }

    /**
     * Creates interview and returns the new interview
     *
     * @param auditId   int
     * @param startDate Date
     * @param endDate   Date
     * @return Created interview
     */
    public Interview createInterview(int auditId, Date startDate, Date endDate, String goal) {
        Interview interview = new Interview();
        interview.setAuditId(auditId);
        interview.setStartDate(startDate);
        interview.setEndDate(endDate);
        interview.setStatus(InterviewStatus.ACTIVE);
        interview.setGoal(goal);
        return repository.save(interview);
    }

    /**
     * Updates the data of an existing interview
     *
     * @param interview Interview
     * @return Updated interview
     */
    public Interview updateInterview(Interview interview) {
        return repository.save(interview);
    }
}
