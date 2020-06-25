package com.amos2020.javabackend.rest_service.request.audit;

import com.amos2020.javabackend.rest_service.request.BasicRequest;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class UpdateAuditContactPeopleRequest extends BasicRequest {
    @Getter
    @Setter
    @NotNull
    @Min(1)
    private int auditId;
    @Getter
    @Setter
    @NotNull
    @Min(1)
    private int contactPersonId;
}
