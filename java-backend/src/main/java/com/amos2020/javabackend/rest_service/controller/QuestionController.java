package com.amos2020.javabackend.rest_service.controller;

import com.amos2020.javabackend.entity.Question;
import com.amos2020.javabackend.rest_service.response.BasicQuestionResponse;
import com.amos2020.javabackend.service.QuestionService;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class QuestionController {
    final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    /**
     * Get a specific question by an id
     *
     * @param questionId int
     * @return Question or Exception
     * @throws NotFoundException if question does not exist
     */
    public BasicQuestionResponse getInterviewById(int questionId) throws NotFoundException {
        Question question = questionService.getQuestionById(questionId);
        return new BasicQuestionResponse(question);
    }
}
