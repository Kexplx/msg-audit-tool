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

    /**
     * Get all answers for an interview
     *
     * @param interviewId int
     * @return List of Answers
     * @throws NotFoundException If interview can not be found
     */
    public List<BasicAnswerResponse> getAllAnswersByInterviewId(int interviewId) throws NotFoundException {
        List<BasicAnswerResponse> response = new ArrayList<>();
        interviewService.getInterviewById(interviewId);
        List<Answer> answers = answerService.getAnswersByInterviewId(interviewId);
        answers.forEach(a -> response.add(new BasicAnswerResponse(a)));
        return response;
    }

    /**
     * Get all answers
     *
     * @return List of Answers
     * @throws Exception
     */
    public List<BasicAnswerResponse> getAllAnswers() throws Exception {
        List<BasicAnswerResponse> response = new ArrayList<>();
        List<Answer> answers = answerService.getAll();
        answers.forEach(a -> response.add(new BasicAnswerResponse(a)));
        return response;
    }

    /**
     * Get an answer by interview id and question id
     *
     * @param interviewId int
     * @param questionId  int
     * @return Answer
     * @throws NotFoundException If interview id or question id is invalid and can not be found
     */
    public BasicAnswerResponse getAnswerByIds(int interviewId, int questionId) throws NotFoundException {
        Answer answer = answerService.getAnswerByIds(questionId, interviewId);
        if (answer == null) {
            throw new NotFoundException("Answer can not be found");
        }
        return new BasicAnswerResponse(answer);
    }

    /**
     * Create a new answer
     *
     * @param interviewId int
     * @param questionId  int
     * @return Created Answer
     * @throws NotFoundException If interview or question can not be found
     */
    public BasicAnswerResponse createAnswer(int interviewId, int questionId) throws NotFoundException {
        interviewService.getInterviewById(interviewId);
        Question question = questionService.getQuestionById(questionId);
        //create Answer
        Answer answer = answerService.createAnswer(questionId, interviewId, question.getFaccritId());
        return new BasicAnswerResponse(answer);
    }

    /**
     * Update an existing answer
     *
     * @param interviewId   int
     * @param questionId    int
     * @param result        bool
     * @param responsible   bool
     * @param documentation bool
     * @param procedure     bool
     * @param reason        String
     * @param proof         String
     * @param annotation    String
     * @return Updated Answer
     * @throws NotFoundException If an id is invalid and can not be found
     */
    public BasicAnswerResponse updateAnswer(int interviewId,
                                            int questionId,
                                            Boolean result,
                                            Boolean responsible,
                                            Boolean documentation,
                                            Boolean procedure,
                                            String reason,
                                            String proof,
                                            String annotation) throws NotFoundException {
        Answer answer = answerService.getAnswerByIds(questionId, interviewId);
        if (answer == null) {
            throw new NotFoundException("Answer can not be found");
        }
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
