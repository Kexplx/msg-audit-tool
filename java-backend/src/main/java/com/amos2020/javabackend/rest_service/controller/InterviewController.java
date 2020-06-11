package com.amos2020.javabackend.rest_service.controller;

import com.amos2020.javabackend.entity.ContactPerson;
import com.amos2020.javabackend.entity.Interview;
import com.amos2020.javabackend.entity.Question;
import com.amos2020.javabackend.rest_service.response.BasicInterviewResponse;
import com.amos2020.javabackend.service.*;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class InterviewController {

    final InterviewService interviewService;
    final ContactPersonService contactPersonService;
    final AuditService auditService;
    final InterviewContactPersonService interviewContactPersonService;
    final AnswerService answerService;
    final QuestionService questionService;

    public InterviewController(InterviewService interviewService, ContactPersonService contactPersonService, AuditService auditService, InterviewContactPersonService interviewContactPersonService, AnswerService answerService, QuestionService questionService) {
        this.interviewService = interviewService;
        this.contactPersonService = contactPersonService;
        this.auditService = auditService;
        this.interviewContactPersonService = interviewContactPersonService;
        this.answerService = answerService;
        this.questionService = questionService;
    }

    public BasicInterviewResponse getInterviewById(int interviewId) throws NotFoundException {
        Interview interview = interviewService.getInterviewById(interviewId);

        // Get ContactPeople for interview
        List<ContactPerson> interviewedContactPeople = getContactPeopleForInterview(interview);
        // Build response
        return new BasicInterviewResponse(interview, interviewedContactPeople);
    }

    public List<BasicInterviewResponse> getAllInterviews() throws NotFoundException {
        List<BasicInterviewResponse> responses = new ArrayList<>();
        for (Interview interview : interviewService.getAllInterviews()) {
            responses.add(new BasicInterviewResponse(interview, getContactPeopleForInterview(interview)));
        }
        return responses;
    }

    private List<ContactPerson> getContactPeopleForInterview(Interview interview) throws NotFoundException {
        List<Integer> contactPeopleIds = new ArrayList<>();
        interview.getInterviewContactPeopleById().forEach(item -> contactPeopleIds.add(item.getContactPersonId()));
        return contactPersonService.getAllByIds(contactPeopleIds);
    }

    public BasicInterviewResponse createInterview(int auditId, Date startDate, Date endDate, HashMap<Integer, String> interviewedPeople, List<Integer> interviewScope) throws NotFoundException {
        // check if audit exists
        auditService.getAuditById(auditId);
        // check if all contactPersons exist
        List<ContactPerson> contactPeople = contactPersonService.getAllByIds(new ArrayList<>(interviewedPeople.keySet()));
        // create interview
        Interview interview = interviewService.createInterview(auditId, startDate, endDate);
        // create interviewContactPersons
        for (int contactPersonId : interviewedPeople.keySet()) {
            interviewContactPersonService.create(interview.getId(), contactPersonId, interviewedPeople.get(contactPersonId));
        }

        // create empty Answers
        for (int facCritId : interviewScope) {
            List<Question> questions = questionService.getQuestionsByFacCritId(facCritId);
            for (Question question : questions) {
                answerService.createAnswer(question.getId(), interview.getId(), question.getFaccritId());
            }
        }
        interview.setAnswersById(answerService.getAnswersByInterviewId(interview.getId()));
        return new BasicInterviewResponse(interview, contactPeople);
    }
}
