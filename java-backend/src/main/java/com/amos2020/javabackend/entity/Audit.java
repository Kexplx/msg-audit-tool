package com.amos2020.javabackend.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Audit {
    private int auditId;
    private String auditName;
    private Date auditStartDate;
    private Date auditEndDate;
    private Date auditExpectedEndDate;
    private Collection<AuditContactPerson> auditContactPeopleByAuditId;
    private Collection<Interview> interviewsByAuditId;
    private Collection<Scope> scopesByAuditId;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "audit_id")
    public int getAuditId() {
        return auditId;
    }

    public void setAuditId(int auditId) {
        this.auditId = auditId;
    }

    @Basic
    @Column(name = "audit_name")
    public String getAuditName() {
        return auditName;
    }

    public void setAuditName(String auditName) {
        this.auditName = auditName;
    }

    @Basic
    @Column(name = "audit_start_date")
    public Date getAuditStartDate() {
        return auditStartDate;
    }

    public void setAuditStartDate(Date auditStartDate) {
        this.auditStartDate = auditStartDate;
    }

    @Basic
    @Column(name = "audit_end_date")
    public Date getAuditEndDate() {
        return auditEndDate;
    }

    public void setAuditEndDate(Date auditEndDate) {
        this.auditEndDate = auditEndDate;
    }

    @Basic
    @Column(name = "audit_expected_end_date")
    public Date getAuditExpectedEndDate() {
        return auditExpectedEndDate;
    }

    public void setAuditExpectedEndDate(Date auditExpectedEndDate) {
        this.auditExpectedEndDate = auditExpectedEndDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Audit audit = (Audit) o;

        if (auditId != audit.auditId) return false;
        if (!Objects.equals(auditName, audit.auditName)) return false;
        if (!Objects.equals(auditStartDate, audit.auditStartDate))
            return false;
        if (!Objects.equals(auditEndDate, audit.auditEndDate)) return false;
        return Objects.equals(auditExpectedEndDate, audit.auditExpectedEndDate);
    }

    @Override
    public int hashCode() {
        int result = auditId;
        result = 31 * result + (auditName != null ? auditName.hashCode() : 0);
        result = 31 * result + (auditStartDate != null ? auditStartDate.hashCode() : 0);
        result = 31 * result + (auditEndDate != null ? auditEndDate.hashCode() : 0);
        result = 31 * result + (auditExpectedEndDate != null ? auditExpectedEndDate.hashCode() : 0);
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
