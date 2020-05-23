package com.amos2020.javabackend.entity;

import javax.persistence.*;

@Entity
@IdClass(AuditContactPersonPK.class)
public class AuditContactPerson {
    private int auditId;
    private int contactpersonId;
    private Audit auditByAuditId;
    private ContactPerson contactPersonByContactpersonId;

    @Id
    @Column(name = "audit_id")
    public int getAuditId() {
        return auditId;
    }

    public void setAuditId(int auditId) {
        this.auditId = auditId;
    }

    @Id
    @Column(name = "contactperson_id")
    public int getContactpersonId() {
        return contactpersonId;
    }

    public void setContactpersonId(int contactpersonId) {
        this.contactpersonId = contactpersonId;
    }

    @ManyToOne
    @JoinColumn(name = "audit_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Audit getAuditByAuditId() {
        return auditByAuditId;
    }

    public void setAuditByAuditId(Audit auditByAuditId) {
        this.auditByAuditId = auditByAuditId;
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

        AuditContactPerson that = (AuditContactPerson) o;

        if (auditId != that.auditId) return false;
        return contactpersonId == that.contactpersonId;
    }

    @Override
    public int hashCode() {
        int result = auditId;
        result = 31 * result + contactpersonId;
        return result;
    }
}
