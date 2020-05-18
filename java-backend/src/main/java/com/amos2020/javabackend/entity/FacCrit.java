package com.amos2020.javabackend.entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class FacCrit {
    private int faccritId;
    private Integer faccritReferenceId;
    private String faccritName;
    private Collection<Answer> answersByFaccritId;
    private FacCrit facCritByFaccritReferenceId;
    private Collection<FacCrit> facCritsByFaccritId;
    private Collection<Scope> scopesByFaccritId;

    @Id
    @Column(name = "faccrit_id")
    public int getFaccritId() {
        return faccritId;
    }

    public void setFaccritId(int faccritId) {
        this.faccritId = faccritId;
    }

    @Basic
    @Column(name = "faccrit_reference_id")
    public Integer getFaccritReferenceId() {
        return faccritReferenceId;
    }

    public void setFaccritReferenceId(Integer faccritReferenceId) {
        this.faccritReferenceId = faccritReferenceId;
    }

    @Basic
    @Column(name = "faccrit_name")
    public String getFaccritName() {
        return faccritName;
    }

    public void setFaccritName(String faccritName) {
        this.faccritName = faccritName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FacCrit facCrit = (FacCrit) o;

        if (faccritId != facCrit.faccritId) return false;
        if (faccritReferenceId != null ? !faccritReferenceId.equals(facCrit.faccritReferenceId) : facCrit.faccritReferenceId != null)
            return false;
        if (faccritName != null ? !faccritName.equals(facCrit.faccritName) : facCrit.faccritName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = faccritId;
        result = 31 * result + (faccritReferenceId != null ? faccritReferenceId.hashCode() : 0);
        result = 31 * result + (faccritName != null ? faccritName.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "facCritByAnswerFaccritId")
    public Collection<Answer> getAnswersByFaccritId() {
        return answersByFaccritId;
    }

    public void setAnswersByFaccritId(Collection<Answer> answersByFaccritId) {
        this.answersByFaccritId = answersByFaccritId;
    }

    @ManyToOne
    @JoinColumn(name = "faccrit_reference_id", referencedColumnName = "faccrit_id")
    public FacCrit getFacCritByFaccritReferenceId() {
        return facCritByFaccritReferenceId;
    }

    public void setFacCritByFaccritReferenceId(FacCrit facCritByFaccritReferenceId) {
        this.facCritByFaccritReferenceId = facCritByFaccritReferenceId;
    }

    @OneToMany(mappedBy = "facCritByFaccritReferenceId")
    public Collection<FacCrit> getFacCritsByFaccritId() {
        return facCritsByFaccritId;
    }

    public void setFacCritsByFaccritId(Collection<FacCrit> facCritsByFaccritId) {
        this.facCritsByFaccritId = facCritsByFaccritId;
    }

    @OneToMany(mappedBy = "facCritByScopeFaccritId")
    public Collection<Scope> getScopesByFaccritId() {
        return scopesByFaccritId;
    }

    public void setScopesByFaccritId(Collection<Scope> scopesByFaccritId) {
        this.scopesByFaccritId = scopesByFaccritId;
    }
}
