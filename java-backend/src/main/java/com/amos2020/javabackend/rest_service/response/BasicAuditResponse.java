package com.amos2020.javabackend.rest_service.response;

import com.amos2020.javabackend.entity.Audit;
import com.amos2020.javabackend.entity.AuditStatus;
import com.amos2020.javabackend.entity.ContactPerson;
import com.amos2020.javabackend.entity.FacCrit;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public class BasicAuditResponse {

    public BasicAuditResponse(Audit audit, List<FacCrit> scope, List<ContactPerson> contactPeople) {
        this.auditId = audit.getId();
        this.auditName = audit.getName();
        this.startDate = audit.getStartDate();
        this.endDate = audit.getEndDate();
        this.creationDate = audit.getCreationDate();
        this.status = audit.getStatus();
        this.scope = scope;
        this.contactPeople = contactPeople;
        this.cancellationContactPerson = audit.getContactPersonByCancellationContactPerson();
        this.cancellationDate = audit.getCancellationDate();
        this.cancellationReason = audit.getCancellationReason();
    }

    @Getter
    @Setter
    private int auditId;

    @Getter
    @Setter

    private String auditName;
    @Getter
    @Setter
    private Date startDate;
    @Getter
    @Setter
    private Date endDate;
    @Getter
    @Setter
    private Timestamp creationDate;

    @Getter
    @Setter
    private AuditStatus status;

    @Getter
    @Setter
    private List<FacCrit> scope;

    @Getter
    @Setter
    private List<ContactPerson> contactPeople;

    @Getter
    @Setter
    private Date cancellationDate;

    @Getter
    @Setter
    private String cancellationReason;

    @Getter
    @Setter
    private ContactPerson cancellationContactPerson;

}
