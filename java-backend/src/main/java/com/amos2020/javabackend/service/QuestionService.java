package com.amos2020.javabackend.service;

import com.amos2020.javabackend.entity.Question;
import com.amos2020.javabackend.repository.QuestionRepository;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    final QuestionRepository repository;

    public QuestionService(QuestionRepository repository) {
        this.repository = repository;
    }

    /**
     * Get a all Questions for a facCrit by the facCrit id
     *
     * @param faccritId int
     * @return List<Question>
     */
    @Transactional
    public List<Question> getQuestionsByFacCritId(int faccritId) {
        return repository.getQuestionsByFaccritId(faccritId);
    }

    /**
     * Get a question by id
     *
     * @param questionId int
     * @return Question
     * @throws NotFoundException If the question id is invalid and can not be found
     */
    public Question getQuestionById(int questionId) throws NotFoundException {
        Optional<Question> question = repository.findById(questionId);
        if (!question.isPresent()) {
            throw new NotFoundException("No question found with id " + question);
        }
        return question.get();
    }
}
