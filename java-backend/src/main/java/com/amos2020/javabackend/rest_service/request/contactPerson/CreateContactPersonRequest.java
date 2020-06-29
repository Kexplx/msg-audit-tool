package com.amos2020.javabackend.rest_service.request.contactPerson;

import com.amos2020.javabackend.entity.Salutation;
import com.amos2020.javabackend.rest_service.Constants;
import com.amos2020.javabackend.rest_service.request.BasicRequest;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateContactPersonRequest extends BasicRequest {

    @Getter
    @Setter
    private Salutation salutation;
    @Getter
    @Setter
    private String title;
    @Getter
    @Setter
    @NotNull
    @NotBlank
    @Size(min = 1, max = Constants.NAME_LENGTH)
    private String forename;
    @Getter
    @Setter
    @NotNull
    @NotBlank
    @Size(min = 1, max = Constants.NAME_LENGTH)
    private String surname;
    @Getter
    @Setter
    @Size(max = Constants.NAME_LENGTH)
    private String contactInformation;
    @Getter
    @Setter
    @NotNull
    @NotBlank
    @Size(min = 1, max = Constants.NAME_LENGTH)
    private String companyName;
    @Getter
    @Setter
    @Size(max = Constants.NAME_LENGTH)
    private String department;
    @Getter
    @Setter
    @Size(max = Constants.NAME_LENGTH)
    private String sector;
    @Getter
    @Setter
    @NotNull
    @NotBlank
    @Size(min = 1, max = Constants.NAME_LENGTH)
    private String corporateDivision;

    public void isValid() {
        assertNameIsValid(forename);
        assertNameIsValid(surname);
    }
}
