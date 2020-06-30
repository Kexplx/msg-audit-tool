package com.amos2020.javabackend.rest_service;

import com.amos2020.javabackend.rest_service.controller.AnswerController;
import com.amos2020.javabackend.rest_service.request.answer.CreateAnswerRequest;
import com.amos2020.javabackend.rest_service.request.answer.UpdateAnswerRequest;
import com.amos2020.javabackend.rest_service.response.BasicAnswerResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Tag(name = "Answer", description = "The endpoints for the Answer resource")
public class AnswerRestService {

    private final AnswerController answerController;

    public AnswerRestService(AnswerController answerController) {
        this.answerController = answerController;
    }

    /**
     * GET Endpoint for receiving a list of Answers associated with a specific Interview
     *
     * @param interviewId int
     * @return ResponseEntity containing a list with BasicAnswerResponses
     */
    @Operation(summary = "Get all Answers by Interview id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Received Answers by Interview id"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    @GetMapping("/answers/interview/{id}")
    public ResponseEntity<List<BasicAnswerResponse>> getAnswersByInterviewId(@PathVariable("id") int interviewId) {
        List<BasicAnswerResponse> response;
        try {
            response = answerController.getAllAnswersByInterviewId(interviewId);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(response);
    }

    /**
     * GET Endpoint for receiving an Answers by its question and interview id
     *
     * @param interviewId int
     * @param questionId  int
     * @return ResponseEntity containing the Answer
     */
    @Operation(summary = "Get Answer by Interview and Question id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Received answer"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    @GetMapping("/answers/interview/{id1}/question/{id2}")
    public ResponseEntity<BasicAnswerResponse> getAnswerByIds(@PathVariable("id1") int interviewId,
                                                              @PathVariable("id2") int questionId) {
        BasicAnswerResponse response;
        try {
            response = answerController.getAnswerByIds(interviewId, questionId);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(response);
    }

    /**
     * POST Endpoint for creating an empty Answer
     *
     * @return ResponseEntity with Answer
     */
    @Operation(summary = "Create a new Answer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created new Answer"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    @PostMapping("/answers")
    public ResponseEntity<BasicAnswerResponse> createAnswer(@RequestBody @Valid CreateAnswerRequest request) {
        BasicAnswerResponse response;
        try {
            response = answerController.createAnswer(request.getInterviewId(), request.getQuestionId());
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(response);
    }

    /**
     * PUT Endpoint for updating an Answer
     *
     * @param interviewId int
     * @param questionId  int
     * @param request     BasicAnswerResponse
     * @return Updated Answer
     */
    @Operation(summary = "Update an existing Answer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated Answer"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    @PutMapping("/answers/interview/{id1}/question/{id2}")
    public ResponseEntity<BasicAnswerResponse> updateAnswer(@PathVariable("id1") int interviewId,
                                                            @PathVariable("id2") int questionId,
                                                            @RequestBody @Valid UpdateAnswerRequest request) {
        BasicAnswerResponse response;
        try {
            response = answerController.updateAnswer(interviewId, questionId, request.getResult(),
                    request.getResponsible(), request.getDocumentation(), request.getProcedure(),
                    request.getReason(), request.getProof(), request.getAnnotation());
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(response);
    }
}
