package com.amos2020.javabackend.rest_service.request.interview;

import com.amos2020.javabackend.rest_service.request.BasicRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
/**
 * Request object for creating a new interview.
 */
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
    @Size(max = MAX_SMALL_TEXT_LENGTH)
    @Schema(type = "String", name = "note", example = "Some note", required = true)
    private String note;
    @Getter
    @Setter
    @Schema(type = "Array", name = "interviewedContactPersons", example = "[\n" +
            "        {\n" +
            "            \"id\": 1,\n" +
            "            \"role\": \"Test\"\n" +
            "        }\n" +
            "    ]", required = true)
    private List<@Valid InterviewPerson> interviewedContactPersons;
    @Getter
    @Setter
    @Schema(type = "Array", name = "interviewScope", example = "[1]", required = true)
    private List<@Min(1) Integer> interviewScope;

    public void isValid() {
        assertDatesAreValid(startDate, endDate);
    }
}
