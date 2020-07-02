package com.amos2020.javabackend.rest_service.controller;

import com.amos2020.javabackend.entity.*;
import com.amos2020.javabackend.rest_service.request.interview.InterviewPerson;
import com.amos2020.javabackend.rest_service.response.BasicInterviewResponse;
import com.amos2020.javabackend.service.*;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class InterviewController {

    final InterviewService interviewService;
    final ContactPersonService contactPersonService;
    final AuditService auditService;
    final InterviewContactPersonService interviewContactPersonService;
    final AnswerService answerService;
    final QuestionService questionService;
    final FacCritService facCritService;

    public InterviewController(InterviewService interviewService, ContactPersonService contactPersonService, AuditService auditService, InterviewContactPersonService interviewContactPersonService, AnswerService answerService, QuestionService questionService, FacCritService facCritService) {
        this.interviewService = interviewService;
        this.contactPersonService = contactPersonService;
        this.auditService = auditService;
        this.interviewContactPersonService = interviewContactPersonService;
        this.answerService = answerService;
        this.questionService = questionService;
        this.facCritService = facCritService;
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
        List<ContactPerson> interviewedContactPersons = getContactPersonsForInterview(interview);
        return new BasicInterviewResponse(interview, interviewedContactPersons);
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
            responses.add(new BasicInterviewResponse(interview, getContactPersonsForInterview(interview)));
        }
        return responses;
    }


    /**
     * Get all existing interviews by an auditId
     *
     * @param auditId int
     * @return all interviews
     * @throws NotFoundException if contactPersonIds are not valid and therefore can not be found
     */
    public List<BasicInterviewResponse> getAllInterviewsByAuditId(int auditId) throws NotFoundException {
        List<BasicInterviewResponse> responses = new ArrayList<>();
        for (Interview interview : interviewService.getAllInterviewsByAuditId(auditId)) {
            responses.add(new BasicInterviewResponse(interview, getContactPersonsForInterview(interview)));
        }
        return responses;
    }

    /**
     * Create a new Interview, the associated InterviewContactPersons and empty Answers for the scope of FacCrits
     *
     * @param auditId           int
     * @param startDate         Date
     * @param endDate           Date
     * @param interviewedPeople List of ContactPersonId and Role
     * @param interviewScope    List of FacCritIds
     * @return New interview
     * @throws NotFoundException If the auditId, an contactPersonId or an facCritId is invalid
     */
    public BasicInterviewResponse createInterview(int auditId, Date startDate, Date endDate, String goal, List<InterviewPerson> interviewedPeople, List<Integer> interviewScope) throws NotFoundException {
        auditService.getAuditById(auditId);
        List<Integer> ContactPersonIds = new ArrayList<>();
        interviewedPeople.forEach(item -> ContactPersonIds.add(item.getId()));
        List<ContactPerson> contactPersons = contactPersonService.getAllByIds(ContactPersonIds);
        facCritService.getAllById(interviewScope);

        Interview interview = interviewService.createInterview(auditId, startDate, endDate, goal);
        for (InterviewPerson interviewPerson : interviewedPeople) {
            interviewContactPersonService.create(interview.getId(), interviewPerson.getId(), interviewPerson.getRole());
        }

        // create empty Answers
        for (int facCritId : interviewScope) {
            List<Question> questions = questionService.getQuestionsByFacCritId(facCritId);
            for (Question question : questions) {
                answerService.createAnswer(question.getId(), interview.getId(), question.getFaccritId());
            }
        }
        interview.setAnswersById(answerService.getAnswersByInterviewId(interview.getId()));
        return new BasicInterviewResponse(interview, contactPersons);
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
    public BasicInterviewResponse updateInterview(int interviewId, Date startDate, Date endDate, InterviewStatus status, String goal) throws NotFoundException {
        Interview interview = interviewService.getInterviewById(interviewId);
        interview.setStartDate(startDate);
        interview.setEndDate(endDate);
        interview.setStatus(status);
        interview.setGoal(goal);
        interviewService.updateInterview(interview);
        return new BasicInterviewResponse(interview, getContactPersonsForInterview(interview));
    }

    /**
     * Add a new ContactPerson to an interview if there does not already exists an InterviewContactPerson
     *
     * @param interviewId     int
     * @param contactPersonId int
     * @param role            String
     * @return Updated Interview with the new ContactPerson
     * @throws NotFoundException If interview or contactPerson does not exist
     */
    public BasicInterviewResponse addContactPersonToInterview(int interviewId, int contactPersonId, String role) throws NotFoundException {
        Interview interview = interviewService.getInterviewById(interviewId);
        contactPersonService.getContactPersonById(contactPersonId);
        if (interviewContactPersonService.exists(interviewId, contactPersonId) == null) {
            InterviewContactPerson interviewContactPerson = new InterviewContactPerson();
            interviewContactPerson.setInterviewId(interviewId);
            interviewContactPerson.setContactPersonId(contactPersonId);
            interviewContactPerson.setRole(role);
            interview.getInterviewContactPeopleById().add(interviewContactPerson);
            interviewService.updateInterview(interview);
        }
        return new BasicInterviewResponse(interview, getContactPersonsForInterview(interview));
    }

    /**
     * Remove a ContactPerson from an existing interview
     *
     * @param interviewId     int
     * @param contactPersonId int
     * @return Updated Interview
     * @throws NotFoundException If the if for the interview, contactPerson or the interviewContactPerson is not valid
     */
    public BasicInterviewResponse removeContactPersonFromInterview(int interviewId, int contactPersonId) throws NotFoundException {
        assertIdIsValid(interviewId);
        assertIdIsValid(contactPersonId);

        Interview interview = interviewService.getInterviewById(interviewId);
        contactPersonService.getContactPersonById(contactPersonId);
        InterviewContactPerson interviewContactPerson = interviewContactPersonService.exists(interviewId, contactPersonId);
        if (interviewContactPerson == null) {
            throw new NotFoundException("No Contact Person with " + contactPersonId + " found for interview!");
        }
        interviewContactPersonService.delete(interviewId, contactPersonId);
        interview.getInterviewContactPeopleById().remove(interviewContactPerson);
        interview = interviewService.updateInterview(interview);
        return new BasicInterviewResponse(interview, getContactPersonsForInterview(interview));
    }

    /**
     * Helper method for receiving the List of ContactPersons associated with the Interview
     *
     * @param interview Interview
     * @return List of ContactPersons
     * @throws NotFoundException If stored contactPersonIds are not valid and therefore can not be found
     */
    private List<ContactPerson> getContactPersonsForInterview(Interview interview) throws NotFoundException {
        List<Integer> ContactPersonIds = new ArrayList<>();
        interview.getInterviewContactPeopleById().forEach(item -> ContactPersonIds.add(item.getContactPersonId()));
        return contactPersonService.getAllByIds(ContactPersonIds);
    }

    /**
     * Asserts that the given id is valid
     *
     * @param id Integer
     */
    private void assertIdIsValid(int id) throws IllegalArgumentException {
        if (id < 1) {
            throw new IllegalArgumentException("invalid id");
        }
    }

}
