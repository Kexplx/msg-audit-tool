package com.amos2020.javabackend.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class ScopePK implements Serializable {
    private int scopeAuditId;
    private int scopeFaccritId;

    @Column(name = "scope_audit_id")
    @Id
    public int getScopeAuditId() {
        return scopeAuditId;
    }

    public void setScopeAuditId(int scopeAuditId) {
        this.scopeAuditId = scopeAuditId;
    }

    @Column(name = "scope_faccrit_id")
    @Id
    public int getScopeFaccritId() {
        return scopeFaccritId;
    }

    public void setScopeFaccritId(int scopeFaccritId) {
        this.scopeFaccritId = scopeFaccritId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScopePK scopePK = (ScopePK) o;

        if (scopeAuditId != scopePK.scopeAuditId) return false;
        if (scopeFaccritId != scopePK.scopeFaccritId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = scopeAuditId;
        result = 31 * result + scopeFaccritId;
        return result;
    }
}
