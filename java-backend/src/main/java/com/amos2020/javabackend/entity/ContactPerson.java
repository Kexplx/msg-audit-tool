package com.amos2020.javabackend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Objects;

@Entity
public class ContactPerson {
    private int contactPersonId;
    private String contactPersonTitle;
    private String contactPersonForename;
    private String contactPersonSurname;
    private String contactPersonContactInformation;
    private String contactPersonCompanyName;
    private String contactPersonDepartment;
    private String contactPersonSector;
    private Collection<AuditContactPerson> auditContactPeopleByContactPersonId;
    private Collection<InterviewContactPerson> interviewContactPeopleByContactPersonId;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "contact_person_id")
    public int getContactPersonId() {
        return contactPersonId;
    }

    public void setContactPersonId(int contactPersonId) {
        this.contactPersonId = contactPersonId;
    }

    @Basic
    @NotBlank
    @Column(name = "contact_person_title")
    public String getContactPersonTitle() {
        return contactPersonTitle;
    }

    public void setContactPersonTitle(String contactPersonTitle) {
        this.contactPersonTitle = contactPersonTitle;
    }

    @Basic
    @NotBlank
    @Column(name = "contact_person_forename")
    public String getContactPersonForename() {
        return contactPersonForename;
    }

    public void setContactPersonForename(String contactPersonForename) {
        this.contactPersonForename = contactPersonForename;
    }

    @Basic
    @NotBlank
    @Column(name = "contact_person_surname")
    public String getContactPersonSurname() {
        return contactPersonSurname;
    }

    public void setContactPersonSurname(String contactPersonSurname) {
        this.contactPersonSurname = contactPersonSurname;
    }

    @Basic
    @Column(name = "contact_person_contact_information")
    public String getContactPersonContactInformation() {
        return contactPersonContactInformation;
    }

    public void setContactPersonContactInformation(String contactPersonContactInformation) {
        this.contactPersonContactInformation = contactPersonContactInformation;
    }

    @Basic
    @NotBlank
    @Column(name = "contact_person_company_name")
    public String getContactPersonCompanyName() {
        return contactPersonCompanyName;
    }

    public void setContactPersonCompanyName(String contactPersonCompanyName) {
        this.contactPersonCompanyName = contactPersonCompanyName;
    }

    @Basic
    @NotBlank
    @Column(name = "contact_person_department")
    public String getContactPersonDepartment() {
        return contactPersonDepartment;
    }

    public void setContactPersonDepartment(String contactPersonDepartment) {
        this.contactPersonDepartment = contactPersonDepartment;
    }

    @Basic
    @NotBlank
    @Column(name = "contact_person_sector")
    public String getContactPersonSector() {
        return contactPersonSector;
    }

    public void setContactPersonSector(String contactPersonSector) {
        this.contactPersonSector = contactPersonSector;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactPerson that = (ContactPerson) o;

        if (contactPersonId != that.contactPersonId) return false;
        if (!Objects.equals(contactPersonTitle, that.contactPersonTitle))
            return false;
        if (!Objects.equals(contactPersonForename, that.contactPersonForename))
            return false;
        if (!Objects.equals(contactPersonSurname, that.contactPersonSurname))
            return false;
        if (!Objects.equals(contactPersonContactInformation, that.contactPersonContactInformation))
            return false;
        if (!Objects.equals(contactPersonCompanyName, that.contactPersonCompanyName))
            return false;
        if (!Objects.equals(contactPersonDepartment, that.contactPersonDepartment))
            return false;
        return Objects.equals(contactPersonSector, that.contactPersonSector);
    }

    @Override
    public int hashCode() {
        int result = contactPersonId;
        result = 31 * result + (contactPersonTitle != null ? contactPersonTitle.hashCode() : 0);
        result = 31 * result + (contactPersonForename != null ? contactPersonForename.hashCode() : 0);
        result = 31 * result + (contactPersonSurname != null ? contactPersonSurname.hashCode() : 0);
        result = 31 * result + (contactPersonContactInformation != null ? contactPersonContactInformation.hashCode() : 0);
        result = 31 * result + (contactPersonCompanyName != null ? contactPersonCompanyName.hashCode() : 0);
        result = 31 * result + (contactPersonDepartment != null ? contactPersonDepartment.hashCode() : 0);
        result = 31 * result + (contactPersonSector != null ? contactPersonSector.hashCode() : 0);
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
