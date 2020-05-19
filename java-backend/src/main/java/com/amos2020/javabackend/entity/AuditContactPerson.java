package com.amos2020.javabackend.entity;

import javax.persistence.*;

@Entity
@IdClass(AuditContactPersonPK.class)
public class AuditContactPerson {
    private int auditId;
    private int contactPersonId;
    private Audit auditByAuditcontactpersonAuditId;
    private ContactPerson contactPersonByAuditcontactpersonContactpersonId;

    @Id
    @Column(name = "auditcontactperson_audit_id")
    public int getAuditId() {
        return auditId;
    }

    public void setAuditId(int auditcontactpersonAuditId) {
        this.auditId = auditcontactpersonAuditId;
    }

    @Id
    @Column(name = "auditcontactperson_contactperson_id")
    public int getContactPersonId() {
        return contactPersonId;
    }

    public void setContactPersonId(int auditcontactpersonContactpersonId) {
        this.contactPersonId = auditcontactpersonContactpersonId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuditContactPerson that = (AuditContactPerson) o;

        if (auditId != that.auditId) return false;
        return contactPersonId == that.contactPersonId;
    }

    @Override
    public int hashCode() {
        int result = auditId;
        result = 31 * result + contactPersonId;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "auditcontactperson_audit_id", referencedColumnName = "audit_id", nullable = false, insertable = false, updatable = false)
    public Audit getAuditByAuditcontactpersonAuditId() {
        return auditByAuditcontactpersonAuditId;
    }

    public void setAuditByAuditcontactpersonAuditId(Audit auditByAuditcontactpersonAuditId) {
        this.auditByAuditcontactpersonAuditId = auditByAuditcontactpersonAuditId;
    }

    @ManyToOne
    @JoinColumn(name = "auditcontactperson_contactperson_id", referencedColumnName = "contact_person_id", nullable = false, insertable = false, updatable = false)
    public ContactPerson getContactPersonByAuditcontactpersonContactpersonId() {
        return contactPersonByAuditcontactpersonContactpersonId;
    }

    public void setContactPersonByAuditcontactpersonContactpersonId(ContactPerson contactPersonByAuditcontactpersonContactpersonId) {
        this.contactPersonByAuditcontactpersonContactpersonId = contactPersonByAuditcontactpersonContactpersonId;
    }
}
