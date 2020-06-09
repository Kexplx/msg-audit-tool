package com.amos2020.javabackend.service;

import com.amos2020.javabackend.entity.InterviewContactPerson;
import com.amos2020.javabackend.repository.InterviewContactPersonRepository;
import org.springframework.stereotype.Service;

@Service
public class InterviewContactPersonService {
    private final InterviewContactPersonRepository repository;


    public InterviewContactPersonService(InterviewContactPersonRepository repository) {
        this.repository = repository;
    }

    /**
     * Creates new InterviewContactPerson and returns it
     *
     * @param interviewId     int
     * @param contactPersonId int
     * @param role            String
     * @return Created InterviewContactPerson
     */
    public InterviewContactPerson create(int interviewId, int contactPersonId, String role) {
        InterviewContactPerson interviewContactPerson = new InterviewContactPerson();
        interviewContactPerson.setInterviewId(interviewId);
        interviewContactPerson.setContactPersonId(contactPersonId);
        interviewContactPerson.setRole(role);
        return repository.save(interviewContactPerson);
    }
}
