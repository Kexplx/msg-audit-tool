package com.amos2020.javabackend.rest_service.controller;

import com.amos2020.javabackend.entity.FacCrit;
import com.amos2020.javabackend.rest_service.response.BasicFacCritResponse;
import com.amos2020.javabackend.service.FacCritService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FacCritController {

    final FacCritService facCritService;

    public FacCritController(FacCritService facCritService) {
        this.facCritService = facCritService;
    }

    /**
     * Get all existing facCrits
     *
     * @return all facCrits
     */
    public List<BasicFacCritResponse> getAllFacCrits() {
        List<BasicFacCritResponse> responses = new ArrayList<>();
        for (FacCrit facCrit : facCritService.getAllFacCrits()) {
            responses.add(new BasicFacCritResponse(facCrit));
        }
        return responses;
    }

    /**
     * get all Faccrits for a Interview
     *
     * @param interviewId
     * @return
     */
    public List<BasicFacCritResponse> getAllFacCritsByInterviewId(int interviewId){
        List<BasicFacCritResponse> responses = new ArrayList<>();
        List<FacCrit> facCrits = facCritService.getAllFacCritsByInterviewId(interviewId);
        facCrits.forEach(facCrit -> responses.add(new BasicFacCritResponse(facCrit)));
        return responses;
    }
}
