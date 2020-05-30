package com.amos2020.javabackend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@IdClass(InterviewContactPersonPK.class)
@Table(name = "interview_contact_person")
public class InterviewContactPerson {
    private int interviewId;
    private int contactPersonId;
    private String role;
    private Interview interviewByInterviewId;
    private ContactPerson contactPersonByContactPersonId;

    @Id
    @Column(name = "interview_id")
    public int getInterviewId() {
        return interviewId;
    }

    public void setInterviewId(int interviewId) {
        this.interviewId = interviewId;
    }

    @Id
    @Column(name = "contact_person_id")
    public int getContactPersonId() {
        return contactPersonId;
    }

    public void setContactPersonId(int contactPersonId) {
        this.contactPersonId = contactPersonId;
    }

    @Basic
    @NotNull
    @Column(name = "role")
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @ManyToOne
    @JoinColumn(name = "interview_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Interview getInterviewByInterviewId() {
        return interviewByInterviewId;
    }

    public void setInterviewByInterviewId(Interview interviewByInterviewId) {
        this.interviewByInterviewId = interviewByInterviewId;
    }

    @ManyToOne
    @JoinColumn(name = "contact_person_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public ContactPerson getContactPersonByContactPersonId() {
        return contactPersonByContactPersonId;
    }

    public void setContactPersonByContactPersonId(ContactPerson contactPersonByContactPersonId) {
        this.contactPersonByContactPersonId = contactPersonByContactPersonId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InterviewContactPerson that = (InterviewContactPerson) o;

        if (interviewId != that.interviewId) return false;
        if (contactPersonId != that.contactPersonId) return false;
        return Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        int result = interviewId;
        result = 31 * result + contactPersonId;
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }
}
