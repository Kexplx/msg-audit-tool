package com.amos2020.javabackend.rest_service.response;

import com.amos2020.javabackend.entity.Scope;
import lombok.Getter;
import lombok.Setter;

public class BasicScopeResponse {

    public BasicScopeResponse(Scope scope){
        this.auditId = scope.getAuditId();
        this.faccritId = scope.getFaccritId();
        this.changeNote = scope.getChangeNote();
        this.removed = scope.getRemoved();
        this.note = scope.getNote();
    }
    @Getter
    @Setter
    private int auditId;

    @Getter
    @Setter
    private int faccritId;

    @Getter
    @Setter
    private String changeNote;

    @Getter
    @Setter
    private Boolean removed;

    @Getter
    @Setter
    private String note;
}
