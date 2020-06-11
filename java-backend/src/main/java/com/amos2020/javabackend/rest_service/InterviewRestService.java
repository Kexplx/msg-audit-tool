package com.amos2020.javabackend.rest_service;

import com.amos2020.javabackend.rest_service.controller.InterviewController;
import com.amos2020.javabackend.rest_service.request.interview.CreateInterviewRequest;
import com.amos2020.javabackend.rest_service.response.BasicInterviewResponse;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InterviewRestService {

    final InterviewController interviewController;

    public InterviewRestService(InterviewController interviewController) {
        this.interviewController = interviewController;
    }

    /**
     * GET Endpoint for receiving a interview by a specific id
     *
     * @param interviewId int
     * @return ResponseEntity with a BasicInterviewResponse that includes all information regarding the interview
     */
    @GetMapping("/interviews/{id}")
    public ResponseEntity<BasicInterviewResponse> getInterviewById(@PathVariable("id") int interviewId) {
        BasicInterviewResponse response;

        try {
            response = interviewController.getInterviewById(interviewId);
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
    @GetMapping("/interviews")
    public ResponseEntity<List<BasicInterviewResponse>> getAllInterviews() {
        List<BasicInterviewResponse> responses;
        try {
            responses = interviewController.getAllInterviews();
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
    @PostMapping("/interviews")
    public ResponseEntity<BasicInterviewResponse> createInterview(@RequestBody CreateInterviewRequest request) {
        BasicInterviewResponse response;

        try {
            request.isValid();
            response = interviewController.createInterview(request.getAuditId(), request.getStartDate(), request.getEndDate(), request.getInterviewedPeople(), request.getInterviewScope());
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(response);
    }


}
