package com.amos2020.javabackend.rest_service.request.audit;

import com.amos2020.javabackend.rest_service.request.BasicRequest;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class CreateAuditRequest extends BasicRequest {
    @Getter
    @Setter
    @NotNull
    @NotBlank
    @Size(min = 1, max = 256)
    private String auditName;

    @Getter
    @Setter
    @NotNull
    private Date startDate;

    @Getter
    @Setter
    private Date endDate;

    @Getter
    @Setter
    private List<@Min(1) Integer> scope;

    @Getter
    @Setter
    private List<@Min(1) Integer> contactPeople;

    public void isValid() throws IllegalArgumentException {
        assertDatesAreValid(startDate, endDate);

        if (contactPeople == null) {
            contactPeople = new ArrayList<>();
        }
        if (scope == null) {
            scope = new ArrayList<>();
        }
    }


}
