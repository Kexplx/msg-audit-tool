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
     * @return ResponseEntity<BasicInterviewResponse>
     */
    @GetMapping("/interview/{id}")
    public ResponseEntity<BasicInterviewResponse> getInterviewById(@PathVariable("id") int interviewId) {
        BasicInterviewResponse response;

        try {
            Interview interview = interviewService.getInterviewById(interviewId);

            // Get ContactPeople for interview
            List<Integer> contactPeopleIds = new ArrayList<>();
            interview.getInterviewContactPeopleById().forEach(item -> contactPeopleIds.add(item.getContactPersonId()));
            List<ContactPerson> interviewedContactPeople = contactPersonService.getAllByIds(contactPeopleIds);
            // Build response
            response = new BasicInterviewResponse(interview, interviewedContactPeople);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(response);
    }
}
