package com.amos2020.javabackend;

import com.amos2020.javabackend.controller.AuditController;
import com.amos2020.javabackend.controller.request.CreateAuditRequest;
import com.amos2020.javabackend.controller.request.DeleteAuditRequest;
import com.amos2020.javabackend.controller.request.UpdateAuditRequest;
import com.amos2020.javabackend.controller.request.UpdateAuditScopeRequest;
import com.amos2020.javabackend.entity.Audit;
import com.amos2020.javabackend.entity.AuditStatus;
import com.amos2020.javabackend.entity.FacCrit;
import com.amos2020.javabackend.entity.Scope;
import com.amos2020.javabackend.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import javassist.NotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AuditController.class)
public class AuditControllerTest {

    @Autowired
    MockMvc controller;

    @MockBean
    private AuditService auditService;
    @MockBean
    private ScopeService scopeService;
    @MockBean
    private ContactPersonService contactPersonService;
    @MockBean
    private AuditContactPersonService auditContactPersonService;
    @MockBean
    private FacCritService facCritService;

    @Test
    public void createAuditWithValidRequest_returnsOk() throws Exception {
        String auditName = "TestAuditName";
        Date startDate = Date.valueOf("2000-01-02");
        Date endDate = Date.valueOf("2000-01-03");
        List<Integer> scopes = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> contacts = Arrays.asList(1, 2, 3, 4, 5);

        CreateAuditRequest request = new CreateAuditRequest();
        request.setAuditName(auditName);
        request.setStartDate(startDate);
        request.setEndDate(endDate);
        request.setScope(scopes);
        request.setContactPeople(contacts);
        String requestAsJson = buildJson(request);

        Audit audit = new Audit();
        audit.setName(auditName);
        audit.setStartDate(startDate);
        audit.setCreationDate(Timestamp.from(Instant.now()));
        audit.setStatus(AuditStatus.OPEN);
        if (endDate != null) {
            audit.setEndDate(endDate);
        }

        given(auditService.createAudit(request.getAuditName(), request.getStartDate(), request.getEndDate())).willReturn(audit);

        controller.perform(post("/audit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isOk());
    }

    @Test
    public void createAuditWithAuditNameNull_returns400() throws Exception {
        Date startDate = Date.valueOf("2000-01-02");
        Date endDate = Date.valueOf("2000-01-03");
        List<Integer> scopes = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> contacts = Arrays.asList(1, 2, 3, 4, 5);

        CreateAuditRequest request = new CreateAuditRequest();
        request.setStartDate(startDate);
        request.setEndDate(endDate);
        request.setScope(scopes);
        request.setContactPeople(contacts);
        String requestAsJson = buildJson(request);

        Audit audit = new Audit();
        audit.setStartDate(startDate);
        audit.setCreationDate(Timestamp.from(Instant.now()));
        audit.setStatus(AuditStatus.OPEN);
        if (endDate != null) {
            audit.setEndDate(endDate);
        }

        given(auditService.createAudit(request.getAuditName(), request.getStartDate(), request.getEndDate())).willReturn(audit);

        controller.perform(post("/audit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createAuditWithAuditNameBlank_returns400() throws Exception {
        String auditName = "";
        Date startDate = Date.valueOf("2000-01-02");
        Date endDate = Date.valueOf("2000-01-03");
        List<Integer> scopes = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> contacts = Arrays.asList(1, 2, 3, 4, 5);

        CreateAuditRequest request = new CreateAuditRequest();
        request.setAuditName(auditName);
        request.setStartDate(startDate);
        request.setEndDate(endDate);
        request.setScope(scopes);
        request.setContactPeople(contacts);
        String requestAsJson = buildJson(request);

        Audit audit = new Audit();
        audit.setName(auditName);
        audit.setStartDate(startDate);
        audit.setCreationDate(Timestamp.from(Instant.now()));
        audit.setStatus(AuditStatus.OPEN);
        if (endDate != null) {
            audit.setEndDate(endDate);
        }

        given(auditService.createAudit(request.getAuditName(), request.getStartDate(), request.getEndDate())).willReturn(audit);

        controller.perform(post("/audit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createAuditWithAuditNameTooShort_returns400() throws Exception {
        String auditName = "";
        Date startDate = Date.valueOf("2000-01-02");
        Date endDate = Date.valueOf("2000-01-03");
        List<Integer> scopes = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> contacts = Arrays.asList(1, 2, 3, 4, 5);

        CreateAuditRequest request = new CreateAuditRequest();
        request.setAuditName(auditName);
        request.setStartDate(startDate);
        request.setEndDate(endDate);
        request.setScope(scopes);
        request.setContactPeople(contacts);
        String requestAsJson = buildJson(request);

        Audit audit = new Audit();
        audit.setName(auditName);
        audit.setStartDate(startDate);
        audit.setCreationDate(Timestamp.from(Instant.now()));
        audit.setStatus(AuditStatus.OPEN);
        if (endDate != null) {
            audit.setEndDate(endDate);
        }

        given(auditService.createAudit(request.getAuditName(), request.getStartDate(), request.getEndDate())).willReturn(audit);

        controller.perform(post("/audit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createAuditWithAuditNameTooLong_returns400() throws Exception {
        char[] charArray = new char[46];
        Arrays.fill(charArray, 't');
        String auditName = new String(charArray);
        Date startDate = Date.valueOf("2000-01-02");
        Date endDate = Date.valueOf("2000-01-03");
        List<Integer> scopes = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> contacts = Arrays.asList(1, 2, 3, 4, 5);

        CreateAuditRequest request = new CreateAuditRequest();
        request.setAuditName(auditName);
        request.setStartDate(startDate);
        request.setEndDate(endDate);
        request.setScope(scopes);
        request.setContactPeople(contacts);
        String requestAsJson = buildJson(request);

        Audit audit = new Audit();
        audit.setName(auditName);
        audit.setStartDate(startDate);
        audit.setCreationDate(Timestamp.from(Instant.now()));
        audit.setStatus(AuditStatus.OPEN);
        if (endDate != null) {
            audit.setEndDate(endDate);
        }

        given(auditService.createAudit(request.getAuditName(), request.getStartDate(), request.getEndDate())).willReturn(audit);

        controller.perform(post("/audit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createAuditWithStartDateNull_returns400() throws Exception {
        String auditName = "testauditname";
        Date endDate = Date.valueOf("2000-01-03");
        List<Integer> scopes = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> contacts = Arrays.asList(1, 2, 3, 4, 5);

        CreateAuditRequest request = new CreateAuditRequest();
        request.setAuditName(auditName);
        request.setEndDate(endDate);
        request.setScope(scopes);
        request.setContactPeople(contacts);
        String requestAsJson = buildJson(request);

        Audit audit = new Audit();
        audit.setName(auditName);
        audit.setCreationDate(Timestamp.from(Instant.now()));
        audit.setStatus(AuditStatus.OPEN);
        audit.setEndDate(endDate);


        given(auditService.createAudit(request.getAuditName(), request.getStartDate(), request.getEndDate())).willReturn(audit);

        controller.perform(post("/audit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createAuditWithEndDateNull_returnsOk() throws Exception {
        String auditName = "testauditname";
        Date startDate = Date.valueOf("2000-01-03");
        List<Integer> scopes = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> contacts = Arrays.asList(1, 2, 3, 4, 5);

        CreateAuditRequest request = new CreateAuditRequest();
        request.setAuditName(auditName);
        request.setStartDate(startDate);
        request.setScope(scopes);
        request.setContactPeople(contacts);
        String requestAsJson = buildJson(request);

        Audit audit = new Audit();
        audit.setName(auditName);
        audit.setStartDate(startDate);
        audit.setCreationDate(Timestamp.from(Instant.now()));
        audit.setStatus(AuditStatus.OPEN);


        given(auditService.createAudit(request.getAuditName(), request.getStartDate(), request.getEndDate())).willReturn(audit);

        controller.perform(post("/audit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isOk());
    }

    @Test
    public void createAuditWithInvalidDates_returns400() throws Exception {
        String auditName = "testauditname";
        Date startDate = Date.valueOf("2000-01-03");
        Date endDate = Date.valueOf("2000-01-01");
        List<Integer> scopes = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> contacts = Arrays.asList(1, 2, 3, 4, 5);

        CreateAuditRequest request = new CreateAuditRequest();
        request.setAuditName(auditName);
        request.setStartDate(startDate);
        request.setEndDate(endDate);
        request.setScope(scopes);
        request.setContactPeople(contacts);
        String requestAsJson = buildJson(request);

        Audit audit = new Audit();
        audit.setName(auditName);
        audit.setStartDate(startDate);
        audit.setCreationDate(Timestamp.from(Instant.now()));
        audit.setStatus(AuditStatus.OPEN);
        if (endDate != null) {
            audit.setEndDate(endDate);
        }

        given(auditService.createAudit(request.getAuditName(), request.getStartDate(), request.getEndDate())).willReturn(audit);

        controller.perform(post("/audit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createAuditWithScopesNull_returnsOk() throws Exception {
        String auditName = "testauditname";
        Date startDate = Date.valueOf("2000-01-01");
        Date endDate = Date.valueOf("2000-01-03");
        List<Integer> contacts = Arrays.asList(1, 2, 3, 4, 5);

        CreateAuditRequest request = new CreateAuditRequest();
        request.setAuditName(auditName);
        request.setStartDate(startDate);
        request.setEndDate(endDate);
        request.setContactPeople(contacts);
        String requestAsJson = buildJson(request);

        Audit audit = new Audit();
        audit.setName(auditName);
        audit.setStartDate(startDate);
        audit.setCreationDate(Timestamp.from(Instant.now()));
        audit.setStatus(AuditStatus.OPEN);
        if (endDate != null) {
            audit.setEndDate(endDate);
        }

        given(auditService.createAudit(request.getAuditName(), request.getStartDate(), request.getEndDate())).willReturn(audit);

        controller.perform(post("/audit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isOk());
    }

    @Test
    public void createAuditWithScopesInvalid_returns400() throws Exception {
        String auditName = "testauditname";
        Date startDate = Date.valueOf("2000-01-01");
        Date endDate = Date.valueOf("2000-01-03");
        List<Integer> scopes = Arrays.asList(1, -1, 3, 4, 5);
        List<Integer> contacts = Arrays.asList(1, 2, 3, 4, 5);

        CreateAuditRequest request = new CreateAuditRequest();
        request.setAuditName(auditName);
        request.setStartDate(startDate);
        request.setEndDate(endDate);
        request.setScope(scopes);
        request.setContactPeople(contacts);
        String requestAsJson = buildJson(request);

        Audit audit = new Audit();
        audit.setName(auditName);
        audit.setStartDate(startDate);
        audit.setCreationDate(Timestamp.from(Instant.now()));
        audit.setStatus(AuditStatus.OPEN);
        if (endDate != null) {
            audit.setEndDate(endDate);
        }

        given(auditService.createAudit(request.getAuditName(), request.getStartDate(), request.getEndDate())).willReturn(audit);

        controller.perform(post("/audit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createAuditWithContactsInvalid_returns400() throws Exception {
        String auditName = "testauditname";
        Date startDate = Date.valueOf("2000-01-01");
        Date endDate = Date.valueOf("2000-01-03");
        List<Integer> scopes = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> contacts = Arrays.asList(1, 0, 3, 4, 5);

        CreateAuditRequest request = new CreateAuditRequest();
        request.setAuditName(auditName);
        request.setStartDate(startDate);
        request.setEndDate(endDate);
        request.setScope(scopes);
        request.setContactPeople(contacts);
        String requestAsJson = buildJson(request);

        Audit audit = new Audit();
        audit.setName(auditName);
        audit.setStartDate(startDate);
        audit.setCreationDate(Timestamp.from(Instant.now()));
        audit.setStatus(AuditStatus.OPEN);
        if (endDate != null) {
            audit.setEndDate(endDate);
        }

        given(auditService.createAudit(request.getAuditName(), request.getStartDate(), request.getEndDate())).willReturn(audit);

        controller.perform(post("/audit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createAuditWithContactsNull_returnsOk() throws Exception {
        String auditName = "testauditname";
        Date startDate = Date.valueOf("2000-01-01");
        Date endDate = Date.valueOf("2000-01-03");
        List<Integer> scopes = Arrays.asList(1, 2, 3, 4, 5);

        CreateAuditRequest request = new CreateAuditRequest();
        request.setAuditName(auditName);
        request.setStartDate(startDate);
        request.setEndDate(endDate);
        request.setScope(scopes);
        String requestAsJson = buildJson(request);

        Audit audit = new Audit();
        audit.setName(auditName);
        audit.setStartDate(startDate);
        audit.setCreationDate(Timestamp.from(Instant.now()));
        audit.setStatus(AuditStatus.OPEN);
        if (endDate != null) {
            audit.setEndDate(endDate);
        }

        given(auditService.createAudit(request.getAuditName(), request.getStartDate(), request.getEndDate())).willReturn(audit);

        controller.perform(post("/audit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isOk());
    }

    @Test
    public void getAuditByIdWithValidId_returnsOk() throws Exception {
        Audit audit = new Audit();
        audit.setScopesById(new ArrayList<>());
        audit.setAuditContactPeopleById(new ArrayList<>());
        given(auditService.getAuditById(1)).willReturn(audit);
        given(facCritService.getAllById(anyList())).willReturn(new ArrayList<>());
        given(contactPersonService.getAllByIds(anyList())).willReturn(new ArrayList<>());

        controller.perform(get("/audit/1")).andExpect(status().isOk());
    }

    @Test
    public void getAuditByIdWithInvalidId_returnsNotFound() throws Exception {
        given(auditService.getAuditById(0)).willThrow(NotFoundException.class);
        controller.perform(get("/audit/0")).andExpect(status().isNotFound());
    }

    @Test
    public void updateAuditByIdWithValidRequest_returnsOk() throws Exception {
        Audit audit = new Audit();
        audit.setScopesById(new ArrayList<>());
        audit.setAuditContactPeopleById(new ArrayList<>());

        given(auditService.getAuditById(1)).willReturn(audit);
        given(auditService.updateAudit(any())).willReturn(audit);
        given(facCritService.getAllById(anyList())).willReturn(new ArrayList<>());
        given(contactPersonService.getAllByIds(anyList())).willReturn(new ArrayList<>());


        UpdateAuditRequest request = new UpdateAuditRequest();
        request.setAuditName("New Test Name");
        request.setStartDate(Date.valueOf("2000-01-02"));
        request.setEndDate(Date.valueOf("2000-01-02"));
        request.setContactPeople(new ArrayList<>());

        String requestAsJson = buildJson(request);

        controller.perform(put("/audit/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isOk());
    }

    @Test
    public void updateAuditByIdWithAuditNotExisting_returnsNotFound() throws Exception {
        Audit audit = new Audit();
        audit.setScopesById(new ArrayList<>());
        audit.setAuditContactPeopleById(new ArrayList<>());

        given(auditService.getAuditById(1000)).willThrow(NotFoundException.class);
        given(auditService.updateAudit(any())).willReturn(audit);
        given(facCritService.getAllById(anyList())).willReturn(new ArrayList<>());
        given(contactPersonService.getAllByIds(anyList())).willReturn(new ArrayList<>());


        UpdateAuditRequest request = new UpdateAuditRequest();
        request.setAuditName("New Test Name");
        request.setStartDate(Date.valueOf("2000-01-02"));
        request.setEndDate(Date.valueOf("2000-01-02"));
        request.setContactPeople(new ArrayList<>());

        String requestAsJson = buildJson(request);

        controller.perform(put("/audit/1000")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateAuditByIdWithContactPersonNotExisting_returnsNotFound() throws Exception {
        Audit audit = new Audit();
        audit.setScopesById(new ArrayList<>());
        audit.setAuditContactPeopleById(new ArrayList<>());

        given(auditService.getAuditById(1)).willReturn(audit);
        given(auditService.updateAudit(any())).willReturn(audit);
        given(facCritService.getAllById(anyList())).willReturn(new ArrayList<>());
        given(contactPersonService.getAllByIds(anyList())).willThrow(NotFoundException.class);


        UpdateAuditRequest request = new UpdateAuditRequest();
        request.setAuditName("New Test Name");
        request.setStartDate(Date.valueOf("2000-01-02"));
        request.setEndDate(Date.valueOf("2000-01-02"));
        request.setContactPeople(new ArrayList<>());

        String requestAsJson = buildJson(request);

        controller.perform(put("/audit/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateAuditByIdWithFacCritNotExisting_returnsNotFound() throws Exception {
        Audit audit = new Audit();
        audit.setScopesById(new ArrayList<>());
        audit.setAuditContactPeopleById(new ArrayList<>());

        given(auditService.getAuditById(1)).willReturn(audit);
        given(auditService.updateAudit(any())).willReturn(audit);
        given(facCritService.getAllById(anyList())).willThrow(NotFoundException.class);
        given(contactPersonService.getAllByIds(anyList())).willReturn(new ArrayList<>());


        UpdateAuditRequest request = new UpdateAuditRequest();
        request.setAuditName("New Test Name");
        request.setStartDate(Date.valueOf("2000-01-02"));
        request.setEndDate(Date.valueOf("2000-01-02"));
        request.setContactPeople(new ArrayList<>());

        String requestAsJson = buildJson(request);

        controller.perform(put("/audit/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateAuditByIdWithNameIsNull_returns400() throws Exception {
        Audit audit = new Audit();
        audit.setScopesById(new ArrayList<>());
        audit.setAuditContactPeopleById(new ArrayList<>());

        given(auditService.getAuditById(1)).willReturn(audit);
        given(auditService.updateAudit(any())).willReturn(audit);
        given(facCritService.getAllById(anyList())).willReturn(new ArrayList<>());
        given(contactPersonService.getAllByIds(anyList())).willReturn(new ArrayList<>());


        UpdateAuditRequest request = new UpdateAuditRequest();
        request.setStartDate(Date.valueOf("2000-01-02"));
        request.setEndDate(Date.valueOf("2000-01-02"));
        request.setContactPeople(new ArrayList<>());

        String requestAsJson = buildJson(request);

        controller.perform(put("/audit/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateAuditByIdWithNameIsBlank_returns400() throws Exception {
        Audit audit = new Audit();
        audit.setScopesById(new ArrayList<>());
        audit.setAuditContactPeopleById(new ArrayList<>());

        given(auditService.getAuditById(1)).willReturn(audit);
        given(auditService.updateAudit(any())).willReturn(audit);
        given(facCritService.getAllById(anyList())).willReturn(new ArrayList<>());
        given(contactPersonService.getAllByIds(anyList())).willReturn(new ArrayList<>());


        UpdateAuditRequest request = new UpdateAuditRequest();
        request.setAuditName("   ");
        request.setStartDate(Date.valueOf("2000-01-02"));
        request.setEndDate(Date.valueOf("2000-01-02"));
        request.setContactPeople(new ArrayList<>());

        String requestAsJson = buildJson(request);

        controller.perform(put("/audit/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateAuditByIdWithNameIsMaximum_returnsIsOk() throws Exception {
        Audit audit = new Audit();
        audit.setScopesById(new ArrayList<>());
        audit.setAuditContactPeopleById(new ArrayList<>());

        given(auditService.getAuditById(1)).willReturn(audit);
        given(auditService.updateAudit(any())).willReturn(audit);
        given(facCritService.getAllById(anyList())).willReturn(new ArrayList<>());
        given(contactPersonService.getAllByIds(anyList())).willReturn(new ArrayList<>());


        UpdateAuditRequest request = new UpdateAuditRequest();
        request.setAuditName(StringUtils.repeat("*", 45));
        request.setStartDate(Date.valueOf("2000-01-02"));
        request.setEndDate(Date.valueOf("2000-01-02"));
        request.setContactPeople(new ArrayList<>());

        String requestAsJson = buildJson(request);

        controller.perform(put("/audit/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isOk());
    }

    @Test
    public void updateAuditByIdWithNameIsTooLong_returns400() throws Exception {
        Audit audit = new Audit();
        audit.setScopesById(new ArrayList<>());
        audit.setAuditContactPeopleById(new ArrayList<>());

        given(auditService.getAuditById(1)).willReturn(audit);
        given(auditService.updateAudit(any())).willReturn(audit);
        given(facCritService.getAllById(anyList())).willReturn(new ArrayList<>());
        given(contactPersonService.getAllByIds(anyList())).willReturn(new ArrayList<>());


        UpdateAuditRequest request = new UpdateAuditRequest();
        request.setAuditName(StringUtils.repeat("*", 46));
        request.setStartDate(Date.valueOf("2000-01-02"));
        request.setEndDate(Date.valueOf("2000-01-02"));
        request.setContactPeople(new ArrayList<>());

        String requestAsJson = buildJson(request);

        controller.perform(put("/audit/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateAuditByIdWithNameIsMinimum_returnsIsOk() throws Exception {
        Audit audit = new Audit();
        audit.setScopesById(new ArrayList<>());
        audit.setAuditContactPeopleById(new ArrayList<>());

        given(auditService.getAuditById(1)).willReturn(audit);
        given(auditService.updateAudit(any())).willReturn(audit);
        given(facCritService.getAllById(anyList())).willReturn(new ArrayList<>());
        given(contactPersonService.getAllByIds(anyList())).willReturn(new ArrayList<>());


        UpdateAuditRequest request = new UpdateAuditRequest();
        request.setAuditName("aaa");
        request.setStartDate(Date.valueOf("2000-01-02"));
        request.setEndDate(Date.valueOf("2000-01-02"));
        request.setContactPeople(new ArrayList<>());

        String requestAsJson = buildJson(request);

        controller.perform(put("/audit/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isOk());
    }

    @Test
    public void updateAuditByIdWithNameIsTooShort_returns400() throws Exception {
        Audit audit = new Audit();
        audit.setScopesById(new ArrayList<>());
        audit.setAuditContactPeopleById(new ArrayList<>());

        given(auditService.getAuditById(1)).willReturn(audit);
        given(auditService.updateAudit(any())).willReturn(audit);
        given(facCritService.getAllById(anyList())).willReturn(new ArrayList<>());
        given(contactPersonService.getAllByIds(anyList())).willReturn(new ArrayList<>());


        UpdateAuditRequest request = new UpdateAuditRequest();
        request.setAuditName("");
        request.setStartDate(Date.valueOf("2000-01-02"));
        request.setEndDate(Date.valueOf("2000-01-02"));
        request.setContactPeople(new ArrayList<>());

        String requestAsJson = buildJson(request);

        controller.perform(put("/audit/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateAuditByIdWithStartDateIsNull_returns400() throws Exception {
        Audit audit = new Audit();
        audit.setScopesById(new ArrayList<>());
        audit.setAuditContactPeopleById(new ArrayList<>());

        given(auditService.getAuditById(1)).willReturn(audit);
        given(auditService.updateAudit(any())).willReturn(audit);
        given(facCritService.getAllById(anyList())).willReturn(new ArrayList<>());
        given(contactPersonService.getAllByIds(anyList())).willReturn(new ArrayList<>());


        UpdateAuditRequest request = new UpdateAuditRequest();
        request.setAuditName("New Test Name");
        request.setEndDate(Date.valueOf("2000-01-02"));
        request.setContactPeople(new ArrayList<>());

        String requestAsJson = buildJson(request);

        controller.perform(put("/audit/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateAuditByIdWithEndDateIsBeforeStartDate_returns400() throws Exception {
        Audit audit = new Audit();
        audit.setScopesById(new ArrayList<>());
        audit.setAuditContactPeopleById(new ArrayList<>());

        given(auditService.getAuditById(1)).willReturn(audit);
        given(auditService.updateAudit(any())).willReturn(audit);
        given(facCritService.getAllById(anyList())).willReturn(new ArrayList<>());
        given(contactPersonService.getAllByIds(anyList())).willReturn(new ArrayList<>());


        UpdateAuditRequest request = new UpdateAuditRequest();
        request.setAuditName("New Test Name");
        request.setStartDate(Date.valueOf("2000-01-02"));
        request.setEndDate(Date.valueOf("2000-01-01"));
        request.setContactPeople(new ArrayList<>());

        String requestAsJson = buildJson(request);

        controller.perform(put("/audit/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateAuditByIdWithContactPeopleIdIsInvalid_returns400() throws Exception {
        Audit audit = new Audit();
        audit.setScopesById(new ArrayList<>());
        audit.setAuditContactPeopleById(new ArrayList<>());

        given(auditService.getAuditById(1)).willReturn(audit);
        given(auditService.updateAudit(any())).willReturn(audit);
        given(facCritService.getAllById(anyList())).willReturn(new ArrayList<>());
        given(contactPersonService.getAllByIds(anyList())).willReturn(new ArrayList<>());

        List<Integer> contactPeople = new ArrayList<>();
        contactPeople.add(-1);
        UpdateAuditRequest request = new UpdateAuditRequest();
        request.setAuditName("New Test Name");
        request.setStartDate(Date.valueOf("2000-01-02"));
        request.setEndDate(Date.valueOf("2000-01-02"));
        request.setContactPeople(contactPeople);

        String requestAsJson = buildJson(request);

        controller.perform(put("/audit/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateAuditScopeWithValidRequest_returnsOk() throws Exception {
        Audit audit = new Audit();
        audit.setScopesById(new ArrayList<>());
        audit.setAuditContactPeopleById(new ArrayList<>());

        Scope scope = new Scope();
        scope.setRemoved(true);
        scope.setFaccritId(10);

        given(auditService.getAuditById(1)).willReturn(audit);
        given(auditService.updateAudit(any())).willReturn(audit);
        given(facCritService.getAllById(anyList())).willReturn(new ArrayList<>());
        given(facCritService.exists(anyInt())).willReturn(new FacCrit());
        given(contactPersonService.getAllByIds(anyList())).willReturn(new ArrayList<>());
        given(scopeService.updateScopeItem(anyInt(), anyInt(), anyString(), anyBoolean())).willReturn(scope);


        UpdateAuditScopeRequest request = new UpdateAuditScopeRequest();
        request.setFacCritId(10);
        request.setChangeNote("No longer needed");
        request.setRemoved(true);

        String requestAsJson = buildJson(request);

        controller.perform(put("/audit/1/scope")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isOk());
    }

    @Test
    public void updateAuditScopeWithInvalidRequestChangeNoteIsTooLong_returns400() throws Exception {
        Audit audit = new Audit();
        audit.setScopesById(new ArrayList<>());
        audit.setAuditContactPeopleById(new ArrayList<>());

        Scope scope = new Scope();
        scope.setRemoved(true);
        scope.setFaccritId(10);

        given(auditService.getAuditById(1)).willReturn(audit);
        given(auditService.updateAudit(any())).willReturn(audit);
        given(facCritService.getAllById(anyList())).willReturn(new ArrayList<>());
        given(facCritService.exists(anyInt())).willReturn(new FacCrit());
        given(contactPersonService.getAllByIds(anyList())).willReturn(new ArrayList<>());
        given(scopeService.updateScopeItem(anyInt(), anyInt(), anyString(), anyBoolean())).willReturn(scope);


        UpdateAuditScopeRequest request = new UpdateAuditScopeRequest();
        request.setFacCritId(10);
        request.setChangeNote(StringUtils.repeat("*", 257));
        request.setRemoved(true);

        String requestAsJson = buildJson(request);

        controller.perform(put("/audit/1/scope")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateAuditScopeWithInvalidRequestChangeNoteIsMaximum_returns200() throws Exception {
        Audit audit = new Audit();
        audit.setScopesById(new ArrayList<>());
        audit.setAuditContactPeopleById(new ArrayList<>());

        Scope scope = new Scope();
        scope.setRemoved(true);
        scope.setFaccritId(10);

        given(auditService.getAuditById(1)).willReturn(audit);
        given(auditService.updateAudit(any())).willReturn(audit);
        given(facCritService.getAllById(anyList())).willReturn(new ArrayList<>());
        given(facCritService.exists(anyInt())).willReturn(new FacCrit());
        given(contactPersonService.getAllByIds(anyList())).willReturn(new ArrayList<>());
        given(scopeService.updateScopeItem(anyInt(), anyInt(), anyString(), anyBoolean())).willReturn(scope);


        UpdateAuditScopeRequest request = new UpdateAuditScopeRequest();
        request.setFacCritId(10);
        request.setChangeNote(StringUtils.repeat("*", 256));
        request.setRemoved(true);

        String requestAsJson = buildJson(request);

        controller.perform(put("/audit/1/scope")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isOk());
    }

    @Test
    public void updateAuditScopeWithInvalidRequestFacCritIdIsInvalid_returns400() throws Exception {
        Audit audit = new Audit();
        audit.setScopesById(new ArrayList<>());
        audit.setAuditContactPeopleById(new ArrayList<>());

        Scope scope = new Scope();
        scope.setRemoved(true);
        scope.setFaccritId(-1);

        given(auditService.getAuditById(1)).willReturn(audit);
        given(auditService.updateAudit(any())).willReturn(audit);
        given(facCritService.getAllById(anyList())).willReturn(new ArrayList<>());
        given(facCritService.exists(anyInt())).willReturn(new FacCrit());
        given(contactPersonService.getAllByIds(anyList())).willReturn(new ArrayList<>());
        given(scopeService.updateScopeItem(anyInt(), anyInt(), anyString(), anyBoolean())).willReturn(scope);


        UpdateAuditScopeRequest request = new UpdateAuditScopeRequest();
        request.setFacCritId(-1);
        request.setChangeNote(StringUtils.repeat("*", 256));
        request.setRemoved(true);

        String requestAsJson = buildJson(request);

        controller.perform(put("/audit/1/scope")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateAuditScopeWithAuditNotExisting_returnsNotFound() throws Exception {
        Audit audit = new Audit();
        audit.setScopesById(new ArrayList<>());
        audit.setAuditContactPeopleById(new ArrayList<>());

        Scope scope = new Scope();
        scope.setRemoved(true);
        scope.setFaccritId(10);

        given(auditService.getAuditById(1)).willThrow(NotFoundException.class);
        given(auditService.updateAudit(any())).willReturn(audit);
        given(facCritService.getAllById(anyList())).willReturn(new ArrayList<>());
        given(facCritService.exists(anyInt())).willReturn(new FacCrit());
        given(contactPersonService.getAllByIds(anyList())).willReturn(new ArrayList<>());
        given(scopeService.updateScopeItem(anyInt(), anyInt(), anyString(), anyBoolean())).willReturn(scope);


        UpdateAuditScopeRequest request = new UpdateAuditScopeRequest();
        request.setFacCritId(10);
        request.setChangeNote("No longer needed");
        request.setRemoved(true);

        String requestAsJson = buildJson(request);

        controller.perform(put("/audit/1/scope")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateAuditScopeWithFacCritNotExisting_returnsNotFound() throws Exception {
        Audit audit = new Audit();
        audit.setScopesById(new ArrayList<>());
        audit.setAuditContactPeopleById(new ArrayList<>());

        Scope scope = new Scope();
        scope.setRemoved(true);
        scope.setFaccritId(1000);

        given(auditService.getAuditById(1)).willReturn(audit);
        given(auditService.updateAudit(any())).willReturn(audit);
        given(facCritService.getAllById(anyList())).willReturn(new ArrayList<>());
        given(facCritService.exists(anyInt())).willThrow(NotFoundException.class);
        given(contactPersonService.getAllByIds(anyList())).willReturn(new ArrayList<>());
        given(scopeService.updateScopeItem(anyInt(), anyInt(), anyString(), anyBoolean())).willReturn(scope);


        UpdateAuditScopeRequest request = new UpdateAuditScopeRequest();
        request.setFacCritId(1000);
        request.setChangeNote("No longer needed");
        request.setRemoved(true);

        String requestAsJson = buildJson(request);

        controller.perform(put("/audit/1/scope")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateAuditScopeWithScopeItemAlreadyDeleted_returnsIllegalAccess() throws Exception {
        Audit audit = new Audit();
        audit.setScopesById(new ArrayList<>());
        audit.setAuditContactPeopleById(new ArrayList<>());

        Scope scope = new Scope();
        scope.setRemoved(true);
        scope.setFaccritId(1000);

        given(auditService.getAuditById(1)).willReturn(audit);
        given(auditService.updateAudit(any())).willReturn(audit);
        given(facCritService.getAllById(anyList())).willReturn(new ArrayList<>());
        given(facCritService.exists(anyInt())).willReturn(new FacCrit());
        given(contactPersonService.getAllByIds(anyList())).willReturn(new ArrayList<>());
        given(scopeService.updateScopeItem(anyInt(), anyInt(), anyString(), anyBoolean())).willThrow(IllegalAccessException.class);


        UpdateAuditScopeRequest request = new UpdateAuditScopeRequest();
        request.setFacCritId(1000);
        request.setChangeNote("No longer needed");
        request.setRemoved(true);

        String requestAsJson = buildJson(request);

        controller.perform(put("/audit/1/scope")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isForbidden());
    }

    @Test
    public void deleteAuditValidRequest_returnsOK() throws Exception {
        Audit audit = new Audit();
        audit.setStatus(AuditStatus.ACTIVE);
        audit.setScopesById(new ArrayList<>());
        audit.setAuditContactPeopleById(new ArrayList<>());

        given(auditService.getAuditById(1)).willReturn(audit);
        given(auditService.updateAudit(any())).willReturn(audit);
        given(facCritService.getAllById(anyList())).willReturn(new ArrayList<>());
        given(facCritService.exists(anyInt())).willReturn(new FacCrit());
        given(contactPersonService.getAllByIds(anyList())).willReturn(new ArrayList<>());
        given(scopeService.updateScopeItem(anyInt(), anyInt(), anyString(), anyBoolean())).willThrow(IllegalAccessException.class);


        DeleteAuditRequest request = new DeleteAuditRequest();
        request.setContactPerson(1);
        request.setDate(Date.valueOf("2020-06-02"));
        request.setReason("TestReason");

        String requestAsJson = buildJson(request);
        controller.perform(delete("/audit/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isOk());

        Assert.assertEquals(audit.getStatus(), request.getStatus());
        Assert.assertEquals(audit.getCancellationDate(), request.getDate());
        Assert.assertEquals(audit.getCancellationReason(), request.getReason());
        Assert.assertEquals(audit.getCancellationContactPerson(), request.getContactPerson());

    }

    @Test
    public void deleteAuditValidRequestTwice_returnsOK() throws Exception {
        Audit audit = new Audit();
        audit.setStatus(AuditStatus.ACTIVE);
        audit.setScopesById(new ArrayList<>());
        audit.setAuditContactPeopleById(new ArrayList<>());

        given(auditService.getAuditById(1)).willReturn(audit);
        given(auditService.updateAudit(any())).willReturn(audit);
        given(facCritService.getAllById(anyList())).willReturn(new ArrayList<>());
        given(facCritService.exists(anyInt())).willReturn(new FacCrit());
        given(contactPersonService.getAllByIds(anyList())).willReturn(new ArrayList<>());
        given(scopeService.updateScopeItem(anyInt(), anyInt(), anyString(), anyBoolean())).willThrow(IllegalAccessException.class);


        DeleteAuditRequest request = new DeleteAuditRequest();
        request.setContactPerson(1);
        request.setDate(Date.valueOf("2020-06-02"));
        request.setReason("TestReason");

        String requestAsJson = buildJson(request);
        controller.perform(delete("/audit/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isOk());

        Assert.assertEquals(audit.getStatus(), request.getStatus());
        Assert.assertEquals(audit.getCancellationDate(), request.getDate());
        Assert.assertEquals(audit.getCancellationReason(), request.getReason());
        Assert.assertEquals(audit.getCancellationContactPerson(), request.getContactPerson());

        DeleteAuditRequest request2 = new DeleteAuditRequest();
        request2.setContactPerson(2);
        request2.setDate(Date.valueOf("2020-06-03"));
        request2.setReason("TestReason2");

        String requestAsJson2 = buildJson(request2);
        controller.perform(delete("/audit/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson2))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteAuditReasonBlank__returns400() throws Exception {
        DeleteAuditRequest request = new DeleteAuditRequest();
        request.setContactPerson(1);
        request.setDate(Date.valueOf("2020-06-02"));
        request.setReason("");

        String requestAsJson = buildJson(request);
        controller.perform(delete("/audit/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteAuditContactPersonNull_returns400() throws Exception {
        DeleteAuditRequest request = new DeleteAuditRequest();
        request.setDate(Date.valueOf("2020-06-02"));
        request.setReason("TestReason");

        String requestAsJson = buildJson(request);
        controller.perform(delete("/audit/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteAuditContactPersonNegative_returns400() throws Exception {
        DeleteAuditRequest request = new DeleteAuditRequest();
        request.setContactPerson(-1);
        request.setDate(Date.valueOf("2020-06-02"));
        request.setReason("TestReason");

        String requestAsJson = buildJson(request);
        controller.perform(delete("/audit/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteAuditInvalidAuditId_returnsNotFound() throws Exception {
        given(auditService.getAuditById(1)).willThrow(NotFoundException.class);

        DeleteAuditRequest request = new DeleteAuditRequest();
        request.setContactPerson(1);
        request.setDate(Date.valueOf("2020-06-02"));
        request.setReason("TestReason");

        String requestAsJson = buildJson(request);
        controller.perform(delete("/audit/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isNotFound());

    }


    private String buildJson(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        return mapper.writer().withDefaultPrettyPrinter().writeValueAsString(object);
    }
}
