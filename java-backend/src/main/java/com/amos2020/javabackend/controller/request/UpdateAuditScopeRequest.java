package com.amos2020.javabackend.controller.request;

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
        assertChangeNoteIsValid(changeNote);
        assertIdIsValid(facCritId);
    }
}
