package com.amos2020.javabackend.service;

import com.amos2020.javabackend.entity.FacCrit;
import com.amos2020.javabackend.repository.FacCritRepository;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class FacCritService {

    final FacCritRepository repository;

    public FacCritService(FacCritRepository repository) {
        this.repository = repository;
    }

    /**
     * Get a list of facCrits by a list of facCrit ids
     *
     * @param factorCriteriaIds List<Integer>
     * @return List of FacCrits
     * @throws NotFoundException If a given facCrit id is not found
     */
    public List<FacCrit> getAllById(List<Integer> factorCriteriaIds) throws NotFoundException {
        List<FacCrit> facCrits = repository.findAllById(factorCriteriaIds);
        if (facCrits.size() != factorCriteriaIds.size()) {
            throw new NotFoundException("Not all factor/criteria ids are valid");
        }
        return facCrits;
    }

    /**
     * Checks if facCrit exists and returns the facCrit or throws an exception
     *
     * @param facCritId int
     * @return FacCrit
     * @throws NotFoundException If the given facCrit id is invalid and can not be found
     */
    public FacCrit exists(int facCritId) throws NotFoundException {
        Optional<FacCrit> facCrit = repository.findById(facCritId);
        if (!facCrit.isPresent()) {
            throw new NotFoundException("FacCrit can not be found");
        }
        return facCrit.get();
    }

    /**
     * Returns all existing facCrits
     *
     * @return List<FacCrit>
     */
    public List<FacCrit> getAllFacCrits() {
        return repository.findAll();
    }

    /**
     * Returns all Faccrits ordered by an Interview id ordered by their hierachy
     *
     * @param interviewId
     * @return List<FacCrit>
     */
    public List<FacCrit> getAllFacCritsByInterviewId(int interviewId) {
        List<FacCrit> facCrits = repository.getFacCritsByInterviewId(interviewId);
        Comparator<FacCrit> comparator = new Comparator<FacCrit>() {
            @Override
            public int compare(FacCrit o1, FacCrit o2) {
                if (o1.getReferenceId() == null && o2.getReferenceId() != null) {
                    return o1.getId() < o2.getReferenceId() ? -1 : 1;
                }
                if (o1.getReferenceId() != null && o2.getReferenceId() == null) {
                    return o1.getReferenceId() < o2.getId() ? -1 : 1;
                }
                if (o1.getReferenceId() != null && o2.getReferenceId() != null) {
                    if(o1.getReferenceId() == o2.getReferenceId()){
                        return o1.getId() < o2.getId() ? -1 : 1;
                    }
                    return o1.getReferenceId() < o2.getReferenceId() ? -1 : 1;
                }

                if (o1.getReferenceId() == null && o2.getReferenceId() == null) {
                    return o1.getId() < o2.getId() ? -1 : 1;
                }
                return 0;
            }
        };
        facCrits.sort(comparator);
        return facCrits;
    }

}
