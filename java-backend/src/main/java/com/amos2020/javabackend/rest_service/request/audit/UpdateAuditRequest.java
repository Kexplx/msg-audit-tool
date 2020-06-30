package com.amos2020.javabackend.rest_service.request.audit;

import com.amos2020.javabackend.rest_service.Constants;
import com.amos2020.javabackend.rest_service.request.BasicRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;

public class UpdateAuditRequest extends BasicRequest {
    @Getter
    @Setter
    @NotNull
    @NotBlank
    @Size(min = 1, max = Constants.NAME_LENGTH)
    @Schema(type = "string", name = "auditName", example = "updated audit name", required = true)
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

    public void isValid() throws IllegalArgumentException {
        assertDatesAreValid(startDate, endDate);
    }
}
