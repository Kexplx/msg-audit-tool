package com.amos2020.javabackend.service;

import com.amos2020.javabackend.entity.Question;
import com.amos2020.javabackend.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuestionService {
    @Autowired
    QuestionRepository repository;

    @Transactional
    public List<Question> getQuestionsByFacCritId(int faccritId) {
        return repository.getQuestionsByFaccritId(faccritId);
    }
}
