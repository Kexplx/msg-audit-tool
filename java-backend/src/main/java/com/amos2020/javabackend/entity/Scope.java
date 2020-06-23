package com.amos2020.javabackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@IdClass(ScopePK.class)
public class Scope {
    private int auditId;
    private int faccritId;
    private String changeNote;
    private Boolean removed;
    private String note;
    @JsonIgnore
    private Audit auditByAuditId;
    @JsonIgnore
    private FacCrit facCritByFaccritId;

    @Id
    @NotNull
    @Column(name = "audit_id")
    public int getAuditId() {
        return auditId;
    }

    public void setAuditId(int auditId) {
        this.auditId = auditId;
    }

    @Id
    @NotNull
    @Column(name = "faccrit_id")
    public int getFaccritId() {
        return faccritId;
    }

    public void setFaccritId(int faccritId) {
        this.faccritId = faccritId;
    }

    @Basic
    @Column(name = "change_note", length = 1024)
    public String getChangeNote() {
        return changeNote;
    }

    public void setChangeNote(String changeNote) {
        this.changeNote = changeNote;
    }

    @Basic
    @NotNull
    @Column(name = "removed")
    public Boolean getRemoved() {
        return removed;
    }

    public void setRemoved(Boolean removed) {
        this.removed = removed;
    }

    @Basic
    @Column(name = "note", length = 8096)
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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
    @JoinColumn(name = "faccrit_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public FacCrit getFacCritByFaccritId() {
        return facCritByFaccritId;
    }

    public void setFacCritByFaccritId(FacCrit facCritByFaccritId) {
        this.facCritByFaccritId = facCritByFaccritId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Scope scope = (Scope) o;

        if (auditId != scope.auditId) return false;
        if (faccritId != scope.faccritId) return false;
        if (!Objects.equals(changeNote, scope.changeNote)) return false;
        if (!Objects.equals(removed, scope.removed)) return false;
        return Objects.equals(note, scope.note);
    }

    @Override
    public int hashCode() {
        int result = auditId;
        result = 31 * result + faccritId;
        result = 31 * result + (changeNote != null ? changeNote.hashCode() : 0);
        result = 31 * result + (removed != null ? removed.hashCode() : 0);
        result = 31 * result + (note != null ? note.hashCode() : 0);
        return result;
    }
}
