package com.amos2020.javabackend.rest_service.request.audit;

import com.amos2020.javabackend.entity.AuditStatus;
import com.amos2020.javabackend.rest_service.request.BasicRequest;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

public class DeleteAuditRequest extends BasicRequest {
    @Getter
    @Setter
    private String reason;

    @Getter
    @Setter
    private Integer contactPerson;

    @Getter
    @Setter
    private Date date;

    @Getter
    final AuditStatus status = AuditStatus.CANCELED;

    public void isValid() throws IllegalArgumentException {
        assertNameIsValid(reason);
        assertIdIsValid(contactPerson);
        assertDateIsNotNull(date);
    }
}