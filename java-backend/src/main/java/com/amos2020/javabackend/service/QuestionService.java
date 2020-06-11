package com.amos2020.javabackend.service;

import com.amos2020.javabackend.entity.Question;
import com.amos2020.javabackend.repository.QuestionRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    @Autowired
    QuestionRepository repository;

    @Transactional
    public List<Question> getQuestionsByFacCritId(int faccritId) {
        return repository.getQuestionsByFaccritId(faccritId);
    }

    public Question getQuestionById(int questionId) throws NotFoundException {
        Optional<Question> question = repository.findById(questionId);
        if (!question.isPresent()) {
            throw new NotFoundException("No question found with id " + question);
        }
        return question.get();
    }
}
