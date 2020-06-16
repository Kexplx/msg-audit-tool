package com.amos2020.javabackend.rest_service.controller;

import com.amos2020.javabackend.entity.Answer;
import com.amos2020.javabackend.entity.Question;
import com.amos2020.javabackend.rest_service.response.BasicAnswerResponse;
import com.amos2020.javabackend.service.AnswerService;
import com.amos2020.javabackend.service.InterviewService;
import com.amos2020.javabackend.service.QuestionService;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnswerController {

    final AnswerService answerService;
    final InterviewService interviewService;
    final QuestionService questionService;

    public AnswerController(AnswerService answerService, InterviewService interviewService, QuestionService questionService) {
        this.answerService = answerService;
        this.interviewService = interviewService;
        this.questionService = questionService;
    }

    public List<BasicAnswerResponse> getAllAnswersByInterviewId(int interviewId) throws NotFoundException {
        List<BasicAnswerResponse> response = new ArrayList<>();
        List<Answer> answers = answerService.getAnswersByInterviewId(interviewId);
        answers.forEach(a -> response.add(new BasicAnswerResponse(a)));
        return response;
    }

    public BasicAnswerResponse getAnswerByIds(int interviewId, int questionId) throws NotFoundException {
        Answer answer = answerService.getAnswerByIds(questionId, interviewId);
        return new BasicAnswerResponse(answer);
    }

    public BasicAnswerResponse createAnswer(int interviewId, int questionId) throws NotFoundException {
        interviewService.getInterviewById(interviewId);
        Question question = questionService.getQuestionById(questionId);
        //create Answer
        Answer answer = answerService.createAnswer(questionId, interviewId, question.getFaccritId());
        return new BasicAnswerResponse(answer);
    }

    public BasicAnswerResponse updateAnswer(int interviewId,
                                            int questionId,
                                            int faccritId,
                                            Boolean result,
                                            Boolean responsible,
                                            Boolean documentation,
                                            Boolean procedure,
                                            String reason,
                                            String proof,
                                            String annotation) throws NotFoundException{
        Answer answer = answerService.getAnswerByIds(questionId, interviewId);
        answer.setFaccritId(faccritId);
        answer.setResult(result);
        answer.setResponsible(responsible);
        answer.setDocumentation(documentation);
        answer.setProcedure(procedure);
        answer.setReason(reason);
        answer.setProof(proof);
        answer.setAnnotation(annotation);

        answer = answerService.updateAnswer(answer);
        return new BasicAnswerResponse(answer);
    }

}
