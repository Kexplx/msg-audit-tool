package com.amos2020.javabackend.rest_service.request.scope;

import com.amos2020.javabackend.rest_service.request.BasicRequest;
import lombok.Setter;
import lombok.Getter;

public class UpdateScopeRequest extends BasicRequest {

    @Getter
    @Setter
    private boolean removed;

    @Getter
    @Setter
    private String change_note;

    @Getter
    @Setter
    private String note;

    @Override
    public void isValid() {
        assertNoteIsValid(note);
        assertNoteIsValid(change_note);
    }
}
