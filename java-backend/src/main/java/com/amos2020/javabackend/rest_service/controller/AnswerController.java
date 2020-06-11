package com.amos2020.javabackend.rest_service.controller;

import com.amos2020.javabackend.entity.Answer;
import com.amos2020.javabackend.rest_service.response.BasicAnswerResponse;
import com.amos2020.javabackend.service.AnswerService;
import com.amos2020.javabackend.service.InterviewService;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnswerController {

    final AnswerService answerService;
    final InterviewService interviewService;

    public AnswerController(AnswerService answerService, InterviewService interviewService) {
        this.answerService = answerService;
        this.interviewService = interviewService;
    }

    public List<BasicAnswerResponse> getAllAnswers(int interviewId) {
        List<BasicAnswerResponse> response = new ArrayList<>();
        List<Answer> answers = answerService.getAnswersByInterviewId(interviewId);
        answers.forEach(a -> response.add(new BasicAnswerResponse(a)));
        return response;
    }

    public BasicAnswerResponse getAnswerByIds(int interviewId, int questionId) {
        Answer answer = answerService.getAnswerByIds(questionId, interviewId);
        return new BasicAnswerResponse(answer);
    }

    public BasicAnswerResponse createAnswer(int interviewId, int questionId) throws NotFoundException {
        interviewService.getInterviewById(interviewId);
        //create Answer
        Answer answer = answerService.createAnswer(questionId, interviewId);
        return new BasicAnswerResponse(answer);
    }

}
