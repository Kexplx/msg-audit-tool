package com.amos2020.javabackend.rest_service.response;

import com.amos2020.javabackend.entity.Audit;
import com.amos2020.javabackend.entity.AuditStatus;
import com.amos2020.javabackend.entity.ContactPerson;
import com.amos2020.javabackend.entity.FacCrit;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public class BasicAuditResponse {

    @Getter
    @Setter
    @Schema(type = "Integer", name = "auditId", example = "123456")
    private int auditId;
    @Getter
    @Setter
    @Schema(type = "String", name = "auditName", example = "MSG project audit")
    private String auditName;
    @Getter
    @Setter
    @Schema(type = "string", name = "startDate", format = "date")
    private Date startDate;
    @Getter
    @Setter
    @Schema(type = "string", name = "endDate", format = "date")
    private Date endDate;
    @Getter
    @Setter
    @Schema(type = "string", name = "creationDate", format = "date-time")
    private Timestamp creationDate;
    @Getter
    @Setter
    @Schema(type = "string", name = "status", example = "ACTIVE")
    private AuditStatus status;
    @Getter
    @Setter
    @Schema(type = "array", name = "scope", example = "[{\n" +
            "   \"id\": 50,\n" +
            "   \"referenceId\": 12,\n" +
            "   \"name\": \"Modifizierbarkeit\"\n" +
            "}]")
    private List<FacCrit> scope;
    @Getter
    @Setter
    @Schema(type = "array", name = "contactPeople", example = "[{\n" +
            "    \"id\": 1,\n" +
            "    \"salutation\": \"MANN\",\n" +
            "    \"title\": \"Prof\",\n" +
            "    \"forename\": \"Max\",\n" +
            "    \"surname\": \"Mustermann\",\n" +
            "    \"contactInformation\": \"max.mustermann@gmx.de, tel: 0123456789\",\n" +
            "    \"companyName\": \"msg systems AG\",\n" +
            "    \"department\": \"Softwareentwicklung\",\n" +
            "    \"sector\": \"msg Public Sector\",\n" +
            "    \"corporateDivision\": \"Software\"\n" +
            "  }]")
    private List<ContactPerson> contactPeople;
    @Getter
    @Setter
    @Schema(type = "string", name = "cancellationDate", format = "date")
    private Date cancellationDate;
    @Getter
    @Setter
    @Schema(type = "String", name = "cancellationReason", example = "Project got canceled")
    private String cancellationReason;
    @Getter
    @Setter
    @Schema(type = "object", name = "cancellationContactPerson", example = "{\n" +
            "    \"id\": 1,\n" +
            "    \"salutation\": \"MANN\",\n" +
            "    \"title\": \"Prof\",\n" +
            "    \"forename\": \"Max\",\n" +
            "    \"surname\": \"Mustermann\",\n" +
            "    \"contactInformation\": \"max.mustermann@gmx.de, tel: 0123456789\",\n" +
            "    \"companyName\": \"msg systems AG\",\n" +
            "    \"department\": \"Softwareentwicklung\",\n" +
            "    \"sector\": \"msg Public Sector\",\n" +
            "    \"corporateDivision\": \"Software\"\n" +
            "  }")
    private ContactPerson cancellationContactPerson;

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

}
