package com.amos2020.javabackend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
@Table(name = "customer", schema = "msg_audit_database")
public class CustomerEntity {
    private int id;
    private String companyName;
    private String sector;
    private String department;
    private ContactPersonEntity contactPersonByContactPersonId;

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
    @Column(name = "company_name")
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Basic
    @NotBlank
    @Column(name = "sector")
    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    @Basic
    @NotBlank
    @Column(name = "department")
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomerEntity that = (CustomerEntity) o;

        if (id != that.id) return false;
        if (!Objects.equals(companyName, that.companyName)) return false;
        if (!Objects.equals(sector, that.sector)) return false;
        return Objects.equals(department, that.department);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (companyName != null ? companyName.hashCode() : 0);
        result = 31 * result + (sector != null ? sector.hashCode() : 0);
        result = 31 * result + (department != null ? department.hashCode() : 0);
        return result;
    }

    @ManyToOne( cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_person_id", referencedColumnName = "id", nullable = false)
    public ContactPersonEntity getContactPersonByContactPersonId() {
        return contactPersonByContactPersonId;
    }

    public void setContactPersonByContactPersonId(ContactPersonEntity contactPersonByContactPersonId) {
        this.contactPersonByContactPersonId = contactPersonByContactPersonId;
    }
}
