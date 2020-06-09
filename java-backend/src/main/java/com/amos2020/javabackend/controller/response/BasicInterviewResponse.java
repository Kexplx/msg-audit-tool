package com.amos2020.javabackend.controller.response;

import com.amos2020.javabackend.entity.Answer;
import com.amos2020.javabackend.entity.ContactPerson;
import com.amos2020.javabackend.entity.Interview;
import com.amos2020.javabackend.entity.InterviewStatus;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BasicInterviewResponse {

    public BasicInterviewResponse(Interview interview, List<ContactPerson> interviewedPeople) {
        this.interviewId = interview.getId();
        this.auditId = interview.getAuditId();
        this.startDate = interview.getStartDate();
        this.endDate = interview.getEndDate();
        this.status = interview.getStatus();
        this.answers = new ArrayList<>();
        if (interview.getAnswersById() != null) {
            this.answers = interview.getAnswersById();
        }
        this.interviewedPeople = interviewedPeople;
    }

    @Getter
    @Setter
    private int interviewId;

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
    private InterviewStatus status;

    @Getter
    @Setter
    private Collection<Answer> answers;

    @Getter
    @Setter
    private List<ContactPerson> interviewedPeople;
}
