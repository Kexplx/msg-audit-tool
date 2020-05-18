package com.amos2020.javabackend.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Interview {
    private int interviewId;
    private int interviewAuditId;
    private Date interviewDate;
    private String interviewAnnotation;
    private Collection<Answer> answersByInterviewId;
    private Audit auditByInterviewAuditId;
    private Collection<InterviewContactPerson> interviewContactPeopleByInterviewId;

    @Id
    @Column(name = "interview_id")
    public int getInterviewId() {
        return interviewId;
    }

    public void setInterviewId(int interviewId) {
        this.interviewId = interviewId;
    }

    @Basic
    @Column(name = "interview_audit_id")
    public int getInterviewAuditId() {
        return interviewAuditId;
    }

    public void setInterviewAuditId(int interviewAuditId) {
        this.interviewAuditId = interviewAuditId;
    }

    @Basic
    @Column(name = "interview_date")
    public Date getInterviewDate() {
        return interviewDate;
    }

    public void setInterviewDate(Date interviewDate) {
        this.interviewDate = interviewDate;
    }

    @Basic
    @Column(name = "interview_annotation")
    public String getInterviewAnnotation() {
        return interviewAnnotation;
    }

    public void setInterviewAnnotation(String interviewAnnotation) {
        this.interviewAnnotation = interviewAnnotation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Interview interview = (Interview) o;

        if (interviewId != interview.interviewId) return false;
        if (interviewAuditId != interview.interviewAuditId) return false;
        if (!Objects.equals(interviewDate, interview.interviewDate))
            return false;
        return Objects.equals(interviewAnnotation, interview.interviewAnnotation);
    }

    @Override
    public int hashCode() {
        int result = interviewId;
        result = 31 * result + interviewAuditId;
        result = 31 * result + (interviewDate != null ? interviewDate.hashCode() : 0);
        result = 31 * result + (interviewAnnotation != null ? interviewAnnotation.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "interviewByAnswerInterviewId")
    public Collection<Answer> getAnswersByInterviewId() {
        return answersByInterviewId;
    }

    public void setAnswersByInterviewId(Collection<Answer> answersByInterviewId) {
        this.answersByInterviewId = answersByInterviewId;
    }

    @ManyToOne
    @JoinColumn(name = "interview_audit_id", referencedColumnName = "audit_id", nullable = false)
    public Audit getAuditByInterviewAuditId() {
        return auditByInterviewAuditId;
    }

    public void setAuditByInterviewAuditId(Audit auditByInterviewAuditId) {
        this.auditByInterviewAuditId = auditByInterviewAuditId;
    }

    @OneToMany(mappedBy = "interviewByInterviewcontactpersonInterviewId")
    public Collection<InterviewContactPerson> getInterviewContactPeopleByInterviewId() {
        return interviewContactPeopleByInterviewId;
    }

    public void setInterviewContactPeopleByInterviewId(Collection<InterviewContactPerson> interviewContactPeopleByInterviewId) {
        this.interviewContactPeopleByInterviewId = interviewContactPeopleByInterviewId;
    }
}
