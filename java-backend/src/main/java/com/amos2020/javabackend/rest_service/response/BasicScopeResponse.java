package com.amos2020.javabackend.rest_service.response;


import com.amos2020.javabackend.entity.Scope;
import lombok.Getter;
import lombok.Setter;

public class BasicScopeResponse {

    public BasicScopeResponse() {

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
    private int auditId;
    @Getter
    @Setter
    private int facCritId;

    @Getter
    @Setter
    private boolean removed;


    @Getter
    @Setter
    private String change_note;


    @Getter
    @Setter
    private String note;


}
