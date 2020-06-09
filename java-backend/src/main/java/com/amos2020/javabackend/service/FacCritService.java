package com.amos2020.javabackend.service;

import com.amos2020.javabackend.entity.FacCrit;
import com.amos2020.javabackend.repository.FacCritRepository;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FacCritService {

    final FacCritRepository repository;

    public FacCritService(FacCritRepository repository) {
        this.repository = repository;
    }

    public List<FacCrit> getAllById(List<Integer> factorCriteriaIds) throws NotFoundException {
        List<FacCrit> facCrits = repository.findAllById(factorCriteriaIds);
        if (facCrits.size() != factorCriteriaIds.size()) {
            throw new NotFoundException("Not all factor/criteria ids are valid");
        }
        return facCrits;
    }


    public FacCrit exists(int facCritId) throws NotFoundException {
        Optional<FacCrit> facCrit = repository.findById(facCritId);
        if (!facCrit.isPresent()) {
            throw new NotFoundException("FacCrit can not be found");
        }
        return facCrit.get();
    }
}