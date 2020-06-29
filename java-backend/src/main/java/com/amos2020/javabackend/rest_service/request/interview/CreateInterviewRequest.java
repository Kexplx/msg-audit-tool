package com.amos2020.javabackend.rest_service.request.interview;

import com.amos2020.javabackend.rest_service.request.BasicRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;

public class CreateInterviewRequest extends BasicRequest {

    @Getter
    @Setter
    @NotNull
    @Min(1)
    @Schema(type = "Integer", name = "auditId", example = "1", required = true)
    private int auditId;
    @Getter
    @Setter
    @NotNull
    @Schema(type = "string", name = "startDate", format = "date", required = true)
    private Date startDate;

    @Getter
    @Setter
    @Schema(type = "string", name = "endDate", format = "date", required = true)
    private Date endDate;
    @Getter
    @Setter
    @NotNull
    @NotBlank
    @Size(max = MAX_SMALL_TEXT_LENGTH)
    @Schema(type = "String", name = "goal", example = "Das Ziel ist ...", required = true)
    private String goal;
    @Getter
    @Setter
    @Schema(type = "Array", name = "interviewedPeople", example = "{\"1\":" + "\"Software architect\"}", required = true)
    private HashMap<@Min(1) Integer, @NotNull @NotBlank @Size(min = 1, max = 256) String> interviewedPeople;
    @Getter
    @Setter
    @Schema(type = "Array", name = "interviewScope", example = "[1]", required = true)
    private List<@Min(1) Integer> interviewScope;

    public void isValid() {
        assertDatesAreValid(startDate, endDate);
    }
}
