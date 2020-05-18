package com.amos2020.javabackend.entity;

import javax.persistence.*;

@Entity
@IdClass(ScopePK.class)
public class Scope {
    private int scopeAuditId;
    private int scopeFaccritId;
    private String scopeChangeNote;
    private Boolean scopeRemoved;
    private Audit auditByScopeAuditId;
    private FacCrit facCritByScopeFaccritId;

    @Id
    @Column(name = "scope_audit_id")
    public int getScopeAuditId() {
        return scopeAuditId;
    }

    public void setScopeAuditId(int scopeAuditId) {
        this.scopeAuditId = scopeAuditId;
    }

    @Id
    @Column(name = "scope_faccrit_id")
    public int getScopeFaccritId() {
        return scopeFaccritId;
    }

    public void setScopeFaccritId(int scopeFaccritId) {
        this.scopeFaccritId = scopeFaccritId;
    }

    @Basic
    @Column(name = "scope_change_note")
    public String getScopeChangeNote() {
        return scopeChangeNote;
    }

    public void setScopeChangeNote(String scopeChangeNote) {
        this.scopeChangeNote = scopeChangeNote;
    }

    @Basic
    @Column(name = "scope_removed")
    public Boolean getScopeRemoved() {
        return scopeRemoved;
    }

    public void setScopeRemoved(Boolean scopeRemoved) {
        this.scopeRemoved = scopeRemoved;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Scope scope = (Scope) o;

        if (scopeAuditId != scope.scopeAuditId) return false;
        if (scopeFaccritId != scope.scopeFaccritId) return false;
        if (scopeChangeNote != null ? !scopeChangeNote.equals(scope.scopeChangeNote) : scope.scopeChangeNote != null)
            return false;
        if (scopeRemoved != null ? !scopeRemoved.equals(scope.scopeRemoved) : scope.scopeRemoved != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = scopeAuditId;
        result = 31 * result + scopeFaccritId;
        result = 31 * result + (scopeChangeNote != null ? scopeChangeNote.hashCode() : 0);
        result = 31 * result + (scopeRemoved != null ? scopeRemoved.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "scope_audit_id", referencedColumnName = "audit_id", nullable = false)
    public Audit getAuditByScopeAuditId() {
        return auditByScopeAuditId;
    }

    public void setAuditByScopeAuditId(Audit auditByScopeAuditId) {
        this.auditByScopeAuditId = auditByScopeAuditId;
    }

    @ManyToOne
    @JoinColumn(name = "scope_faccrit_id", referencedColumnName = "faccrit_id", nullable = false)
    public FacCrit getFacCritByScopeFaccritId() {
        return facCritByScopeFaccritId;
    }

    public void setFacCritByScopeFaccritId(FacCrit facCritByScopeFaccritId) {
        this.facCritByScopeFaccritId = facCritByScopeFaccritId;
    }
}
