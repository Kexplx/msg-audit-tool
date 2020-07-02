package com.amos2020.javabackend.rest_service.request.interview;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class InterviewPerson {
    @Getter
    @Setter
    @Min(1)
    private int id;
    @Getter
    @Setter
    @NotNull
    @NotBlank
    @Size(min = 1, max = 256)
    private String role;

    public InterviewPerson(int id, String role) {
        this.id = id;
        this.role = role;
    }
}
