package com.amos2020.javabackend.controller.request.interview;

import com.amos2020.javabackend.controller.request.BasicRequest;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

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
    private HashMap<Integer, String> interviewedPeople;

    @Override
    public void isValid() {
        assertIdIsValid(auditId);
        assertDateIsNotNull(startDate);
        assertDatesAreValid(startDate, endDate);
        assertIdsAreValid(new ArrayList<>(interviewedPeople.keySet()));
    }
}
