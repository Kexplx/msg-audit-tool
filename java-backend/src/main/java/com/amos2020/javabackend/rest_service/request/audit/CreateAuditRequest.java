package com.amos2020.javabackend.rest_service.request.audit;

import com.amos2020.javabackend.rest_service.request.BasicRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class CreateAuditRequest extends BasicRequest {
    @Getter
    @Setter
    @NotNull
    @NotBlank
    @Size(min = 1, max = 256)
    @Schema(type = "String", name = "auditName", example = "MSG project audit", required = true)
    private String auditName;

    @Getter
    @Setter
    @NotNull
    @Schema(type = "string", name = "startDate", format = "date", required = true)
    private Date startDate;

    @Getter
    @Setter
    @Schema(type = "string", name = "endDate", format = "date")
    private Date endDate;

    @Getter
    @Setter
    @Schema(type = "Array", name = "scope", example = "[1]", required = true)
    private List<@Min(1) Integer> scope;

    @Getter
    @Setter
    @Schema(type = "Array", name = "contactPeople", example = "[1]", required = true)
    private List<@Min(1) Integer> contactPeople;

    public void isValid() throws IllegalArgumentException {
        assertDatesAreValid(startDate, endDate);

        if (contactPeople == null) {
            contactPeople = new ArrayList<>();
        }
        if (scope == null) {
            scope = new ArrayList<>();
        }
    }


}
