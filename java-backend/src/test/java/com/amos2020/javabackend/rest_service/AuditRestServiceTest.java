package com.amos2020.javabackend.rest_service;

import com.amos2020.javabackend.entity.Audit;
import com.amos2020.javabackend.entity.AuditStatus;
import com.amos2020.javabackend.entity.Scope;
import com.amos2020.javabackend.rest_service.controller.AuditController;
import com.amos2020.javabackend.rest_service.request.audit.CreateAuditRequest;
import com.amos2020.javabackend.rest_service.request.audit.DeleteAuditRequest;
import com.amos2020.javabackend.rest_service.request.audit.UpdateAuditRequest;
import com.amos2020.javabackend.rest_service.request.audit.UpdateAuditScopeRequest;
import com.amos2020.javabackend.rest_service.response.BasicAuditResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import javassist.NotFoundException;
import org.apache.commons.lang3.StringUtils;
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
@WebMvcTest(AuditRestService.class)
public class AuditRestServiceTest {

    @Autowired
    MockMvc restService;

    @MockBean
    private AuditController auditController;

    @Test
    public void createAuditWithValidRequest_returnsOk() throws Exception {
        String auditName = "TestAuditName";
        Date startDate = Date.valueOf("2000-01-02");
        Date endDate = Date.valueOf("2000-01-03");
        List<Integer> scopes = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> contacts = Arrays.asList(1, 2, 3, 4, 5);

        CreateAuditRequest request = new CreateAuditRequest();
        request.setName(auditName);
        request.setStartDate(startDate);
        request.setEndDate(endDate);
        request.setScope(scopes);
        request.setContactPersons(contacts);
        String requestAsJson = buildJson(request);

        Audit audit = new Audit();
        audit.setName(auditName);
        audit.setStartDate(startDate);
        audit.setCreationDate(Timestamp.from(Instant.now()));
        audit.setStatus(AuditStatus.OPEN);
        if (endDate != null) {
            audit.setEndDate(endDate);
        }

        given(auditController.createAudit(request.getName(), request.getStartDate(), request.getEndDate(), request.getScope(), request.getContactPersons())).willReturn(new BasicAuditResponse(audit, new ArrayList<>(), new ArrayList<>()));

        restService.perform(post("/audits")
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
        request.setContactPersons(contacts);
        String requestAsJson = buildJson(request);

        Audit audit = new Audit();
        audit.setStartDate(startDate);
        audit.setCreationDate(Timestamp.from(Instant.now()));
        audit.setStatus(AuditStatus.OPEN);
        if (endDate != null) {
            audit.setEndDate(endDate);
        }

        given(auditController.createAudit(request.getName(), request.getStartDate(), request.getEndDate(), request.getScope(), request.getContactPersons())).willReturn(new BasicAuditResponse(audit, new ArrayList<>(), new ArrayList<>()));

        restService.perform(post("/audits")
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
        request.setName(auditName);
        request.setStartDate(startDate);
        request.setEndDate(endDate);
        request.setScope(scopes);
        request.setContactPersons(contacts);
        String requestAsJson = buildJson(request);

        Audit audit = new Audit();
        audit.setName(auditName);
        audit.setStartDate(startDate);
        audit.setCreationDate(Timestamp.from(Instant.now()));
        audit.setStatus(AuditStatus.OPEN);
        if (endDate != null) {
            audit.setEndDate(endDate);
        }

        given(auditController.createAudit(request.getName(), request.getStartDate(), request.getEndDate(), request.getScope(), request.getContactPersons())).willReturn(new BasicAuditResponse(audit, new ArrayList<>(), new ArrayList<>()));

        restService.perform(post("/audits")
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
        request.setName(auditName);
        request.setStartDate(startDate);
        request.setEndDate(endDate);
        request.setScope(scopes);
        request.setContactPersons(contacts);
        String requestAsJson = buildJson(request);

        Audit audit = new Audit();
        audit.setName(auditName);
        audit.setStartDate(startDate);
        audit.setCreationDate(Timestamp.from(Instant.now()));
        audit.setStatus(AuditStatus.OPEN);
        if (endDate != null) {
            audit.setEndDate(endDate);
        }

        given(auditController.createAudit(request.getName(), request.getStartDate(), request.getEndDate(), request.getScope(), request.getContactPersons())).willReturn(new BasicAuditResponse(audit, new ArrayList<>(), new ArrayList<>()));

        restService.perform(post("/audits")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createAuditWithAuditNameTooLong_returns400() throws Exception {
        char[] charArray = new char[257];
        Arrays.fill(charArray, 't');
        String auditName = new String(charArray);
        Date startDate = Date.valueOf("2000-01-02");
        Date endDate = Date.valueOf("2000-01-03");
        List<Integer> scopes = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> contacts = Arrays.asList(1, 2, 3, 4, 5);

        CreateAuditRequest request = new CreateAuditRequest();
        request.setName(auditName);
        request.setStartDate(startDate);
        request.setEndDate(endDate);
        request.setScope(scopes);
        request.setContactPersons(contacts);
        String requestAsJson = buildJson(request);

        Audit audit = new Audit();
        audit.setName(auditName);
        audit.setStartDate(startDate);
        audit.setCreationDate(Timestamp.from(Instant.now()));
        audit.setStatus(AuditStatus.OPEN);
        if (endDate != null) {
            audit.setEndDate(endDate);
        }

        given(auditController.createAudit(request.getName(), request.getStartDate(), request.getEndDate(), request.getScope(), request.getContactPersons())).willReturn(new BasicAuditResponse(audit, new ArrayList<>(), new ArrayList<>()));

        restService.perform(post("/audits")
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
        request.setName(auditName);
        request.setEndDate(endDate);
        request.setScope(scopes);
        request.setContactPersons(contacts);
        String requestAsJson = buildJson(request);

        Audit audit = new Audit();
        audit.setName(auditName);
        audit.setCreationDate(Timestamp.from(Instant.now()));
        audit.setStatus(AuditStatus.OPEN);
        audit.setEndDate(endDate);


        given(auditController.createAudit(request.getName(), request.getStartDate(), request.getEndDate(), request.getScope(), request.getContactPersons())).willReturn(new BasicAuditResponse(audit, new ArrayList<>(), new ArrayList<>()));

        restService.perform(post("/audits")
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
        request.setName(auditName);
        request.setStartDate(startDate);
        request.setScope(scopes);
        request.setContactPersons(contacts);
        String requestAsJson = buildJson(request);

        Audit audit = new Audit();
        audit.setName(auditName);
        audit.setStartDate(startDate);
        audit.setCreationDate(Timestamp.from(Instant.now()));
        audit.setStatus(AuditStatus.OPEN);


        given(auditController.createAudit(request.getName(), request.getStartDate(), request.getEndDate(), request.getScope(), request.getContactPersons())).willReturn(new BasicAuditResponse(audit, new ArrayList<>(), new ArrayList<>()));

        restService.perform(post("/audits")
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
        request.setName(auditName);
        request.setStartDate(startDate);
        request.setEndDate(endDate);
        request.setScope(scopes);
        request.setContactPersons(contacts);
        String requestAsJson = buildJson(request);

        Audit audit = new Audit();
        audit.setName(auditName);
        audit.setStartDate(startDate);
        audit.setCreationDate(Timestamp.from(Instant.now()));
        audit.setStatus(AuditStatus.OPEN);
        if (endDate != null) {
            audit.setEndDate(endDate);
        }

        given(auditController.createAudit(request.getName(), request.getStartDate(), request.getEndDate(), request.getScope(), request.getContactPersons())).willReturn(new BasicAuditResponse(audit, new ArrayList<>(), new ArrayList<>()));

        restService.perform(post("/audits")
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
        request.setName(auditName);
        request.setStartDate(startDate);
        request.setEndDate(endDate);
        request.setContactPersons(contacts);
        String requestAsJson = buildJson(request);

        Audit audit = new Audit();
        audit.setName(auditName);
        audit.setStartDate(startDate);
        audit.setCreationDate(Timestamp.from(Instant.now()));
        audit.setStatus(AuditStatus.OPEN);
        if (endDate != null) {
            audit.setEndDate(endDate);
        }

        given(auditController.createAudit(request.getName(), request.getStartDate(), request.getEndDate(), request.getScope(), request.getContactPersons())).willReturn(new BasicAuditResponse(audit, new ArrayList<>(), new ArrayList<>()));

        restService.perform(post("/audits")
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
        request.setName(auditName);
        request.setStartDate(startDate);
        request.setEndDate(endDate);
        request.setScope(scopes);
        request.setContactPersons(contacts);
        String requestAsJson = buildJson(request);

        Audit audit = new Audit();
        audit.setName(auditName);
        audit.setStartDate(startDate);
        audit.setCreationDate(Timestamp.from(Instant.now()));
        audit.setStatus(AuditStatus.OPEN);
        if (endDate != null) {
            audit.setEndDate(endDate);
        }

        given(auditController.createAudit(request.getName(), request.getStartDate(), request.getEndDate(), request.getScope(), request.getContactPersons())).willReturn(new BasicAuditResponse(audit, new ArrayList<>(), new ArrayList<>()));

        restService.perform(post("/audits")
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
        request.setName(auditName);
        request.setStartDate(startDate);
        request.setEndDate(endDate);
        request.setScope(scopes);
        request.setContactPersons(contacts);
        String requestAsJson = buildJson(request);

        Audit audit = new Audit();
        audit.setName(auditName);
        audit.setStartDate(startDate);
        audit.setCreationDate(Timestamp.from(Instant.now()));
        audit.setStatus(AuditStatus.OPEN);
        if (endDate != null) {
            audit.setEndDate(endDate);
        }

        given(auditController.createAudit(request.getName(), request.getStartDate(), request.getEndDate(), request.getScope(), request.getContactPersons())).willReturn(new BasicAuditResponse(audit, new ArrayList<>(), new ArrayList<>()));

        restService.perform(post("/audits")
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
        request.setName(auditName);
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

        given(auditController.createAudit(request.getName(), request.getStartDate(), request.getEndDate(), request.getScope(), request.getContactPersons())).willReturn(new BasicAuditResponse(audit, new ArrayList<>(), new ArrayList<>()));

        restService.perform(post("/audits")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isOk());
    }

    @Test
    public void getAuditByIdWithValidId_returnsOk() throws Exception {
        Audit audit = new Audit();
        given(auditController.getAuditById(anyInt())).willReturn(new BasicAuditResponse(audit, new ArrayList<>(), new ArrayList<>()));
        restService.perform(get("/audits/1")).andExpect(status().isOk());
    }

    @Test
    public void getAuditByIdWithInvalidId_returnsNotFound() throws Exception {
        given(auditController.getAuditById(0)).willThrow(new NotFoundException(""));
        restService.perform(get("/audits/0")).andExpect(status().isBadRequest());
    }

    @Test
    public void updateAuditByIdWithValidRequest_returnsOk() throws Exception {
        Audit audit = new Audit();
        given(auditController.updateAudit(anyInt(), anyString(), any(), any(), any())).willReturn(new BasicAuditResponse(audit, new ArrayList<>(), new ArrayList<>()));

        UpdateAuditRequest request = new UpdateAuditRequest();
        request.setName("New Test Name");
        request.setStartDate(Date.valueOf("2000-01-02"));
        request.setEndDate(Date.valueOf("2000-01-02"));
        request.setStatus(AuditStatus.ACTIVE);

        String requestAsJson = buildJson(request);

        restService.perform(put("/audits/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isOk());
    }

    @Test
    public void updateAuditByIdWithAuditNotExisting_returnsNotFound() throws Exception {
        given(auditController.updateAudit(anyInt(), anyString(), any(), any(), any())).willThrow(NotFoundException.class);

        UpdateAuditRequest request = new UpdateAuditRequest();
        request.setName("New Test Name");
        request.setStartDate(Date.valueOf("2000-01-02"));
        request.setEndDate(Date.valueOf("2000-01-02"));
        request.setStatus(AuditStatus.ACTIVE);

        String requestAsJson = buildJson(request);

        restService.perform(put("/audits/1000")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateAuditByIdWithNameIsNull_returns400() throws Exception {
        Audit audit = new Audit();
        given(auditController.updateAudit(anyInt(), anyString(), any(), any(), any())).willReturn(new BasicAuditResponse(audit, new ArrayList<>(), new ArrayList<>()));


        UpdateAuditRequest request = new UpdateAuditRequest();
        request.setStartDate(Date.valueOf("2000-01-02"));
        request.setEndDate(Date.valueOf("2000-01-02"));
        request.setStatus(AuditStatus.ACTIVE);

        String requestAsJson = buildJson(request);

        restService.perform(put("/audits/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateAuditByIdWithNameIsBlank_returns400() throws Exception {
        Audit audit = new Audit();
        given(auditController.updateAudit(anyInt(), anyString(), any(), any(), any())).willReturn(new BasicAuditResponse(audit, new ArrayList<>(), new ArrayList<>()));

        UpdateAuditRequest request = new UpdateAuditRequest();
        request.setName("   ");
        request.setStartDate(Date.valueOf("2000-01-02"));
        request.setEndDate(Date.valueOf("2000-01-02"));
        request.setStatus(AuditStatus.ACTIVE);

        String requestAsJson = buildJson(request);

        restService.perform(put("/audits/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateAuditByIdWithNameIsMaximum_returnsIsOk() throws Exception {
        Audit audit = new Audit();
        given(auditController.updateAudit(anyInt(), anyString(), any(), any(), any())).willReturn(new BasicAuditResponse(audit, new ArrayList<>(), new ArrayList<>()));

        UpdateAuditRequest request = new UpdateAuditRequest();
        request.setName(StringUtils.repeat("*", 256));
        request.setStartDate(Date.valueOf("2000-01-02"));
        request.setEndDate(Date.valueOf("2000-01-02"));
        request.setStatus(AuditStatus.ACTIVE);

        String requestAsJson = buildJson(request);

        restService.perform(put("/audits/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isOk());
    }

    @Test
    public void updateAuditByIdWithNameIsTooLong_returns400() throws Exception {
        Audit audit = new Audit();
        given(auditController.updateAudit(anyInt(), anyString(), any(), any(), any())).willReturn(new BasicAuditResponse(audit, new ArrayList<>(), new ArrayList<>()));

        UpdateAuditRequest request = new UpdateAuditRequest();
        request.setName(StringUtils.repeat("*", 257));
        request.setStartDate(Date.valueOf("2000-01-02"));
        request.setEndDate(Date.valueOf("2000-01-02"));
        request.setStatus(AuditStatus.ACTIVE);

        String requestAsJson = buildJson(request);

        restService.perform(put("/audits/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateAuditByIdWithNameIsMinimum_returnsIsOk() throws Exception {
        Audit audit = new Audit();
        given(auditController.updateAudit(anyInt(), anyString(), any(), any(), any())).willReturn(new BasicAuditResponse(audit, new ArrayList<>(), new ArrayList<>()));

        UpdateAuditRequest request = new UpdateAuditRequest();
        request.setName("*");
        request.setStartDate(Date.valueOf("2000-01-02"));
        request.setEndDate(Date.valueOf("2000-01-02"));
        request.setStatus(AuditStatus.ACTIVE);

        String requestAsJson = buildJson(request);

        restService.perform(put("/audits/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isOk());
    }

    @Test
    public void updateAuditByIdWithNameIsTooShort_returns400() throws Exception {
        Audit audit = new Audit();
        given(auditController.updateAudit(anyInt(), anyString(), any(), any(), any())).willReturn(new BasicAuditResponse(audit, new ArrayList<>(), new ArrayList<>()));

        UpdateAuditRequest request = new UpdateAuditRequest();
        request.setName("");
        request.setStartDate(Date.valueOf("2000-01-02"));
        request.setEndDate(Date.valueOf("2000-01-02"));
        request.setStatus(AuditStatus.ACTIVE);

        String requestAsJson = buildJson(request);

        restService.perform(put("/audits/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateAuditByIdWithStartDateIsNull_returns400() throws Exception {
        Audit audit = new Audit();
        given(auditController.updateAudit(anyInt(), anyString(), any(), any(), any())).willReturn(new BasicAuditResponse(audit, new ArrayList<>(), new ArrayList<>()));

        UpdateAuditRequest request = new UpdateAuditRequest();
        request.setName("New Test Name");
        request.setEndDate(Date.valueOf("2000-01-02"));
        request.setStatus(AuditStatus.ACTIVE);

        String requestAsJson = buildJson(request);

        restService.perform(put("/audits/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateAuditByIdWithEndDateIsBeforeStartDate_returns400() throws Exception {
        Audit audit = new Audit();
        given(auditController.updateAudit(anyInt(), anyString(), any(), any(), any())).willReturn(new BasicAuditResponse(audit, new ArrayList<>(), new ArrayList<>()));

        UpdateAuditRequest request = new UpdateAuditRequest();
        request.setName("New Test Name");
        request.setStartDate(Date.valueOf("2000-01-02"));
        request.setEndDate(Date.valueOf("2000-01-01"));
        request.setStatus(AuditStatus.ACTIVE);

        String requestAsJson = buildJson(request);

        restService.perform(put("/audits/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateAuditByIdWithAuditStatusIsNull_returns400() throws Exception {
        Audit audit = new Audit();
        given(auditController.updateAudit(anyInt(), anyString(), any(), any(), any())).willReturn(new BasicAuditResponse(audit, new ArrayList<>(), new ArrayList<>()));

        UpdateAuditRequest request = new UpdateAuditRequest();
        request.setName("New Test Name");
        request.setStartDate(Date.valueOf("2000-01-02"));
        request.setEndDate(Date.valueOf("2000-01-02"));
        request.setStatus(null);

        String requestAsJson = buildJson(request);

        restService.perform(put("/audits/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addContactPersonToAuditWithValidIds_returns200() throws Exception {
        Audit audit = new Audit();
        audit.setScopesById(new ArrayList<>());
        audit.setAuditContactPeopleById(new ArrayList<>());

        given(auditController.addContactPersonToAudit(anyInt(), anyInt())).willReturn(new BasicAuditResponse(audit, new ArrayList<>(), new ArrayList<>()));
        restService.perform(put("/audits/1/contactpersons/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void addContactPersonToAuditWithInvalidAuditId_returns404() throws Exception {
        Audit audit = new Audit();
        audit.setScopesById(new ArrayList<>());
        audit.setAuditContactPeopleById(new ArrayList<>());

        given(auditController.addContactPersonToAudit(eq(1000), anyInt())).willThrow(NotFoundException.class);
        restService.perform(put("/audits/1000/contactpersons/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void addContactPersonToAuditWithInvalidContactPersonId_returns404() throws Exception {
        Audit audit = new Audit();
        audit.setScopesById(new ArrayList<>());
        audit.setAuditContactPeopleById(new ArrayList<>());

        given(auditController.addContactPersonToAudit(anyInt(), eq(1000))).willThrow(NotFoundException.class);
        restService.perform(put("/audits/1/contactpersons/1000"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void removeContactPersonFromAuditWithValidIds_returns200() throws Exception {
        Audit audit = new Audit();
        audit.setScopesById(new ArrayList<>());
        audit.setAuditContactPeopleById(new ArrayList<>());

        given(auditController.removeContactPersonFromAudit(anyInt(), anyInt())).willReturn(new BasicAuditResponse(audit, new ArrayList<>(), new ArrayList<>()));
        restService.perform(delete("/audits/1/contactpersons/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void removeContactPersonFromAuditWithInvalidAuditId_returns404() throws Exception {
        Audit audit = new Audit();
        audit.setScopesById(new ArrayList<>());
        audit.setAuditContactPeopleById(new ArrayList<>());

        given(auditController.removeContactPersonFromAudit(eq(1000), anyInt())).willThrow(NotFoundException.class);
        restService.perform(delete("/audits/1000/contactpersons/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void removeContactPersonFromAuditWithInvalidContactPersonId_returns404() throws Exception {
        Audit audit = new Audit();
        audit.setScopesById(new ArrayList<>());
        audit.setAuditContactPeopleById(new ArrayList<>());

        given(auditController.removeContactPersonFromAudit(anyInt(), eq(1000))).willThrow(NotFoundException.class);
        restService.perform(delete("/audits/1/contactpersons/1000"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateAuditScopeWithValidRequest_returnsOk() throws Exception {
        Audit audit = new Audit();
        given(auditController.updateAuditScope(anyInt(), anyInt(), anyString(), anyBoolean(), anyString())).willReturn(new BasicAuditResponse(audit, new ArrayList<>(), new ArrayList<>()));

        UpdateAuditScopeRequest request = new UpdateAuditScopeRequest();
        request.setFacCritId(10);
        request.setChangeNote("No longer needed");
        request.setRemoved(true);
        request.setNote("");

        String requestAsJson = buildJson(request);

        restService.perform(put("/audits/1/scope")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isOk());
    }

    @Test
    public void updateAuditScopeWithInvalidRequestChangeNoteIsTooLong_returns400() throws Exception {
        Audit audit = new Audit();
        Scope scope = new Scope();
        scope.setRemoved(true);
        scope.setFaccritId(10);
        scope.setNote("");

        given(auditController.updateAuditScope(anyInt(), anyInt(), anyString(), anyBoolean(), anyString())).willReturn(new BasicAuditResponse(audit, new ArrayList<>(), new ArrayList<>()));

        UpdateAuditScopeRequest request = new UpdateAuditScopeRequest();
        request.setFacCritId(10);
        request.setChangeNote(StringUtils.repeat("*", 1025));
        request.setRemoved(true);
        request.setNote("");

        String requestAsJson = buildJson(request);

        restService.perform(put("/audits/1/scope")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateAuditScopeWithInvalidRequestChangeNoteIsMaximum_returns200() throws Exception {
        Audit audit = new Audit();

        Scope scope = new Scope();
        scope.setRemoved(true);
        scope.setFaccritId(10);
        scope.setNote("");
        given(auditController.updateAuditScope(anyInt(), anyInt(), anyString(), anyBoolean(), anyString())).willReturn(new BasicAuditResponse(audit, new ArrayList<>(), new ArrayList<>()));

        UpdateAuditScopeRequest request = new UpdateAuditScopeRequest();
        request.setFacCritId(10);
        request.setChangeNote(StringUtils.repeat("*", 1024));
        request.setRemoved(true);
        request.setNote("");

        String requestAsJson = buildJson(request);

        restService.perform(put("/audits/1/scope")
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
        given(auditController.updateAuditScope(anyInt(), anyInt(), anyString(), anyBoolean(), anyString())).willReturn(new BasicAuditResponse(audit, new ArrayList<>(), new ArrayList<>()));


        UpdateAuditScopeRequest request = new UpdateAuditScopeRequest();
        request.setFacCritId(-1);
        request.setChangeNote(StringUtils.repeat("*", 1024));
        request.setRemoved(true);

        String requestAsJson = buildJson(request);

        restService.perform(put("/audits/1/scope")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateAuditScopeWithAuditNotExisting_returnsNotFound() throws Exception {
        Scope scope = new Scope();
        scope.setRemoved(true);
        scope.setFaccritId(10);
        scope.setNote("");

        given(auditController.updateAuditScope(anyInt(), anyInt(), anyString(), anyBoolean(), anyString())).willThrow(NotFoundException.class);

        UpdateAuditScopeRequest request = new UpdateAuditScopeRequest();
        request.setFacCritId(10);
        request.setChangeNote("No longer needed");
        request.setRemoved(true);
        request.setNote("");


        String requestAsJson = buildJson(request);

        restService.perform(put("/audits/1/scope")
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
        scope.setNote("1000");

        given(auditController.updateAuditScope(anyInt(), anyInt(), anyString(), anyBoolean(), anyString())).willThrow(NotFoundException.class);


        UpdateAuditScopeRequest request = new UpdateAuditScopeRequest();
        request.setFacCritId(1000);
        request.setChangeNote("No longer needed");
        request.setRemoved(true);
        request.setNote("");

        String requestAsJson = buildJson(request);

        restService.perform(put("/audits/1/scope")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateAuditScopeWithScopeItemAlreadyDeleted_returnsIllegalAccess() throws Exception {
        Scope scope = new Scope();
        scope.setRemoved(true);
        scope.setFaccritId(1000);
        scope.setNote("");
        given(auditController.updateAuditScope(anyInt(), anyInt(), anyString(), anyBoolean(), anyString())).willThrow(IllegalAccessException.class);

        UpdateAuditScopeRequest request = new UpdateAuditScopeRequest();
        request.setFacCritId(1000);
        request.setChangeNote("No longer needed");
        request.setRemoved(true);
        request.setNote("");

        String requestAsJson = buildJson(request);

        restService.perform(put("/audits/1/scope")
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
        given(auditController.softDeleteAudit(anyInt(), any(), anyString(), anyInt())).willReturn(new BasicAuditResponse(audit, new ArrayList<>(), new ArrayList<>()));


        DeleteAuditRequest request = new DeleteAuditRequest();
        request.setContactPerson(1);
        request.setDate(Date.valueOf("2020-06-02"));
        request.setReason("TestReason");

        String requestAsJson = buildJson(request);
        restService.perform(delete("/audits/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteAuditReasonBlank__returns400() throws Exception {
        DeleteAuditRequest request = new DeleteAuditRequest();
        request.setContactPerson(1);
        request.setDate(Date.valueOf("2020-06-02"));
        request.setReason("");

        String requestAsJson = buildJson(request);
        restService.perform(delete("/audits/1")
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
        restService.perform(delete("/audits/1")
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
        restService.perform(delete("/audits/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteAuditInvalidAuditId_returnsNotFound() throws Exception {
        given(auditController.softDeleteAudit(anyInt(), any(), anyString(), anyInt())).willThrow(NotFoundException.class);

        DeleteAuditRequest request = new DeleteAuditRequest();
        request.setContactPerson(1);
        request.setDate(Date.valueOf("2020-06-02"));
        request.setReason("TestReason");

        String requestAsJson = buildJson(request);
        restService.perform(delete("/audits/1")
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
