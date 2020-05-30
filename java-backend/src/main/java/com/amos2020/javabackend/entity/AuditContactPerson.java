package com.amos2020.javabackend.entity;

import javax.persistence.*;

@Entity
@IdClass(AuditContactPersonPK.class)
@Table(name = "audit_contact_person")
public class AuditContactPerson {
    private int auditId;
    private int contactPersonId;
    private Audit auditByAuditId;
    private ContactPerson contactPersonByContactPersonId;

    @Id
    @Column(name = "audit_id")
    public int getAuditId() {
        return auditId;
    }

    public void setAuditId(int auditId) {
        this.auditId = auditId;
    }

    @Id
    @Column(name = "contact_person_id")
    public int getContactPersonId() {
        return contactPersonId;
    }

    public void setContactPersonId(int contactPersonId) {
        this.contactPersonId = contactPersonId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        com.amos2020.javabackend.entity.AuditContactPerson that = (com.amos2020.javabackend.entity.AuditContactPerson) o;

        if (auditId != that.auditId) return false;
        return contactPersonId == that.contactPersonId;
    }

    @Override
    public int hashCode() {
        int result = auditId;
        result = 31 * result + contactPersonId;
        return result;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "audit_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Audit getAuditByAuditId() {
        return auditByAuditId;
    }

    public void setAuditByAuditId(Audit auditByAuditId) {
        this.auditByAuditId = auditByAuditId;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_person_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public ContactPerson getContactPersonByContactPersonId() {
        return contactPersonByContactPersonId;
    }

    public void setContactPersonByContactPersonId(ContactPerson contactPersonByContactPersonId) {
        this.contactPersonByContactPersonId = contactPersonByContactPersonId;
    }
}
