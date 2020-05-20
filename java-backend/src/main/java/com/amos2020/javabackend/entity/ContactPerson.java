package com.amos2020.javabackend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Objects;

@Entity
public class ContactPerson {
    private int id;
    private String title;
    private String forename;
    private String surname;
    private String contactInformation;
    private String companyName;
    private String department;
    private String sector;
    private Collection<AuditContactPerson> auditContactPeopleByContactPersonId;
    private Collection<InterviewContactPerson> interviewContactPeopleByContactPersonId;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "contact_person_id")
    public int getId() {
        return id;
    }

    public void setId(int contactPersonId) {
        this.id = contactPersonId;
    }

    @Basic
    @NotBlank
    @Column(name = "contact_person_title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String contactPersonTitle) {
        this.title = contactPersonTitle;
    }

    @Basic
    @NotBlank
    @Column(name = "contact_person_forename")
    public String getForename() {
        return forename;
    }

    public void setForename(String contactPersonForename) {
        this.forename = contactPersonForename;
    }

    @Basic
    @NotBlank
    @Column(name = "contact_person_surname")
    public String getSurname() {
        return surname;
    }

    public void setSurname(String contactPersonSurname) {
        this.surname = contactPersonSurname;
    }

    @Basic
    @Column(name = "contact_person_contact_information")
    public String getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(String contactPersonContactInformation) {
        this.contactInformation = contactPersonContactInformation;
    }

    @Basic
    @NotBlank
    @Column(name = "contact_person_company_name")
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String contactPersonCompanyName) {
        this.companyName = contactPersonCompanyName;
    }

    @Basic
    @NotBlank
    @Column(name = "contact_person_department")
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String contactPersonDepartment) {
        this.department = contactPersonDepartment;
    }

    @Basic
    @NotBlank
    @Column(name = "contact_person_sector")
    public String getSector() {
        return sector;
    }

    public void setSector(String contactPersonSector) {
        this.sector = contactPersonSector;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactPerson that = (ContactPerson) o;

        if (id != that.id) return false;
        if (!Objects.equals(title, that.title))
            return false;
        if (!Objects.equals(forename, that.forename))
            return false;
        if (!Objects.equals(surname, that.surname))
            return false;
        if (!Objects.equals(contactInformation, that.contactInformation))
            return false;
        if (!Objects.equals(companyName, that.companyName))
            return false;
        if (!Objects.equals(department, that.department))
            return false;
        return Objects.equals(sector, that.sector);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (forename != null ? forename.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (contactInformation != null ? contactInformation.hashCode() : 0);
        result = 31 * result + (companyName != null ? companyName.hashCode() : 0);
        result = 31 * result + (department != null ? department.hashCode() : 0);
        result = 31 * result + (sector != null ? sector.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "contactPersonByAuditcontactpersonContactpersonId")
    public Collection<AuditContactPerson> getAuditContactPeopleByContactPersonId() {
        return auditContactPeopleByContactPersonId;
    }

    public void setAuditContactPeopleByContactPersonId(Collection<AuditContactPerson> auditContactPeopleByContactPersonId) {
        this.auditContactPeopleByContactPersonId = auditContactPeopleByContactPersonId;
    }

    @OneToMany(mappedBy = "contactPersonByInterviewcontactpersonContactpersonId")
    public Collection<InterviewContactPerson> getInterviewContactPeopleByContactPersonId() {
        return interviewContactPeopleByContactPersonId;
    }

    public void setInterviewContactPeopleByContactPersonId(Collection<InterviewContactPerson> interviewContactPeopleByContactPersonId) {
        this.interviewContactPeopleByContactPersonId = interviewContactPeopleByContactPersonId;
    }
}
