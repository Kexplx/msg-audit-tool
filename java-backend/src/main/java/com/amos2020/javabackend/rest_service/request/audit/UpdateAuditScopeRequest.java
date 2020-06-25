package com.amos2020.javabackend.rest_service.request.audit;

import com.amos2020.javabackend.rest_service.Constants;
import com.amos2020.javabackend.rest_service.request.BasicRequest;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UpdateAuditScopeRequest extends BasicRequest {
    @Getter
    @Setter
    @NotNull
    @Min(message = "facccrit ID", value = 1)
    private int facCritId;

    @Getter
    @Setter
    @Size(message = "change note", max = Constants.NOTE_LENGTH)
    private String changeNote;

    @Getter
    @Setter
    @NotNull
    private boolean removed;

    @Getter
    @Setter
    @Size(message = "normal note", max = Constants.TEXT_LENGTH)
    private String note;
}
