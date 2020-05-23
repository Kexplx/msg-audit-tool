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

    @Column(name = "contact_person_id")
    @Id
    public int getContactPersonId() {
        return contactpersonId;
    }

    public void setContactPersonId(int contactPersonId) {
        this.contactpersonId = contactPersonId;
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
