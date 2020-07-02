package com.amos2020.javabackend.rest_service;

import com.amos2020.javabackend.rest_service.controller.QuestionController;
import com.amos2020.javabackend.rest_service.response.BasicQuestionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;

@RestController
@Tag(name = "Question", description = "The endpoint for the question resource")
@CrossOrigin
public class QuestionRestService {

    final QuestionController questionController;

    public QuestionRestService(QuestionController questionController) {
        this.questionController = questionController;
    }

    /**
     * GET Endpoint for receiving a question by a specific id
     *
     * @param questionId int
     * @return ResponseEntity with a BasicQuestionResponse that includes all information regarding the question
     */
    @Operation(summary = "Get Question by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Receive question with specific id"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    @GetMapping("/questions/{id}")
    public ResponseEntity<BasicQuestionResponse> getInterviewById(@PathVariable("id") @Parameter(name = "id", example = "1") @Min(1) int questionId) {
        BasicQuestionResponse response;
        try {
            response = questionController.getInterviewById(questionId);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(response);
    }
}