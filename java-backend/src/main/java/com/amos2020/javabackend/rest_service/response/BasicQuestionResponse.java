package com.amos2020.javabackend.rest_service.response;

import com.amos2020.javabackend.entity.Question;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

public class BasicQuestionResponse {

    @Getter
    @Setter
    @Schema(type = "Integer", name = "id", example = "15")
    private int id;

    @Getter
    @Setter
    @Schema(type = "Integer", name = "faccritId", example = "2")
    private int faccritId;

    @Getter
    @Setter
    @Schema(type = "String", name = "name", example = "Werden konkrete Maßnahmen ergriffen, um das Qualitätskriterium in der Anwendung zu steigern?")
    private String textDe;

    public BasicQuestionResponse(Question question) {
        id = question.getId();
        faccritId = question.getFaccritId();
        textDe = question.getTextDe();
    }
}
