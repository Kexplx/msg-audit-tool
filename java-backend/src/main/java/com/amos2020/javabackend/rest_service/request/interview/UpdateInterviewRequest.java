package com.amos2020.javabackend.rest_service.request.interview;

import com.amos2020.javabackend.entity.InterviewStatus;
import com.amos2020.javabackend.rest_service.request.BasicRequest;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

public class UpdateInterviewRequest extends BasicRequest {

    @Getter
    @Setter
    private Date startDate;
    @Getter
    @Setter
    private Date endDate;
    @Getter
    @Setter
    private InterviewStatus status;
    @Getter
    @Setter
    private String goal;


    public void isValid() {
        assertDateIsNotNull(startDate);
        assertDatesAreValid(startDate, endDate);
        assertInterviewStatusIsNotNull(status);
        assertNoteIsValid(goal);
    }

    private void assertInterviewStatusIsNotNull(InterviewStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Interview status can not be null!");
        }
    }
}
