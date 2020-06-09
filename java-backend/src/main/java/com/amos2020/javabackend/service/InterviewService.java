package com.amos2020.javabackend.service;

import com.amos2020.javabackend.entity.Interview;
import com.amos2020.javabackend.repository.InterviewRepository;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

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
     * @throws NotFoundException exception
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
}
