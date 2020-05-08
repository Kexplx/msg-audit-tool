package com.amos2020.javabackend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "criteria", schema = "msg_audit_database")
public class CriteriaEntity {
    private int id;
    private String name;
    private FactorEntity factorByFactorId;
    private Collection<QuestionEntity> questionsById;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @NotNull
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CriteriaEntity that = (CriteriaEntity) o;

        if (id != that.id) return false;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @ManyToOne( cascade = CascadeType.ALL)
    @JoinColumn(name = "factor_id", referencedColumnName = "id", nullable = false)
    public FactorEntity getFactorByFactorId() {
        return factorByFactorId;
    }

    public void setFactorByFactorId(FactorEntity factorByFactorId) {
        this.factorByFactorId = factorByFactorId;
    }

    @OneToMany(mappedBy = "criteriaByCriteriaId", cascade = CascadeType.ALL)
    public Collection<QuestionEntity> getQuestionsById() {
        return questionsById;
    }

    public void setQuestionsById(Collection<QuestionEntity> questionsById) {
        this.questionsById = questionsById;
    }
}
