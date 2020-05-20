package com.amos2020.javabackend.entity;

import javax.persistence.*;

@Entity
@IdClass(InterviewContactPersonPK.class)
public class InterviewContactPerson {
    private int interviewcontactpersonInterviewId;
    private int interviewcontactpersonContactpersonId;
    private Interview interviewByInterviewcontactpersonInterviewId;
    private ContactPerson contactPersonByInterviewcontactpersonContactpersonId;

    @Id
    @Column(name = "interviewcontactperson_interview_id")
    public int getInterviewcontactpersonInterviewId() {
        return interviewcontactpersonInterviewId;
    }

    public void setInterviewcontactpersonInterviewId(int interviewcontactpersonInterviewId) {
        this.interviewcontactpersonInterviewId = interviewcontactpersonInterviewId;
    }

    @Id
    @Column(name = "interviewcontactperson_contactperson_id")
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

        InterviewContactPerson that = (InterviewContactPerson) o;

        if (interviewcontactpersonInterviewId != that.interviewcontactpersonInterviewId) return false;
        return interviewcontactpersonContactpersonId == that.interviewcontactpersonContactpersonId;
    }

    @Override
    public int hashCode() {
        int result = interviewcontactpersonInterviewId;
        result = 31 * result + interviewcontactpersonContactpersonId;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "interviewcontactperson_interview_id", referencedColumnName = "interview_id", nullable = false, insertable = false, updatable = false)
    public Interview getInterviewByInterviewcontactpersonInterviewId() {
        return interviewByInterviewcontactpersonInterviewId;
    }

    public void setInterviewByInterviewcontactpersonInterviewId(Interview interviewByInterviewcontactpersonInterviewId) {
        this.interviewByInterviewcontactpersonInterviewId = interviewByInterviewcontactpersonInterviewId;
    }

    @ManyToOne
    @JoinColumn(name = "interviewcontactperson_contactperson_id", referencedColumnName = "contact_person_id", nullable = false, insertable = false, updatable = false)
    public ContactPerson getContactPersonByInterviewcontactpersonContactpersonId() {
        return contactPersonByInterviewcontactpersonContactpersonId;
    }

    public void setContactPersonByInterviewcontactpersonContactpersonId(ContactPerson contactPersonByInterviewcontactpersonContactpersonId) {
        this.contactPersonByInterviewcontactpersonContactpersonId = contactPersonByInterviewcontactpersonContactpersonId;
    }
}
