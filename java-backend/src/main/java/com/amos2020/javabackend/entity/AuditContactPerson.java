package com.amos2020.javabackend.entity;

import javax.persistence.*;

@Entity
@IdClass(AuditContactPersonPK.class)
public class AuditContactPerson {
    private int auditcontactpersonAuditId;
    private int auditcontactpersonContactpersonId;
    private Audit auditByAuditcontactpersonAuditId;
    private ContactPerson contactPersonByAuditcontactpersonContactpersonId;

    @Id
    @Column(name = "auditcontactperson_audit_id")
    public int getAuditcontactpersonAuditId() {
        return auditcontactpersonAuditId;
    }

    public void setAuditcontactpersonAuditId(int auditcontactpersonAuditId) {
        this.auditcontactpersonAuditId = auditcontactpersonAuditId;
    }

    @Id
    @Column(name = "auditcontactperson_contactperson_id")
    public int getAuditcontactpersonContactpersonId() {
        return auditcontactpersonContactpersonId;
    }

    public void setAuditcontactpersonContactpersonId(int auditcontactpersonContactpersonId) {
        this.auditcontactpersonContactpersonId = auditcontactpersonContactpersonId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuditContactPerson that = (AuditContactPerson) o;

        if (auditcontactpersonAuditId != that.auditcontactpersonAuditId) return false;
        return auditcontactpersonContactpersonId == that.auditcontactpersonContactpersonId;
    }

    @Override
    public int hashCode() {
        int result = auditcontactpersonAuditId;
        result = 31 * result + auditcontactpersonContactpersonId;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "auditcontactperson_audit_id", referencedColumnName = "audit_id", nullable = false)
    public Audit getAuditByAuditcontactpersonAuditId() {
        return auditByAuditcontactpersonAuditId;
    }

    public void setAuditByAuditcontactpersonAuditId(Audit auditByAuditcontactpersonAuditId) {
        this.auditByAuditcontactpersonAuditId = auditByAuditcontactpersonAuditId;
    }

    @ManyToOne
    @JoinColumn(name = "auditcontactperson_contactperson_id", referencedColumnName = "contact_person_id", nullable = false)
    public ContactPerson getContactPersonByAuditcontactpersonContactpersonId() {
        return contactPersonByAuditcontactpersonContactpersonId;
    }

    public void setContactPersonByAuditcontactpersonContactpersonId(ContactPerson contactPersonByAuditcontactpersonContactpersonId) {
        this.contactPersonByAuditcontactpersonContactpersonId = contactPersonByAuditcontactpersonContactpersonId;
    }
}
