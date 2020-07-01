package com.amos2020.javabackend.rest_service;

import com.amos2020.javabackend.rest_service.controller.InterviewController;
import com.amos2020.javabackend.rest_service.request.interview.CreateInterviewRequest;
import com.amos2020.javabackend.rest_service.request.interview.InterviewAddContactPersonRequest;
import com.amos2020.javabackend.rest_service.request.interview.UpdateInterviewRequest;
import com.amos2020.javabackend.rest_service.response.BasicInterviewResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@Validated
@Tag(name = "Interview", description = "The endpoints for the interview resource")
@CrossOrigin
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
    @Operation(summary = "Get Interview by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Receive interview with specific id"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    @GetMapping("/interviews/{id}")
    public ResponseEntity<BasicInterviewResponse> getInterviewById(@PathVariable("id") @Parameter(name = "id", example = "1") @Min(1) int interviewId) {
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
    @Operation(summary = "Get all interviews")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Receive all existing interviews")
    })
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
    @Operation(summary = "Create a new interview")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "New interview created"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    @PostMapping("/interviews")
    public ResponseEntity<BasicInterviewResponse> createInterview(@RequestBody @Valid CreateInterviewRequest request) {
        BasicInterviewResponse response;

        try {
            request.isValid();
            response = interviewController.createInterview(request.getAuditId(), request.getStartDate(), request.getEndDate(), request.getGoal(), request.getInterviewedPeople(), request.getInterviewScope());
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
    @Operation(summary = "Change an existing interview")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Existing interview changed"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    @PutMapping("/interviews/{interviewId}")
    public ResponseEntity<BasicInterviewResponse> updateInterview(@PathVariable("interviewId") @Parameter(name = "interviewId", example = "1") @Min(1) int interviewId, @RequestBody @Valid UpdateInterviewRequest request) {
        BasicInterviewResponse response;

        try {
            request.isValid();
            request.assertIdIsValid(interviewId);
            response = interviewController.updateInterview(interviewId, request.getStartDate(), request.getEndDate(), request.getStatus(), request.getGoal());
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
    @Operation(summary = "Add a contact person to an existing interview")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contact person added to interview"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    @PutMapping("/interviews/{interviewId}/add/person")
    public ResponseEntity<BasicInterviewResponse> addContactPersonToInterview(@PathVariable("interviewId") @Parameter(name = "interviewId", example = "1") @Min(1) int interviewId, @RequestBody @Valid InterviewAddContactPersonRequest request) {
        BasicInterviewResponse response;
        try {
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
    @Operation(summary = "Delete an existing interview")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Existing interview deleted"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    @DeleteMapping("/interviews/{interviewId}/delete/person/{contactPersonId}")
    public ResponseEntity<BasicInterviewResponse> removeContactPersonFromInterview(@PathVariable("interviewId") @Parameter(name = "interviewId", example = "1") @Min(1) int interviewId, @PathVariable("contactPersonId") @Parameter(name = "contactPersonId", example = "1") @Min(1) int contactPersonId) {
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

    /**
     * GET Endpoint for receiving all existing interviews by auditId
     *
     * @return ResponseEntity with a List of the Interviews as BasicInterviewResponses
     */
    @Operation(summary = "Get all interviews by auditId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Receive all existing interviews by auditId"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    @GetMapping("/audits/{auditId}/interviews")
    public ResponseEntity<List<BasicInterviewResponse>> getAllInterviewsByAuditId(@PathVariable("auditId") @Parameter(name = "auditId", example = "1") @Min(1) int auditId) {
        List<BasicInterviewResponse> responses;
        try {
            responses = interviewController.getAllInterviewsByAuditId(auditId);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(responses);
    }
}
