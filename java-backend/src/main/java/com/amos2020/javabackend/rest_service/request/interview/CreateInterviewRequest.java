package com.amos2020.javabackend.rest_service.request.interview;

import com.amos2020.javabackend.rest_service.request.BasicRequest;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CreateInterviewRequest extends BasicRequest {

    @Getter
    @Setter
    private int auditId;
    @Getter
    @Setter
    private Date startDate;
    @Getter
    @Setter
    private Date endDate;
    @Getter
    @Setter
    private String goal;
    @Getter
    @Setter
    private HashMap<Integer, String> interviewedPeople;
    @Getter
    @Setter
    private List<Integer> interviewScope;

    public void isValid() {
        assertIdIsValid(auditId);
        assertDateIsNotNull(startDate);
        assertDatesAreValid(startDate, endDate);
        assertNoteIsValid(goal);
        assertIdsAreValid(new ArrayList<>(interviewedPeople.keySet()));
        assertIdsAreValid(interviewScope);
    }
}
