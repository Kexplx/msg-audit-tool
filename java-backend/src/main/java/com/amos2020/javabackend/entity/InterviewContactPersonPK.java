package com.amos2020.javabackend.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class InterviewContactPersonPK implements Serializable {
    private int interviewcontactpersonInterviewId;
    private int interviewcontactpersonContactpersonId;

    @Column(name = "interviewcontactperson_interview_id")
    @Id
    public int getInterviewcontactpersonInterviewId() {
        return interviewcontactpersonInterviewId;
    }

    public void setInterviewcontactpersonInterviewId(int interviewcontactpersonInterviewId) {
        this.interviewcontactpersonInterviewId = interviewcontactpersonInterviewId;
    }

    @Column(name = "interviewcontactperson_contactperson_id")
    @Id
    public int getInterviewcontactpersonContactpersonId() {
        return interviewcontactpersonContactpersonId;
    }

    public void setInterviewcontactpersonContactpersonId(int interviewcontactpersonContactpersonId) {
        this.interviewcontactpersonContactpersonId = interviewcontactpersonContactpersonId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InterviewContactPersonPK that = (InterviewContactPersonPK) o;

        if (interviewcontactpersonInterviewId != that.interviewcontactpersonInterviewId) return false;
        if (interviewcontactpersonContactpersonId != that.interviewcontactpersonContactpersonId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = interviewcontactpersonInterviewId;
        result = 31 * result + interviewcontactpersonContactpersonId;
        return result;
    }
}
