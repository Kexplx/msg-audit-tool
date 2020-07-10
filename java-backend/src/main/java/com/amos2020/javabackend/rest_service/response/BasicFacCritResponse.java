package com.amos2020.javabackend.rest_service.response;

import com.amos2020.javabackend.entity.FacCrit;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

public class BasicFacCritResponse {
    @Getter
    @Setter
    @Schema(type = "Integer", name = "id", example = "15")
    private int id;
    @Getter
    @Setter
    @Schema(type = "Integer", name = "referenceId", example = "3")
    private Integer referenceId;
    @Getter
    @Setter
    @Schema(type = "String", name = "name", example = "Nützlichkeit")
    private String name;
    @Getter
    @Setter
    @Schema(type = "String", name = "goal", example = "Das Ziel dieses Kriteriums ist....")
    private String goal;

    public BasicFacCritResponse(){
        //empty
    }

    public BasicFacCritResponse(FacCrit facCrit) {
        this.id = facCrit.getId();
        this.referenceId = facCrit.getReferenceId();
        this.name = facCrit.getName();
        this.goal = facCrit.getGoal();
    }
}
