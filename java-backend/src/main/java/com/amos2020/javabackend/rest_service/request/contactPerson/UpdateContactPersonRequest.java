package com.amos2020.javabackend.rest_service.request.contactPerson;

import com.amos2020.javabackend.entity.Salutation;
import com.amos2020.javabackend.rest_service.Constants;
import com.amos2020.javabackend.rest_service.request.BasicRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UpdateContactPersonRequest extends BasicRequest {
    @Getter
    @Setter
    @Schema(type = "String", name = "salutation", example = "MANN")
    private Salutation salutation;
    @Getter
    @Setter
    @Schema(type = "String", name = "title", example = "Dr.")
    private String title;
    @Getter
    @Setter
    @NotNull
    @NotBlank
    @Size(min = 1, max = Constants.NAME_LENGTH)
    @Schema(type = "String", name = "forename", example = "John", required = true)
    private String forename;
    @Getter
    @Setter
    @NotNull
    @NotBlank
    @Size(min = 1, max = Constants.NAME_LENGTH)
    @Schema(type = "String", name = "surname", example = "Doe", required = true)
    private String surname;
    @Getter
    @Setter
    @Size(max = Constants.NAME_LENGTH)
    @Schema(type = "String", name = "contactInformation", example = "john.doe@msg.de")
    private String contactInformation;
    @Getter
    @Setter
    @NotNull
    @NotBlank
    @Size(min = 1, max = Constants.NAME_LENGTH)
    @Schema(type = "String", name = "companyName", example = "msg systems AG", required = true)
    private String companyName;
    @Getter
    @Setter
    @Size(max = Constants.NAME_LENGTH)
    @Schema(type = "String", name = "department", example = "Softwareentwicklung")
    private String department;
    @Getter
    @Setter
    @Size(max = Constants.NAME_LENGTH)
    @Schema(type = "String", name = "department", example = "Software")
    private String sector;
    @Getter
    @Setter
    @NotNull
    @NotBlank
    @Size(min = 1, max = Constants.NAME_LENGTH)
    @Schema(type = "String", name = "corporateDivision", example = "msg Public Sector", required = true)
    private String corporateDivision;

    public void isValid() {
        assertNameIsValid(forename);
        assertNameIsValid(surname);
    }
}
