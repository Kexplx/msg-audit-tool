package com.amos2020.javabackend.controller;

import com.amos2020.javabackend.controller.request.answer.CreateAnswerRequest;
import com.amos2020.javabackend.controller.response.BasicAnswerResponse;
import com.amos2020.javabackend.entity.Answer;

import com.amos2020.javabackend.service.AnswerService;
import com.amos2020.javabackend.service.InterviewService;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AnswerController {

    private final AnswerService answerService;
    private final InterviewService interviewService;

    public AnswerController(AnswerService answerService, InterviewService interviewService) {
        this.answerService = answerService;
        this.interviewService = interviewService;
    }

    /**
     * GET Endpoint for receiving a list of Answers associated with a specific Interview
     *
     * @param interviewId
     * @return ResponseEntity containg a list with BasicAnswerResponses
     */
    @GetMapping("/answer/interview/{id}")
    public ResponseEntity<List<BasicAnswerResponse>> getAnswersByInterviewId(@PathVariable("id") int interviewId) {
        List<BasicAnswerResponse> response = new ArrayList<>();
        try {
            List<Answer> answers = answerService.getAnswersByInterviewId(interviewId);
            answers.forEach(a -> response.add(new BasicAnswerResponse(a)));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    /**
     * GET Endpoint for receiving an Answers by its question and interview id
     *
     * @param interviewId
     * @param questionId
     * @return ResponseEntity containing the Answer
     */
    @GetMapping("/answer/interview/{id1}/question/{id2}")
    public ResponseEntity<BasicAnswerResponse> getAnswerByIds(@PathVariable("id1") int interviewId,
                                                              @PathVariable("id2") int questionId) {
        BasicAnswerResponse response;
        try {
            Answer answer = answerService.getAnswerByIds(questionId, interviewId);
            response = new BasicAnswerResponse(answer);
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
    @PostMapping("/answer")
    public ResponseEntity<BasicAnswerResponse> createAnswer(@RequestBody CreateAnswerRequest request) {
        BasicAnswerResponse response;

        try {
            request.isValid();
            // TODO check if question by id exists
            // check interview exists
            interviewService.getInterviewById(request.getInterviewId());
            //create Answer
            Answer answer = answerService.createAnswer(request.getQuestionId(), request.getInterviewId());
            response = new BasicAnswerResponse(answer);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(response);
    }

}
