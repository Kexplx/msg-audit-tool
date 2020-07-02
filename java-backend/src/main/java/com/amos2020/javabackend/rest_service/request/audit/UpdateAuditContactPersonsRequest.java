package com.amos2020.javabackend.rest_service.request.audit;

import com.amos2020.javabackend.rest_service.request.BasicRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class UpdateAuditContactPersonsRequest extends BasicRequest {
    @Getter
    @Setter
    @NotNull
    @Min(1)
    @Schema(type = "Integer", name = "auditId", example = "1", required = true)
    private int auditId;
    @Getter
    @Setter
    @NotNull
    @Min(1)
    @Schema(type = "Integer", name = "contactPersonId", example = "1", required = true)
    private int contactPersonId;
}
