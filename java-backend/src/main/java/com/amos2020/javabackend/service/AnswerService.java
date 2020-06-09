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
     * @param result
     * @param responsible
     * @param documentation
     * @param procedure
     * @param reason
     * @param proof
     * @param annotation
     * @return Answer
     */
    public Answer createAnswer(int questionId, int interviewId, boolean result,
                               boolean responsible, boolean documentation,
                               boolean procedure, String reason, String proof, String annotation) {
        Answer answer = new Answer();
        answer.setQuestionId(questionId);
        answer.setInterviewId(interviewId);
        answer.setResult(result);
        answer.setResponsible(responsible);
        answer.setDocumentation(documentation);
        answer.setProcedure(procedure);
        answer.setReason(reason);
        answer.setProof(proof);
        answer.setAnnotation(annotation);

        return repository.save(answer);
    }

    /**
     * finds an Answer by its question and interview id
     * @param questionId
     * @param interviewId
     * @return Answer
     */
    @Transactional
    public Answer getAnswerByIds(int questionId, int interviewId){
        return repository.findFirstByQuestionIdAndInterviewId(questionId,interviewId);
    }

    /**
     * get all existing Answer items
     * @return Answer
     */
    public List<Answer> getAll(){
        return repository.findAll();
    }
}
