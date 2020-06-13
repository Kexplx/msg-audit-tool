package com.amos2020.javabackend.rest_service.request.contactPerson;

import com.amos2020.javabackend.entity.Salutation;
import com.amos2020.javabackend.rest_service.request.BasicRequest;
import lombok.Getter;
import lombok.Setter;

public class CreateContactPersonRequest extends BasicRequest {

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

    @Override
    public void isValid() {
        assertNameIsValid(forename);
        assertNameIsValid(surname);
    }
}
