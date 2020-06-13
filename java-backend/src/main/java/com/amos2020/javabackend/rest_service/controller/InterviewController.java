package com.amos2020.javabackend.rest_service.controller;

import com.amos2020.javabackend.entity.ContactPerson;
import com.amos2020.javabackend.entity.Interview;
import com.amos2020.javabackend.entity.InterviewStatus;
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

    /**
     * Get a specific interview by an id
     *
     * @param interviewId int
     * @return Interview or Exception
     * @throws NotFoundException if interview does not exist
     */
    public BasicInterviewResponse getInterviewById(int interviewId) throws NotFoundException {
        Interview interview = interviewService.getInterviewById(interviewId);
        List<ContactPerson> interviewedContactPeople = getContactPeopleForInterview(interview);
        return new BasicInterviewResponse(interview, interviewedContactPeople);
    }

    /**
     * Get all existing interviews
     *
     * @return all interviews
     * @throws NotFoundException if contactPersonIds are not valid and therefore can not be found
     */
    public List<BasicInterviewResponse> getAllInterviews() throws NotFoundException {
        List<BasicInterviewResponse> responses = new ArrayList<>();
        for (Interview interview : interviewService.getAllInterviews()) {
            responses.add(new BasicInterviewResponse(interview, getContactPeopleForInterview(interview)));
        }
        return responses;
    }

    /**
     * Create a new Interview, the associated InterviewContactPeople and empty Answers for the scope of FacCrits
     *
     * @param auditId           int
     * @param startDate         Date
     * @param endDate           Date
     * @param interviewedPeople HashMap of ContactPersonId and Role
     * @param interviewScope    List of FacCritIds
     * @return New interview
     * @throws NotFoundException If the auditId, an contactPersonId or an facCritId is invalid
     */
    public BasicInterviewResponse createInterview(int auditId, Date startDate, Date endDate, HashMap<Integer, String> interviewedPeople, List<Integer> interviewScope) throws NotFoundException {
        auditService.getAuditById(auditId);
        List<ContactPerson> contactPeople = contactPersonService.getAllByIds(new ArrayList<>(interviewedPeople.keySet()));
        Interview interview = interviewService.createInterview(auditId, startDate, endDate);
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

    /**
     * Update the startDate, endDate and status of the interview
     *
     * @param interviewId int
     * @param startDate   Date
     * @param endDate     Date
     * @param status      InterviewStatus
     * @return Updated Interview
     * @throws NotFoundException If stored contactPersonIds are not valid and therefore can not be found
     */
    public BasicInterviewResponse updateInterview(int interviewId, Date startDate, Date endDate, InterviewStatus status) throws NotFoundException {
        Interview interview = interviewService.getInterviewById(interviewId);
        interviewService.updateInterview(interview, startDate, endDate, status);
        return new BasicInterviewResponse(interview, getContactPeopleForInterview(interview));
    }

    /**
     * Helper method for receiving the List of ContactPeople associated with the Interview
     *
     * @param interview Interview
     * @return List of ContactPeople
     * @throws NotFoundException If stored contactPersonIds are not valid and therefore can not be found
     */
    private List<ContactPerson> getContactPeopleForInterview(Interview interview) throws NotFoundException {
        List<Integer> contactPeopleIds = new ArrayList<>();
        interview.getInterviewContactPeopleById().forEach(item -> contactPeopleIds.add(item.getContactPersonId()));
        return contactPersonService.getAllByIds(contactPeopleIds);
    }
}
