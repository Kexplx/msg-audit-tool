package com.amos2020.javabackend.controller.response;

import com.amos2020.javabackend.entity.Audit;
import com.amos2020.javabackend.entity.AuditStatus;
import com.amos2020.javabackend.entity.ContactPerson;
import com.amos2020.javabackend.entity.FacCrit;
import com.amos2020.javabackend.service.FacCritService;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public class CreateAuditResponse {

    public CreateAuditResponse(Audit audit, List<Integer> factorCriteriaIds, List<Integer> contactPersonIds) {
        this.auditId = audit.getId();
        this.auditName = audit.getName();
        this.startDate = audit.getStartDate();
        this.creationDate = audit.getCreationDate();
        this.status = audit.getStatus();
        this.scope = FacCritService.getInstance().getAllById(factorCriteriaIds);
       // this.contactPeople = ContactPersonService.getInstance().getAllByIds(contactPersonIds);
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
}
