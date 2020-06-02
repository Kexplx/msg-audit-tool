package com.amos2020.javabackend;

import com.amos2020.javabackend.controller.AuditController;
import com.amos2020.javabackend.controller.request.UpdateAuditRequest;
import com.amos2020.javabackend.controller.request.UpdateAuditScopeRequest;
import com.amos2020.javabackend.entity.Audit;
import com.amos2020.javabackend.entity.FacCrit;
import com.amos2020.javabackend.entity.Scope;
import com.amos2020.javabackend.service.*;
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
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
        request.setAuditName("aa");
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
    public void updateAuditByIdWithEndDateIsNull_returns400() throws Exception {
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

    private String buildJson(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        return mapper.writer().withDefaultPrettyPrinter().writeValueAsString(object);
    }
}
