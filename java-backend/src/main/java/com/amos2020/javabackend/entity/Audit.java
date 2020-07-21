package com.amos2020.javabackend.entity;

import com.amos2020.javabackend.Constants;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Audit {

    private int id;
    private String name;
    private Timestamp creationDate;
    private Date startDate;
    private Date endDate;
    private Date cancellationDate;
    private String cancellationReason;
    private Integer cancellationContactPerson;
    private AuditStatus status;

    private ContactPerson contactPersonByCancellationContactPerson;
    private Collection<AuditContactPerson> auditContactPeopleById;
    private Collection<Interview> interviewsById;
    private Collection<Scope> scopesById;

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
    @NotBlank
    @Column(name = "name", length = Constants.NAME_LENGTH)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    @Column(name = "cancellation_date")
    public Date getCancellationDate() {
        return cancellationDate;
    }

    public void setCancellationDate(Date cancellationDate) {
        this.cancellationDate = cancellationDate;
    }

    @Basic
    @Column(name = "cancellation_reason", length = Constants.NAME_LENGTH)
    public String getCancellationReason() {
        return cancellationReason;
    }

    public void setCancellationReason(String cancellationReason) {
        this.cancellationReason = cancellationReason;
    }

    @Basic
    @Column(name = "cancellation_contact_person")
    public Integer getCancellationContactPerson() {
        return cancellationContactPerson;
    }

    public void setCancellationContactPerson(Integer cancellationContactPerson) {
        this.cancellationContactPerson = cancellationContactPerson;
    }

    @Basic
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    public AuditStatus getStatus() {
        return status;
    }

    public void setStatus(AuditStatus status) {
        this.status = status;
    }

    @Basic
    @NotNull
    @Column(name = "creation_date")
    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    @ManyToOne
    @JoinColumn(name = "cancellation_contact_person", referencedColumnName = "id", insertable = false, updatable = false)
    public ContactPerson getContactPersonByCancellationContactPerson() {
        return contactPersonByCancellationContactPerson;
    }

    public void setContactPersonByCancellationContactPerson(ContactPerson contactPersonByCancellationContactPerson) {
        this.contactPersonByCancellationContactPerson = contactPersonByCancellationContactPerson;
    }

    @OneToMany(mappedBy = "auditByAuditId", cascade = CascadeType.ALL)
    public Collection<AuditContactPerson> getAuditContactPeopleById() {
        return auditContactPeopleById;
    }

    public void setAuditContactPeopleById(Collection<AuditContactPerson> auditContactPeopleById) {
        this.auditContactPeopleById = auditContactPeopleById;
    }

    @OneToMany(mappedBy = "auditByAuditId", cascade = CascadeType.ALL)
    public Collection<Interview> getInterviewsById() {
        return interviewsById;
    }

    public void setInterviewsById(Collection<Interview> interviewsById) {
        this.interviewsById = interviewsById;
    }

    @OneToMany(mappedBy = "auditByAuditId", cascade = CascadeType.ALL)
    public Collection<Scope> getScopesById() {
        return scopesById;
    }

    public void setScopesById(Collection<Scope> scopesById) {
        this.scopesById = scopesById;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Audit audit = (Audit) o;

        if (id != audit.id) return false;
        if (!Objects.equals(name, audit.name)) return false;
        if (!Objects.equals(startDate, audit.startDate)) return false;
        if (!Objects.equals(endDate, audit.endDate)) return false;
        if (!Objects.equals(cancellationDate, audit.cancellationDate))
            return false;
        if (!Objects.equals(cancellationReason, audit.cancellationReason))
            return false;
        if (!Objects.equals(cancellationContactPerson, audit.cancellationContactPerson))
            return false;
        if (!Objects.equals(creationDate, audit.creationDate)) return false;
        return Objects.equals(status, audit.status);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (cancellationDate != null ? cancellationDate.hashCode() : 0);
        result = 31 * result + (cancellationReason != null ? cancellationReason.hashCode() : 0);
        result = 31 * result + (cancellationContactPerson != null ? cancellationContactPerson.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        return result;
    }
}
