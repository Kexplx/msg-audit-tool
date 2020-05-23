package com.amos2020.javabackend.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class ScopePK implements Serializable {
    private int auditId;
    private int faccritId;

    @Column(name = "audit_id")
    @Id
    public int getAuditId() {
        return auditId;
    }

    public void setAuditId(int auditId) {
        this.auditId = auditId;
    }

    @Column(name = "faccrit_id")
    @Id
    public int getFaccritId() {
        return faccritId;
    }

    public void setFaccritId(int faccritId) {
        this.faccritId = faccritId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScopePK scopePK = (ScopePK) o;

        if (auditId != scopePK.auditId) return false;
        return faccritId == scopePK.faccritId;
    }

    @Override
    public int hashCode() {
        int result = auditId;
        result = 31 * result + faccritId;
        return result;
    }
}
