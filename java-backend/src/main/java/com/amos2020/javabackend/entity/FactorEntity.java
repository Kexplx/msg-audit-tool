package com.amos2020.javabackend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "factor", schema = "msg_audit_database")
public class FactorEntity {
    private int id;
    private String name;
    private String isoName;
    private Collection<CriteriaEntity> criteriaById;

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
    @NotBlank
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @NotBlank
    @Column(name = "iso_name")
    public String getIsoName() {
        return isoName;
    }

    public void setIsoName(String isoName) {
        this.isoName = isoName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FactorEntity that = (FactorEntity) o;

        if (id != that.id) return false;
        if (!Objects.equals(name, that.name)) return false;
        return Objects.equals(isoName, that.isoName);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (isoName != null ? isoName.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "factorByFactorId", cascade = CascadeType.ALL)
    public Collection<CriteriaEntity> getCriteriaById() {
        return criteriaById;
    }

    public void setCriteriaById(Collection<CriteriaEntity> criteriaById) {
        this.criteriaById = criteriaById;
    }
}
