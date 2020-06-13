package com.amos2020.javabackend.rest_service.request.interview;

import com.amos2020.javabackend.rest_service.request.BasicRequest;
import lombok.Getter;
import lombok.Setter;


public class InterviewAddContactPersonRequest extends BasicRequest {

    @Getter
    @Setter
    private int contactPersonId;

    @Getter
    @Setter
    private String role;

    @Override
    public void isValid() {
        assertIdIsValid(contactPersonId);
        assertNameIsValid(role);
    }
}
