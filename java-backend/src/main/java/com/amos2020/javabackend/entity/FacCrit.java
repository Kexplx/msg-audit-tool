package com.amos2020.javabackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "fac_crit")
public class FacCrit {
    private int id;
    private Integer referenceId;
    private String name;
    private String goal;

    @JsonIgnore
    private FacCrit facCritByReferenceId;
    @JsonIgnore
    private Collection<FacCrit> facCritsById;
    @JsonIgnore
    private Collection<Question> questionsById;
    @JsonIgnore
    private Collection<Scope> scopesById;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "reference_id")
    public Integer getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Integer referenceId) {
        this.referenceId = referenceId;
    }

    @Basic
    @NotBlank
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Basic
    @Column(name = "goal", length = 256)
    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    @ManyToOne
    @JoinColumn(name = "reference_id", referencedColumnName = "id", insertable = false, updatable = false)
    public FacCrit getFacCritByReferenceId() {
        return facCritByReferenceId;
    }

    public void setFacCritByReferenceId(FacCrit facCritByReferenceId) {
        this.facCritByReferenceId = facCritByReferenceId;
    }

    @OneToMany(mappedBy = "facCritByReferenceId")
    public Collection<FacCrit> getFacCritsById() {
        return facCritsById;
    }

    public void setFacCritsById(Collection<FacCrit> facCritsById) {
        this.facCritsById = facCritsById;
    }

    @OneToMany(mappedBy = "facCritByFaccritId")
    public Collection<Question> getQuestionsById() {
        return questionsById;
    }

    public void setQuestionsById(Collection<Question> questionsById) {
        this.questionsById = questionsById;
    }

    @OneToMany(mappedBy = "facCritByFaccritId")
    public Collection<Scope> getScopesById() {
        return scopesById;
    }

    public void setScopesById(Collection<Scope> scopesById) {
        this.scopesById = scopesById;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FacCrit facCrit = (FacCrit) o;

        if (id != facCrit.id) return false;
        if (!Objects.equals(referenceId, facCrit.referenceId)) return false;
        return Objects.equals(name, facCrit.name);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (referenceId != null ? referenceId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
