package com.amos2020.javabackend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Audit {
    private int id;
    private String name;
    private Date startDate;
    private Date endDate;
    private Date expectedEndDate;
    private Collection<AuditContactPerson> auditContactPeopleByAuditId;
    private Collection<Interview> interviewsByAuditId;
    private Collection<Scope> scopesByAuditId;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "audit_id")
    public int getId() {
        return id;
    }

    public void setId(int auditId) {
        this.id = auditId;
    }

    @Basic
    @NotBlank
    @Column(name = "audit_name")
    public String getName() {
        return name;
    }

    public void setName(String auditName) {
        this.name = auditName;
    }

    @Basic
    @NotNull
    @Column(name = "audit_start_date")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date auditStartDate) {
        this.startDate = auditStartDate;
    }

    @Basic
    @Column(name = "audit_end_date")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date auditEndDate) {
        this.endDate = auditEndDate;
    }

    @Basic
    @NotNull
    @Column(name = "audit_expected_end_date")
    public Date getExpectedEndDate() {
        return expectedEndDate;
    }

    public void setExpectedEndDate(Date auditExpectedEndDate) {
        this.expectedEndDate = auditExpectedEndDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Audit audit = (Audit) o;

        if (id != audit.id) return false;
        if (!Objects.equals(name, audit.name)) return false;
        if (!Objects.equals(startDate, audit.startDate))
            return false;
        if (!Objects.equals(endDate, audit.endDate)) return false;
        return Objects.equals(expectedEndDate, audit.expectedEndDate);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (expectedEndDate != null ? expectedEndDate.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "auditByAuditcontactpersonAuditId")
    public Collection<AuditContactPerson> getAuditContactPeopleByAuditId() {
        return auditContactPeopleByAuditId;
    }

    public void setAuditContactPeopleByAuditId(Collection<AuditContactPerson> auditContactPeopleByAuditId) {
        this.auditContactPeopleByAuditId = auditContactPeopleByAuditId;
    }

    @OneToMany(mappedBy = "auditByInterviewAuditId")
    public Collection<Interview> getInterviewsByAuditId() {
        return interviewsByAuditId;
    }

    public void setInterviewsByAuditId(Collection<Interview> interviewsByAuditId) {
        this.interviewsByAuditId = interviewsByAuditId;
    }

    @OneToMany(mappedBy = "auditByScopeAuditId")
    public Collection<Scope> getScopesByAuditId() {
        return scopesByAuditId;
    }

    public void setScopesByAuditId(Collection<Scope> scopesByAuditId) {
        this.scopesByAuditId = scopesByAuditId;
    }
}
