package com.amos2020.javabackend.rest_service.request.audit;

import com.amos2020.javabackend.entity.AuditStatus;
import com.amos2020.javabackend.Constants;
import com.amos2020.javabackend.rest_service.request.BasicRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;

/**
 * Request object for updating an existing audit
 */
public class UpdateAuditRequest extends BasicRequest {
    @Getter
    @Setter
    @NotNull
    @NotBlank
    @Size(min = 1, max = Constants.NAME_LENGTH)
    @Schema(type = "string", name = "name", example = "updated audit name", required = true)
    private String name;
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
    @NotNull
    @Schema(type = "String", name = "status", example = "ACTIVE", required = true)
    private AuditStatus status;

    /**
     * Checks if provided startDate is before endDate
     */
    public void isValid() throws IllegalArgumentException {
        assertDatesAreValid(startDate, endDate);
    }
}
