package com.amos2020.javabackend.rest_service.request.interview;

import com.amos2020.javabackend.entity.InterviewStatus;
import com.amos2020.javabackend.rest_service.request.BasicRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
/**
 * Request object for updating an existing interview.
 */
public class UpdateInterviewRequest extends BasicRequest {

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
    @Schema(type = "String", name = "status", example = "ACTIVE", required = true)
    private InterviewStatus status;
    @Getter
    @Setter
    @NotNull
    @Size(max = MAX_SMALL_TEXT_LENGTH)
    @Schema(type = "String", name = "note", example = "Some changed note", required = true)
    private String note;

    /**
     * Checks if provided startDate is before endDate
     */
    public void isValid() {
        assertDatesAreValid(startDate, endDate);
    }
}
