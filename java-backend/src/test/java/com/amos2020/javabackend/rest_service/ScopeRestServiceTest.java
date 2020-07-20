package com.amos2020.javabackend.rest_service;

import com.amos2020.javabackend.entity.Audit;
import com.amos2020.javabackend.rest_service.controller.AuditController;
import com.amos2020.javabackend.rest_service.controller.ScopeController;
import com.amos2020.javabackend.rest_service.request.scope.AddScopeRequest;
import com.amos2020.javabackend.rest_service.request.scope.UpdateScopeRequest;
import com.amos2020.javabackend.rest_service.response.BasicAuditResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import javassist.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ScopeRestService.class)

public class ScopeRestServiceTest {

    @Autowired
    MockMvc restService;
    @MockBean
    private ScopeController scopeController;
    @MockBean
    private AuditController auditController;

    @Test
    public void addValidScope_returnsOk() throws Exception {
        AddScopeRequest request = new AddScopeRequest();
        List<Integer> scope = Arrays.asList(1, 2, 3, 4, 5);
        request.setScope(scope);
        String requestAsJson = buildJson(request);
        Audit audit = new Audit();
        given(auditController.updateAudit(anyInt(), anyString(), any(), any(), any())).willReturn(new BasicAuditResponse(audit, new ArrayList<>(), new ArrayList<>()));

        restService.perform(post("/audits/1/scope")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isOk());
    }

    @Test
    public void addInvalidAuditId_returnsNotFound() throws Exception {
        AddScopeRequest request = new AddScopeRequest();
        List<Integer> scope = Arrays.asList(1, 2, 3, 4, 5);
        request.setScope(scope);
        String requestAsJson = buildJson(request);

        given(scopeController.addScope(anyInt(), anyList())).willThrow(NotFoundException.class);

        restService.perform(post("/audits/1000/scope")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isNotFound());
    }

    @Test
    public void addInvalidScope_returns400() throws Exception {
        AddScopeRequest request = new AddScopeRequest();
        List<Integer> scope = Arrays.asList(1, 2, 3, -4, 5);
        request.setScope(scope);
        String requestAsJson = buildJson(request);
        Audit audit = new Audit();
        given(auditController.updateAudit(anyInt(), anyString(), any(), any(), any())).willReturn(new BasicAuditResponse(audit, new ArrayList<>(), new ArrayList<>()));

        restService.perform(post("/audits/1/scope")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateValid_returnsOK() throws Exception {
        UpdateScopeRequest request = new UpdateScopeRequest();
        request.setRemoved(false);
        request.setChange_note("");
        request.setNote("Test");
        String requestAsJson = buildJson(request);

        restService.perform(put("/audits/1/scope/5")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isOk());
    }

    @Test
    public void updateWithInvalidAuditId_returnsNotFound() throws Exception {
        UpdateScopeRequest request = new UpdateScopeRequest();
        request.setRemoved(false);
        request.setChange_note("");
        request.setNote("Test");
        String requestAsJson = buildJson(request);

        given(scopeController.updateScope(anyInt(), anyInt(), anyBoolean(), anyString(), anyString())).willThrow(NotFoundException.class);

        restService.perform(put("/audits/100/scope/5")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateWithInvalidFacCritId_returnsNotFound() throws Exception {
        UpdateScopeRequest request = new UpdateScopeRequest();
        request.setRemoved(false);
        request.setChange_note("");
        request.setNote("Test");
        String requestAsJson = buildJson(request);

        given(scopeController.updateScope(anyInt(), anyInt(), anyBoolean(), anyString(), anyString())).willThrow(NotFoundException.class);

        restService.perform(put("/audits/100/scope/5")
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
