package com.amos2020.javabackend.rest_service.request.audit;

import com.amos2020.javabackend.rest_service.Constants;
import com.amos2020.javabackend.rest_service.request.BasicRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
/**
 * Request object for deleting an audit
 */
public class DeleteAuditRequest extends BasicRequest {
    @Getter
    @Setter
    @NotNull
    @Size(min = 1, max = Constants.NAME_LENGTH)
    @NotBlank
    @Schema(type = "String", name = "reason", example = "Client canceled the audit", required = true)
    private String reason;

    @Getter
    @Setter
    @Min(1)
    @NotNull
    @Schema(type = "Integer", name = "contactPerson", example = "1", required = true)
    private Integer contactPerson;

    @Getter
    @Setter
    @NotNull
    @Schema(type = "string", name = "date", format = "date", required = true)
    private Date date;
}