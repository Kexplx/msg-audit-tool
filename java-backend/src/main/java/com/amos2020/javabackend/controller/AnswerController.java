package com.amos2020.javabackend.controller;

import com.amos2020.javabackend.controller.response.BasicAnswerResponse;
import com.amos2020.javabackend.entity.Answer;

import com.amos2020.javabackend.service.AnswerService;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AnswerController {

    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
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

}
