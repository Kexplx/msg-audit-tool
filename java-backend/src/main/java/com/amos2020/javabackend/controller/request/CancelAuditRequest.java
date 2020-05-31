package com.amos2020.javabackend.controller.request;

import com.amos2020.javabackend.entity.AuditStatus;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.ArrayList;

public class CancelAuditRequest {
    @Getter
    @Setter
    private int auditId;

    @Getter
    @Setter
    private String reason;

    @Getter
    @Setter
    private Integer contact;

    @Getter
    @Setter
    private Date date;

    @Getter
    final AuditStatus status = AuditStatus.CANCELED;

    public void isValid() throws IllegalArgumentException {


    }
}
