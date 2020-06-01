package com.amos2020.javabackend.controller.request;

import lombok.Getter;
import lombok.Setter;

public class UpdateAuditScopeRequest {
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
        assertId();
        assertString();
    }

    private void assertString() {
        if (changeNote != null && changeNote.length() > 256) {
            throw new IllegalArgumentException("Change note too long");
        }
    }

    private void assertId() {
        if (facCritId < 1) {
            throw new IllegalArgumentException("Ids can not be negative or null");
        }
    }
}
