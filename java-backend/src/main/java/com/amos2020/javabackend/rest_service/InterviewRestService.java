package com.amos2020.javabackend.rest_service;

import com.amos2020.javabackend.rest_service.controller.InterviewController;
import com.amos2020.javabackend.rest_service.request.interview.CreateInterviewRequest;
import com.amos2020.javabackend.rest_service.request.interview.InterviewAddContactPersonRequest;
import com.amos2020.javabackend.rest_service.request.interview.UpdateInterviewRequest;
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

    /**
     * PUT Endpoint for updating the data of an existing interview
     *
     * @return ResponseEntity with the updated interview
     */
    @PutMapping("/interviews/{interviewId}")
    public ResponseEntity<BasicInterviewResponse> updateInterview(@PathVariable("interviewId") int interviewId, @RequestBody UpdateInterviewRequest request) {
        BasicInterviewResponse response;

        try {
            request.isValid();
            request.assertIdIsValid(interviewId);
            response = interviewController.updateInterview(interviewId, request.getStartDate(), request.getEndDate(), request.getStatus());
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(response);
    }

    /**
     * PUT for adding a contactPerson to an interview
     *
     * @param interviewId int
     * @param request     InterviewAddContactPersonRequest
     * @return BasicInterviewResponse
     */
    @PutMapping("/interviews/{interviewId}/add/person")
    public ResponseEntity<BasicInterviewResponse> addContactPersonToInterview(@PathVariable("interviewId") int interviewId, @RequestBody InterviewAddContactPersonRequest request) {
        BasicInterviewResponse response;
        try {
            request.isValid();
            request.assertIdIsValid(interviewId);
            response = interviewController.addContactPersonToInterview(interviewId, request.getContactPersonId(), request.getRole());
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(response);
    }

    /**
     * PUT for removing a contactPerson from an interview
     *
     * @param interviewId     int
     * @param contactPersonId int
     * @return BasicInterviewResponse
     */
    @DeleteMapping("/interviews/{interviewId}/delete/person/{contactPersonId}")
    public ResponseEntity<BasicInterviewResponse> removeContactPersonFromInterview(@PathVariable("interviewId") int interviewId, @PathVariable("contactPersonId") int contactPersonId) {
        BasicInterviewResponse response;
        try {
            response = interviewController.removeContactPersonFromInterview(interviewId, contactPersonId);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(response);
    }
}
