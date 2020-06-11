package com.amos2020.javabackend.rest_service.request;

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

    public void isValid() throws IllegalArgumentException {
        assertNoteIsValid(changeNote);
        assertIdIsValid(facCritId);
    }
}
