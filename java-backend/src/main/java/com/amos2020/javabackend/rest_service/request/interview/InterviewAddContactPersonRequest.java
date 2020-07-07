package com.amos2020.javabackend.rest_service.request.interview;

import com.amos2020.javabackend.rest_service.request.BasicRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Request object for adding an existing contact person to an existing interview.
 */
public class InterviewAddContactPersonRequest extends BasicRequest {

    @Getter
    @Setter
    @NotNull
    @Min(1)
    @Schema(type = "Integer", name = "contactPersonId", example = "1", required = true)
    private int contactPersonId;

    @Getter
    @Setter
    @NotNull
    @NotBlank
    @Size(max = MAX_NAME_LENGTH)
    @Schema(type = "String", name = "role", example = "Software architect", required = true)
    private String role;
}
