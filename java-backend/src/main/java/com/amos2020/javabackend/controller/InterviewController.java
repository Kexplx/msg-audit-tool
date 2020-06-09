package com.amos2020.javabackend.controller;

import com.amos2020.javabackend.controller.response.BasicInterviewResponse;
import com.amos2020.javabackend.entity.ContactPerson;
import com.amos2020.javabackend.entity.Interview;
import com.amos2020.javabackend.service.ContactPersonService;
import com.amos2020.javabackend.service.InterviewService;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class InterviewController {

    final ContactPersonService contactPersonService;

    final InterviewService interviewService;

    public InterviewController(InterviewService interviewService, ContactPersonService contactPersonService) {
        this.interviewService = interviewService;
        this.contactPersonService = contactPersonService;
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

    private List<ContactPerson> getContactPeopleForInterview(Interview interview) throws NotFoundException {
        List<Integer> contactPeopleIds = new ArrayList<>();
        interview.getInterviewContactPeopleById().forEach(item -> contactPeopleIds.add(item.getContactPersonId()));
        return contactPersonService.getAllByIds(contactPeopleIds);
    }

}
