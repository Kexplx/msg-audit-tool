package com.amos2020.javabackend.rest_service.request.audit;

import com.amos2020.javabackend.entity.AuditStatus;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

public class DeleteAuditRequest {
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
        if (reason == null || reason.isEmpty() || reason.length() < 3) {
            throw new IllegalArgumentException("Invalid reason");
        }

        if (contactPerson == null || contactPerson <= 0) {
            throw new IllegalArgumentException("Invalid contactPerson");
        }

        if (date == null) {
            throw new IllegalArgumentException("Invalid date");
        }
    }
}