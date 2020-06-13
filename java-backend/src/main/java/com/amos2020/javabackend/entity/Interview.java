package com.amos2020.javabackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Interview {
    private int id;
    private int auditId;
    private Date startDate;
    private Date endDate;
    private InterviewStatus status;

    @JsonIgnore
    private Collection<Answer> answersById;
    @JsonIgnore
    private Audit auditByAuditId;
    @JsonIgnore
    private Collection<InterviewContactPerson> interviewContactPeopleById;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @NotNull
    @Column(name = "audit_id")
    public int getAuditId() {
        return auditId;
    }

    public void setAuditId(int auditId) {
        this.auditId = auditId;
    }

    @Basic
    @NotNull
    @Column(name = "start_date")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name = "end_date")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Basic
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    public InterviewStatus getStatus() {
        return status;
    }

    public void setStatus(InterviewStatus status) {
        this.status = status;
    }

    @OneToMany(mappedBy = "interviewByInterviewId")
    public Collection<Answer> getAnswersById() {
        return answersById;
    }

    public void setAnswersById(Collection<Answer> answersById) {
        this.answersById = answersById;
    }

    @ManyToOne
    @JoinColumn(name = "audit_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Audit getAuditByAuditId() {
        return auditByAuditId;
    }

    public void setAuditByAuditId(Audit auditByAuditId) {
        this.auditByAuditId = auditByAuditId;
    }

    @OneToMany(mappedBy = "interviewByInterviewId", cascade = CascadeType.ALL)
    public Collection<InterviewContactPerson> getInterviewContactPeopleById() {
        return interviewContactPeopleById;
    }

    public void setInterviewContactPeopleById(Collection<InterviewContactPerson> interviewContactPeopleById) {
        this.interviewContactPeopleById = interviewContactPeopleById;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Interview interview = (Interview) o;

        if (id != interview.id) return false;
        if (auditId != interview.auditId) return false;
        if (!Objects.equals(startDate, interview.startDate)) return false;
        if (!Objects.equals(endDate, interview.endDate)) return false;
        return Objects.equals(status, interview.status);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + auditId;
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
