package com.amos2020.javabackend.rest_service.response;

import com.amos2020.javabackend.entity.ContactPerson;
import com.amos2020.javabackend.entity.Salutation;
import lombok.Getter;
import lombok.Setter;

public class BasicContactPersonResponse {
    @Getter
    @Setter
    private int id;
    @Getter
    @Setter
    private Salutation salutation;
    @Getter
    @Setter
    private String title;
    @Getter
    @Setter
    private String forename;
    @Getter
    @Setter
    private String surname;
    @Getter
    @Setter
    private String contactInformation;
    @Getter
    @Setter
    private String companyName;
    @Getter
    @Setter
    private String department;
    @Getter
    @Setter
    private String sector;
    @Getter
    @Setter
    private String corporateDivision;

    public BasicContactPersonResponse(ContactPerson contactperson) {
        this.id = contactperson.getId();
        this.salutation = contactperson.getSalutation();
        this.title = contactperson.getTitle();
        this.forename = contactperson.getForename();
        this.surname = contactperson.getSurname();
        this.companyName = contactperson.getCompanyName();
        this.department = contactperson.getDepartment();
        this.sector = contactperson.getSector();
        this.corporateDivision = contactperson.getCorporateDivision();
    }

    public BasicContactPersonResponse() {
    }
}
