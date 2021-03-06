package com.amos2020.javabackend.entity;

import com.amos2020.javabackend.Constants;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "contact_person")
public class ContactPerson {
    private int id;
    private Salutation salutation;
    private String title;
    private String forename;
    private String surname;
    private String contactInformation;
    private String companyName;
    private String department;
    private String sector;
    private String corporateDivision;

    @JsonIgnore
    private Collection<Audit> auditsById;
    @JsonIgnore
    private Collection<AuditContactPerson> auditContactPeopleById;
    @JsonIgnore
    private Collection<InterviewContactPerson> interviewContactPeopleById;

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
    @Enumerated(EnumType.STRING)
    @Column(name = "salutation")
    public Salutation getSalutation() {
        return salutation;
    }

    public void setSalutation(Salutation salutation) {
        this.salutation = salutation;
    }

    @Basic
    @Column(name = "title", length = Constants.NAME_LENGTH)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @NotBlank
    @Column(name = "forename", length = Constants.NAME_LENGTH)
    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    @Basic
    @NotBlank
    @Column(name = "surname", length = Constants.NAME_LENGTH)
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Basic
    @Column(name = "contact_information", length = Constants.NAME_LENGTH)
    public String getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }

    @Basic
    @NotBlank
    @Column(name = "company_name", length = Constants.NAME_LENGTH)
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Basic
    @Column(name = "department", length = Constants.NAME_LENGTH)
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Basic
    @Column(name = "sector", length = Constants.NAME_LENGTH)
    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    @Basic
    @NotBlank
    @Column(name = "corporate_division", length = Constants.NAME_LENGTH)
    public String getCorporateDivision() {
        return corporateDivision;
    }

    public void setCorporateDivision(String corporateDivision) {
        this.corporateDivision = corporateDivision;
    }

    @OneToMany(mappedBy = "contactPersonByCancellationContactPerson", cascade = CascadeType.ALL)
    public Collection<Audit> getAuditsById() {
        return auditsById;
    }

    public void setAuditsById(Collection<Audit> auditsById) {
        this.auditsById = auditsById;
    }

    @OneToMany(mappedBy = "contactPersonByContactPersonId", cascade = CascadeType.ALL)
    public Collection<AuditContactPerson> getAuditContactPeopleById() {
        return auditContactPeopleById;
    }

    public void setAuditContactPeopleById(Collection<AuditContactPerson> auditContactPeopleById) {
        this.auditContactPeopleById = auditContactPeopleById;
    }

    @OneToMany(mappedBy = "contactPersonByContactPersonId", cascade = CascadeType.ALL)
    public Collection<InterviewContactPerson> getInterviewContactPeopleById() {
        return interviewContactPeopleById;
    }

    public void setInterviewContactPeopleById(Collection<InterviewContactPerson> interviewContactPeopleById) {
        this.interviewContactPeopleById = interviewContactPeopleById;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactPerson that = (ContactPerson) o;

        if (id != that.id) return false;
        if (!Objects.equals(salutation, that.salutation)) return false;
        if (!Objects.equals(title, that.title)) return false;
        if (!Objects.equals(forename, that.forename)) return false;
        if (!Objects.equals(surname, that.surname)) return false;
        if (!Objects.equals(contactInformation, that.contactInformation))
            return false;
        if (!Objects.equals(companyName, that.companyName)) return false;
        if (!Objects.equals(department, that.department)) return false;
        if (!Objects.equals(sector, that.sector)) return false;
        return Objects.equals(corporateDivision, that.corporateDivision);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (salutation != null ? salutation.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (forename != null ? forename.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (contactInformation != null ? contactInformation.hashCode() : 0);
        result = 31 * result + (companyName != null ? companyName.hashCode() : 0);
        result = 31 * result + (department != null ? department.hashCode() : 0);
        result = 31 * result + (sector != null ? sector.hashCode() : 0);
        result = 31 * result + (corporateDivision != null ? corporateDivision.hashCode() : 0);
        return result;
    }
}
