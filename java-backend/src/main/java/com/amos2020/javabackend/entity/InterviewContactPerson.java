package com.amos2020.javabackend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@IdClass(InterviewContactPersonPK.class)
public class InterviewContactPerson {
    private int interviewId;
    private int contactpersonId;
    private String role;
    private Interview interviewByInterviewId;
    private ContactPerson contactPersonByContactpersonId;

    @Id
    @Column(name = "interview_id")
    public int getInterviewId() {
        return interviewId;
    }

    public void setInterviewId(int interviewId) {
        this.interviewId = interviewId;
    }

    @Id
    @Column(name = "contactperson_id")
    public int getContactpersonId() {
        return contactpersonId;
    }

    public void setContactpersonId(int contactpersonId) {
        this.contactpersonId = contactpersonId;
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
    @JoinColumn(name = "contactperson_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public ContactPerson getContactPersonByContactpersonId() {
        return contactPersonByContactpersonId;
    }

    public void setContactPersonByContactpersonId(ContactPerson contactPersonByContactpersonId) {
        this.contactPersonByContactpersonId = contactPersonByContactpersonId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InterviewContactPerson that = (InterviewContactPerson) o;

        if (interviewId != that.interviewId) return false;
        if (contactpersonId != that.contactpersonId) return false;
        return Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        int result = interviewId;
        result = 31 * result + contactpersonId;
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }
}
