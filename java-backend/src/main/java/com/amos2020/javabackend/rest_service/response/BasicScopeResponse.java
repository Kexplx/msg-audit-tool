package com.amos2020.javabackend.rest_service.response;


import com.amos2020.javabackend.entity.Scope;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

public class BasicScopeResponse {

    public BasicScopeResponse() {
        //empty constructor
    }

    public BasicScopeResponse(int auditId, int facCritId, boolean removed, String change_note, String note) {
        this.auditId = auditId;
        this.facCritId = facCritId;
        this.removed = removed;
        this.change_note = change_note;
        this.note = note;
    }

    public BasicScopeResponse(Scope scope) {
        this.auditId = scope.getAuditId();
        this.facCritId = scope.getFaccritId();
        this.removed = scope.getRemoved();
        this.change_note = scope.getChangeNote();
        this.note = scope.getNote();
    }

    @Getter
    @Setter
    @Schema(type = "Integer", name = "auditId", example = "123456")
    private int auditId;

    @Getter
    @Setter
    @Schema(type = "Integer", name = "faccritId", example = "123456")
    private int facCritId;

    @Getter
    @Setter
    @Schema(type = "boolean", name = "removed", example = "true")
    private boolean removed;

    @Getter
    @Setter
    @Schema(type = "String", name = "change_note", example = "Some change note")
    private String change_note;

    @Getter
    @Setter
    @Schema(type = "String", name = "note", example = "Some note")
    private String note;
}
