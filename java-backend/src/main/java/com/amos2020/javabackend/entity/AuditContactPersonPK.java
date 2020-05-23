package com.amos2020.javabackend.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class AuditContactPersonPK implements Serializable {
    private int auditId;
    private int contactPersonId;

    @Column(name = "audit_id")
    @Id
    public int getAuditId() {
        return auditId;
    }

    public void setAuditId(int auditId) {
        this.auditId = auditId;
    }

    @Column(name = "contact_person_id")
    @Id
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

        AuditContactPersonPK that = (AuditContactPersonPK) o;

        if (auditId != that.auditId) return false;
        return contactPersonId == that.contactPersonId;
    }

    @Override
    public int hashCode() {
        int result = auditId;
        result = 31 * result + contactPersonId;
        return result;
    }
}
