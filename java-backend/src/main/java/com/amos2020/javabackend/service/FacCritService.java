package com.amos2020.javabackend.service;

import com.amos2020.javabackend.entity.FacCrit;
import com.amos2020.javabackend.repository.FacCritRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class FacCritService {
    private static FacCritService instance;

    @Autowired
    FacCritRepository repository;

    public static FacCritService getInstance() {
        if (instance == null) {
            instance = new FacCritService();
        }
        return instance;
    }

    public List<FacCrit> getAllById(List<Integer> factorCriteriaIds) {
        return repository.findAllById(factorCriteriaIds);
    }
}
