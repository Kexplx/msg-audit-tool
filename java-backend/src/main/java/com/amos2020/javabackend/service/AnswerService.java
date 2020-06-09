package com.amos2020.javabackend.service;

import com.amos2020.javabackend.repository.AnswerRepository;

public class AnswerService {

    private final AnswerRepository repository;

    public AnswerService(AnswerRepository answerRepository) {
        this.repository = answerRepository;
    }
}
