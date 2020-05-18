package com.amos2020.javabackend.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class AuditContactPersonPK implements Serializable {
    private int auditcontactpersonAuditId;
    private int auditcontactpersonContactpersonId;

    @Column(name = "auditcontactperson_audit_id")
    @Id
    public int getAuditcontactpersonAuditId() {
        return auditcontactpersonAuditId;
    }

    public void setAuditcontactpersonAuditId(int auditcontactpersonAuditId) {
        this.auditcontactpersonAuditId = auditcontactpersonAuditId;
    }

    @Column(name = "auditcontactperson_contactperson_id")
    @Id
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

        AuditContactPersonPK that = (AuditContactPersonPK) o;

        if (auditcontactpersonAuditId != that.auditcontactpersonAuditId) return false;
        if (auditcontactpersonContactpersonId != that.auditcontactpersonContactpersonId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = auditcontactpersonAuditId;
        result = 31 * result + auditcontactpersonContactpersonId;
        return result;
    }
}
