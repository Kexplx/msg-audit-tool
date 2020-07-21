package com.amos2020.javabackend.rest_service.request.audit;

import com.amos2020.javabackend.Constants;
import com.amos2020.javabackend.rest_service.request.BasicRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
/**
 * Request object for updating the scope of an existing audit
 */
public class UpdateAuditScopeRequest extends BasicRequest {
    @Getter
    @Setter
    @NotNull
    @Min(message = "facccrit ID", value = 1)
    @Schema(type = "Integer", name = "facCritId", example = "1", required = true)
    private int facCritId;

    @Getter
    @Setter
    @Size(message = "change note", max = Constants.NOTE_LENGTH)
    @Schema(type = "String", name = "changeNote", example = "Change note for a scope item", required = true)
    private String changeNote;

    @Getter
    @Setter
    @NotNull
    @Schema(type = "boolean", name = "removed", example = "true", required = true)
    private boolean removed;

    @Getter
    @Setter
    @Size(message = "normal note", max = Constants.TEXT_LENGTH)
    @Schema(type = "String", name = "note", example = "Note for a scope item", required = true)
    private String note;
}
