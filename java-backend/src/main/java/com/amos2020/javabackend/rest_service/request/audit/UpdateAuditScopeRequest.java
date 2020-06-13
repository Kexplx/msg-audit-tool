package com.amos2020.javabackend.rest_service.request.audit;

import com.amos2020.javabackend.rest_service.request.BasicRequest;
import lombok.Getter;
import lombok.Setter;

public class UpdateAuditScopeRequest extends BasicRequest {
    @Getter
    @Setter
    private int facCritId;

    @Getter
    @Setter
    private String changeNote;

    @Getter
    @Setter
    private boolean removed;

    @Getter
    @Setter
    private String note;

    public void isValid() throws IllegalArgumentException {
        assertNoteIsValid(changeNote);
        assertIdIsValid(facCritId);
        assertTextIsValid(note);
    }
}
