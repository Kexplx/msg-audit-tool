package com.amos2020.javabackend.rest_service.request.scope;

import com.amos2020.javabackend.rest_service.Constants;
import com.amos2020.javabackend.rest_service.request.BasicRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Setter;
import lombok.Getter;

import javax.validation.constraints.Size;

public class UpdateScopeRequest extends BasicRequest {

    @Getter
    @Setter
    @Schema(type = "boolean", name = "removed", example = "true")
    private boolean removed;

    @Getter
    @Setter
    @Size(message = "change note", max = Constants.NOTE_LENGTH)
    @Schema(type = "String", name = "change_note", example = "Some change note")
    private String change_note;

    @Getter
    @Setter
    @Size(message = "note", max = Constants.NOTE_LENGTH)
    @Schema(type = "String", name = "note", example = "Some note")
    private String note;

    public void isValid() {
        assertNoteIsValid(note);
        assertNoteIsValid(change_note);
    }
}
