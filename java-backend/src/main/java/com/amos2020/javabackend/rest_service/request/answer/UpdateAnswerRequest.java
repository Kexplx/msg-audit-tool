package com.amos2020.javabackend.rest_service.request.answer;

import com.amos2020.javabackend.rest_service.Constants;
import com.amos2020.javabackend.rest_service.request.BasicRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UpdateAnswerRequest extends BasicRequest {

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

    @Getter
    @Setter
    @Schema(type = "boolean", name = "result", example = "true")
    private Boolean result;

    @Getter
    @Setter
    @Schema(type = "boolean", name = "responsible", example = "true")
    private Boolean responsible;

    @Getter
    @Setter
    @Schema(type = "boolean", name = "documentation", example = "true")
    private Boolean documentation;

    @Getter
    @Setter
    @Schema(type = "boolean", name = "procedure", example = "true")
    private Boolean procedure;

    @Getter
    @Setter
    @Size(message = "reason", max = Constants.NOTE_LENGTH)
    @Schema(type = "String", name = "reason", example = "Some reason")
    private String reason;

    @Getter
    @Setter
    @Size(message = "proof", max = Constants.NOTE_LENGTH)
    @Schema(type = "String", name = "proof", example = "Some proof")
    private String proof;

    @Getter
    @Setter
    @Size(message = "annotation", max = Constants.NOTE_LENGTH)
    @Schema(type = "String", name = "annotation", example = "Some annotation")
    private String annotation;
}
