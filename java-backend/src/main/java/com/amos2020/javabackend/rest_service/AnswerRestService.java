package com.amos2020.javabackend.rest_service;

import com.amos2020.javabackend.rest_service.controller.AnswerController;
import com.amos2020.javabackend.rest_service.request.answer.CreateAnswerRequest;
import com.amos2020.javabackend.rest_service.response.BasicAnswerResponse;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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
    @GetMapping("/answers/interview/{id}")
    public ResponseEntity<List<BasicAnswerResponse>> getAnswersByInterviewId(@PathVariable("id") int interviewId) {
        List<BasicAnswerResponse> response;
        try {
            response = answerController.getAllAnswers(interviewId);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
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
    @GetMapping("/answers/interview/{id1}/question/{id2}")
    public ResponseEntity<BasicAnswerResponse> getAnswerByIds(@PathVariable("id1") int interviewId,
                                                              @PathVariable("id2") int questionId) {
        BasicAnswerResponse response;
        try {
            response = answerController.getAnswerByIds(interviewId, questionId);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    /**
     * POST Endpoint for creating an empty Answer
     *
     * @return ResponseEntity with Answer
     */
    @PostMapping("/answers")
    public ResponseEntity<BasicAnswerResponse> createAnswer(@RequestBody CreateAnswerRequest request) {
        BasicAnswerResponse response;

        try {
            request.isValid();
            response = answerController.createAnswer(request.getInterviewId(), request.getQuestionId());

        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(response);
    }

}
