package com.amos2020.javabackend.service;

import com.amos2020.javabackend.entity.Answer;
import com.amos2020.javabackend.repository.AnswerRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class AnswerService {

    private final AnswerRepository repository;

    public AnswerService(AnswerRepository answerRepository) {
        this.repository = answerRepository;
    }

    /**
     * Creates and stores an Answer
     * @param questionId
     * @param interviewId
     * @return Answer
     */
    public Answer createAnswer(int questionId, int interviewId) {
        Answer answer = new Answer();
        answer.setQuestionId(questionId);
        answer.setInterviewId(interviewId);
        answer.setResult(false);
        answer.setResponsible(false);
        answer.setDocumentation(false);
        answer.setProcedure(false);
        answer.setReason("");
        answer.setProof("");
        answer.setAnnotation("");

        return repository.save(answer);
    }

    /**
     * gets an Answer by its question and interview id
     * @param questionId
     * @param interviewId
     * @return Answer
     */
    @Transactional
    public Answer getAnswerByIds(int questionId, int interviewId) {
        return repository.findFirstByQuestionIdAndInterviewId(questionId,interviewId);
    }

    /**
     * get all Answers belonging to a specific interview
     * @param interviewId
     * @return
     */
    @Transactional
    public List<Answer> getAnswersByInterviewId(int interviewId){
        return repository.getAnswersByInterviewId(interviewId);
    }

    /**
     * get all existing Answer items
     * @return Answer
     */
    public List<Answer> getAll(){
        return repository.findAll();
    }
}
