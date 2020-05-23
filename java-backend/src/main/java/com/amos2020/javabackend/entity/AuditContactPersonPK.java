package com.amos2020.javabackend.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class AuditContactPersonPK implements Serializable {
    private int auditId;
    private int contactpersonId;

    @Column(name = "audit_id")
    @Id
    public int getAuditId() {
        return auditId;
    }

    public void setAuditId(int auditId) {
        this.auditId = auditId;
    }

    @Column(name = "contactperson_id")
    @Id
    public int getContactpersonId() {
        return contactpersonId;
    }

    public void setContactpersonId(int contactpersonId) {
        this.contactpersonId = contactpersonId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuditContactPersonPK that = (AuditContactPersonPK) o;

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
