package com.amos2020.javabackend.controller.request;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

public class CreateAuditRequest extends BasicRequest {
    // mandatory
    @Getter
    @Setter
    private String auditName;

    // mandatory
    @Getter
    @Setter
    private Date startDate;

    // Not mandatory
    @Getter
    @Setter
    private Date endDate;

    // Not mandatory
    @Getter
    @Setter
    private List<Integer> scope;

    // Not mandatory
    @Getter
    @Setter
    private List<Integer> contactPeople;

    public void isValid() throws IllegalArgumentException {
        assertAuditNameIsValid(auditName);
        assertDateIsNotNull(startDate);
        //assertDateIsNotNull(endDate);
        assertDatesAreValid(startDate, endDate);
        assertIdsAreValid(contactPeople);
        assertIdsAreValid(scope);
    }


}
