package com.amos2020.javabackend.rest_service.response;

import com.amos2020.javabackend.entity.Answer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

public class BasicAnswerResponse {

    @Getter
    @Setter
    @Schema(type = "Integer", name = "questionId", example = "123456")
    private int questionId;
    @Getter
    @Setter
    @Schema(type = "Integer", name = "interviewId", example = "123456")
    private int interviewId;
    @Getter
    @Setter
    @Schema(type = "Integer", name = "faccritId", example = "123456")
    private int faccritId;
    @Getter
    @Setter
    @Schema(type = "String", name = "annotation", example = "Some annotation")
    private String annotation;
    @Getter
    @Setter
    @Schema(type = "String", name = "proof", example = "Some proof")
    private String proof;
    @Getter
    @Setter
    @Schema(type = "String", name = "reason", example = "Some reason")
    private String reason;
    @Getter
    @Setter
    @Schema(type = "boolean", name = "procedure", example = "true")
    private Boolean procedure;
    @Getter
    @Setter
    @Schema(type = "boolean", name = "documentation", example = "true")
    private Boolean documentation;
    @Getter
    @Setter
    @Schema(type = "boolean", name = "responsible", example = "true")
    private Boolean responsible;
    @Getter
    @Setter
    @Schema(type = "boolean", name = "result", example = "true")
    private Boolean result;

    public BasicAnswerResponse(Answer answer) {
        this.questionId = answer.getQuestionId();
        this.interviewId = answer.getInterviewId();
        this.faccritId = answer.getFaccritId();
        this.result = answer.getResult();
        this.responsible = answer.getResponsible();
        this.documentation = answer.getDocumentation();
        this.procedure = answer.getProcedure();
        this.reason = answer.getReason();
        this.proof = answer.getProof();
        this.annotation = answer.getAnnotation();
    }

    public BasicAnswerResponse() {
        //empty contructor
    }

}
