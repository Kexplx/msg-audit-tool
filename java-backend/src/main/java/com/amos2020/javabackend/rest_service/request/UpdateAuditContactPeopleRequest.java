package com.amos2020.javabackend.rest_service.request;

import lombok.Getter;
import lombok.Setter;

public class UpdateAuditContactPeopleRequest extends BasicRequest {
    @Getter
    @Setter
    private int auditId;
    @Getter
    @Setter
    private int contactPersonId;

    @Override
    public void isValid() {
        assertIdIsValid(auditId);
        assertIdIsValid(contactPersonId);
    }
}
