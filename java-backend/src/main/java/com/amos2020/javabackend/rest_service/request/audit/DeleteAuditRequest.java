package com.amos2020.javabackend.rest_service.request.audit;

import com.amos2020.javabackend.rest_service.Constants;
import com.amos2020.javabackend.rest_service.request.BasicRequest;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;

public class DeleteAuditRequest extends BasicRequest {
    @Getter
    @Setter
    @NotNull
    @Size(min = 1, max = Constants.NAME_LENGTH)
    @NotBlank
    private String reason;

    @Getter
    @Setter
    @Min(1)
    @NotNull
    private Integer contactPerson;

    @Getter
    @Setter
    @NotNull
    private Date date;
}