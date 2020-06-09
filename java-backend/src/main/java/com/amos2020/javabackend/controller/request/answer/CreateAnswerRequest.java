package com.amos2020.javabackend.controller.request.answer;

import com.amos2020.javabackend.controller.request.BasicRequest;
import lombok.Getter;
import lombok.Setter;

public class CreateAnswerRequest extends BasicRequest {

    @Getter
    @Setter
    private int questionId;
    @Getter
    @Setter
    private int interviewId;

    @Override
    public void isValid() {
        assertIdIsValid(questionId);
        assertIdIsValid(interviewId);
    }
}
