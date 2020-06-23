package com.amos2020.javabackend.rest_service.request.answer;

import com.amos2020.javabackend.rest_service.request.BasicRequest;
import lombok.Getter;
import lombok.Setter;

public class UpdateAnswerRequest extends BasicRequest {

    @Getter
    @Setter
    private int questionId;

    @Getter
    @Setter
    private int interviewId;

    @Getter
    @Setter
    private Boolean result;

    @Getter
    @Setter
    private Boolean responsible;

    @Getter
    @Setter
    private Boolean documentation;

    @Getter
    @Setter
    private Boolean procedure;

    @Getter
    @Setter
    private String reason;

    @Getter
    @Setter
    private String proof;

    @Getter
    @Setter
    private String annotation;

    @Override
    public void isValid() {
        assertIdIsValid(questionId);
        assertIdIsValid(interviewId);
        assertNoteIsValid(reason);
        assertNoteIsValid(proof);
        assertNoteIsValid(annotation);
    }
}
