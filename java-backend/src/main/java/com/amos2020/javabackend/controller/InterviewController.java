package com.amos2020.javabackend.controller;

import com.amos2020.javabackend.controller.request.interview.CreateInterviewRequest;
import com.amos2020.javabackend.controller.response.BasicInterviewResponse;
import com.amos2020.javabackend.entity.ContactPerson;
import com.amos2020.javabackend.entity.Interview;
import com.amos2020.javabackend.service.AuditService;
import com.amos2020.javabackend.service.ContactPersonService;
import com.amos2020.javabackend.service.InterviewContactPersonService;
import com.amos2020.javabackend.service.InterviewService;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class InterviewController {

    final AuditService auditService;
    final ContactPersonService contactPersonService;
    final InterviewContactPersonService interviewContactPersonService;
    final InterviewService interviewService;

    public InterviewController(InterviewService interviewService, ContactPersonService contactPersonService, AuditService auditService, InterviewContactPersonService interviewContactPersonService) {
        this.interviewService = interviewService;
        this.contactPersonService = contactPersonService;
        this.auditService = auditService;
        this.interviewContactPersonService = interviewContactPersonService;
    }

    /**
     * GET Endpoint for receiving a interview by a specific id
     *
     * @param interviewId int
     * @return ResponseEntity with a BasicInterviewResponse that includes all information regarding the interview
     */
    @GetMapping("/interview/{id}")
    public ResponseEntity<BasicInterviewResponse> getInterviewById(@PathVariable("id") int interviewId) {
        BasicInterviewResponse response;

        try {
            Interview interview = interviewService.getInterviewById(interviewId);

            // Get ContactPeople for interview
            List<ContactPerson> interviewedContactPeople = getContactPeopleForInterview(interview);
            // Build response
            response = new BasicInterviewResponse(interview, interviewedContactPeople);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(response);
    }

    /**
     * GET Endpoint for receiving all existing interviews
     *
     * @return ResponseEntity with a List of the Interviews as BasicInterviewResponses
     */
    @GetMapping("/interview")
    public ResponseEntity<List<BasicInterviewResponse>> getAllInterviews() {
        List<BasicInterviewResponse> responses = new ArrayList<>();

        try {
            for (Interview interview : interviewService.getAllInterviews()) {
                responses.add(new BasicInterviewResponse(interview, getContactPeopleForInterview(interview)));
            }
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(responses);
    }

    /**
     * POST Endpoint for creating a new interview
     *
     * @return ResponseEntity with the new interview
     */
    @PostMapping("/interview")
    public ResponseEntity<BasicInterviewResponse> createInterview(@RequestBody CreateInterviewRequest request) {
        BasicInterviewResponse response;

        try {
            request.isValid();

            // check if audit exists
            auditService.getAuditById(request.getAuditId());
            // check if all contactPersons exist
            List<ContactPerson> contactPeople = contactPersonService.getAllByIds(new ArrayList<>(request.getInterviewedPeople().keySet()));
            // create interview
            Interview interview = interviewService.createInterview(request.getAuditId(), request.getStartDate(), request.getEndDate());
            // create interviewContactPersons
            for (int contactPersonId : request.getInterviewedPeople().keySet()) {
                interviewContactPersonService.create(interview.getId(), contactPersonId, request.getInterviewedPeople().get(contactPersonId));
            }
            response = new BasicInterviewResponse(interview, contactPeople);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(response);
    }

    private List<ContactPerson> getContactPeopleForInterview(Interview interview) throws NotFoundException {
        List<Integer> contactPeopleIds = new ArrayList<>();
        interview.getInterviewContactPeopleById().forEach(item -> contactPeopleIds.add(item.getContactPersonId()));
        return contactPersonService.getAllByIds(contactPeopleIds);
    }

}
