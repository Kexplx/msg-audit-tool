package com.amos2020.javabackend.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class InterviewContactPersonPK implements Serializable {
    private int interviewId;
    private int contactpersonId;

    @Column(name = "interview_id")
    @Id
    public int getInterviewId() {
        return interviewId;
    }

    public void setInterviewId(int interviewId) {
        this.interviewId = interviewId;
    }

    @Column(name = "contactperson_id")
    @Id
    public int getContactpersonId() {
        return contactpersonId;
    }

    public void setContactpersonId(int contactpersonId) {
        this.contactpersonId = contactpersonId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InterviewContactPersonPK that = (InterviewContactPersonPK) o;

        if (interviewId != that.interviewId) return false;
        return contactpersonId == that.contactpersonId;
    }

    @Override
    public int hashCode() {
        int result = interviewId;
        result = 31 * result + contactpersonId;
        return result;
    }
}
