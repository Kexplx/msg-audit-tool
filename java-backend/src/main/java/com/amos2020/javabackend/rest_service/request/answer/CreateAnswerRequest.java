package com.amos2020.javabackend.rest_service.request.answer;

import com.amos2020.javabackend.rest_service.request.BasicRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
/**
 * Request object for creating a new answer
 */
public class CreateAnswerRequest extends BasicRequest {

    @Getter
    @Setter
    @NotNull
    @Min(message = "Question Id", value = 1)
    @Schema(type = "Integer", name = "questionId", example = "1", required = true)
    private int questionId;
    @Getter
    @Setter
    @NotNull
    @Min(message = "Interview Id", value = 1)
    @Schema(type = "Integer", name = "interviewId", example = "1", required = true)
    private int interviewId;
}
