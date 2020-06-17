package com.amos2020.javabackend.rest_service.request.scope;

import com.amos2020.javabackend.rest_service.request.BasicRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class AddScopeRequest extends BasicRequest {

    @Getter
    @Setter
    private List<Integer> scope;


    @Override
    public void isValid() {
        for (Integer i : scope) {
            assertIdIsValid(i);
        }
    }
}
