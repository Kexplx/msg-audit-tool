package com.amos2020.javabackend.controller.response;

import com.amos2020.javabackend.entity.Answer;
import lombok.Getter;
import lombok.Setter;

public class BasicAnswerResponse {

    public BasicAnswerResponse(Answer answer){
        this.questionId = answer.getQuestionId();
        this.interviewId = answer.getInterviewId();
        this.result = answer.getResult();
        this.responsible = answer.getResponsible();
        this.documentation = answer.getDocumentation();
        this.procedure = answer.getProcedure();
        this.reason = answer.getReason();
        this.proof= answer.getProof();
        this.annotation = answer.getAnnotation();
    }

    @Getter
    @Setter
    private int questionId;

    @Getter
    @Setter
    private int interviewId;

    @Getter
    @Setter
    private String annotation;

    @Getter
    @Setter
    private String proof;

    @Getter
    @Setter
    private String reason;

    @Getter
    @Setter
    private Boolean procedure;

    @Getter
    @Setter
    private Boolean documentation;

    @Getter
    @Setter
    private Boolean responsible;

    @Getter
    @Setter
    private Boolean result;

}
