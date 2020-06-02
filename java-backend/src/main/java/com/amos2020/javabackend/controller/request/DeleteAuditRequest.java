package com.amos2020.javabackend.controller.request;

import com.amos2020.javabackend.entity.AuditStatus;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.ArrayList;

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
        if(reason.isEmpty() || reason == null || reason.length() < 3) {
            throw new IllegalArgumentException("Invalid reason");
        }

        if(contactPerson == null || contactPerson <= 0) {
            throw new  IllegalArgumentException("Invalid contactPerson");
        }

        if (date == null) {
            throw new IllegalArgumentException("Invalid date");
        }
    }
}