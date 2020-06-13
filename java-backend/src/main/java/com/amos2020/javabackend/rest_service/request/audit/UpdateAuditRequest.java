package com.amos2020.javabackend.rest_service.request.audit;

import com.amos2020.javabackend.rest_service.request.BasicRequest;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

public class UpdateAuditRequest extends BasicRequest {

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

    public void isValid() throws IllegalArgumentException {
        assertNameIsValid(auditName);
        assertDateIsNotNull(startDate);
        assertDatesAreValid(startDate, endDate);
    }
}
