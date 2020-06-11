package com.amos2020.javabackend.rest_service;

import com.amos2020.javabackend.rest_service.request.interview.CreateInterviewRequest;
import com.amos2020.javabackend.entity.Audit;
import com.amos2020.javabackend.entity.Interview;
import com.amos2020.javabackend.entity.InterviewContactPerson;
import com.amos2020.javabackend.service.AuditService;
import com.amos2020.javabackend.service.ContactPersonService;
import com.amos2020.javabackend.service.InterviewContactPersonService;
import com.amos2020.javabackend.service.InterviewService;
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

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(InterviewRestService.class)
public class InterviewRestServiceTest {
    @Autowired
    MockMvc restService;

    @MockBean
    private ContactPersonService contactPersonService;
    @MockBean
    private InterviewService interviewService;
    @MockBean
    private AuditService auditService;
    @MockBean
    private InterviewContactPersonService interviewContactPersonService;

    @Test
    public void getInterviewWithValidId_returnsOk() throws Exception {
        Interview interview = new Interview();
        interview.setAnswersById(new ArrayList<>());
        interview.setInterviewContactPeopleById(new ArrayList<>());
        given(interviewService.getInterviewById(1)).willReturn(interview);
        given(contactPersonService.getAllByIds(anyList())).willReturn(new ArrayList<>());

        restService.perform(get("/interview/1")).andExpect(status().isOk());
    }

    @Test
    public void getInterviewWithInvalidId_returnsNotFound() throws Exception {
        Interview interview = new Interview();
        interview.setAnswersById(new ArrayList<>());
        interview.setInterviewContactPeopleById(new ArrayList<>());
        given(interviewService.getInterviewById(0)).willReturn(interview);
        given(contactPersonService.getAllByIds(anyList())).willReturn(new ArrayList<>());

        restService.perform(get("/interview/0")).andExpect(status().isOk());
    }

    @Test
    public void getAllInterviews() throws Exception {
        Interview interview = new Interview();
        interview.setAnswersById(new ArrayList<>());
        interview.setInterviewContactPeopleById(new ArrayList<>());
        given(interviewService.getInterviewById(anyInt())).willReturn(interview);
        given(contactPersonService.getAllByIds(anyList())).willReturn(new ArrayList<>());

        restService.perform(get("/interview")).andExpect(status().isOk());
    }

    @Test
    public void createInterviewWithValidRequest_returnsOk() throws Exception {
        Interview interview = new Interview();
        interview.setAnswersById(new ArrayList<>());
        interview.setInterviewContactPeopleById(new ArrayList<>());
        given(interviewService.createInterview(anyInt(), any(), any())).willReturn(interview);
        given(contactPersonService.getAllByIds(anyList())).willReturn(new ArrayList<>());
        given(auditService.getAuditById(anyInt())).willReturn(new Audit());
        given(interviewContactPersonService.create(anyInt(), anyInt(), anyString())).willReturn(new InterviewContactPerson());

        CreateInterviewRequest request = new CreateInterviewRequest();
        request.setAuditId(1);
        request.setStartDate(Date.valueOf("2020-05-25"));
        request.setEndDate(Date.valueOf("2020-05-25"));
        request.setInterviewedPeople(new HashMap<>());

        String requestAsJson = buildJson(request);

        restService.perform(post("/interview")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isOk());
    }

    @Test
    public void createInterviewWithAuditIdIsInvalid_returns400() throws Exception {
        Interview interview = new Interview();
        interview.setAnswersById(new ArrayList<>());
        interview.setInterviewContactPeopleById(new ArrayList<>());
        given(interviewService.createInterview(anyInt(), any(), any())).willReturn(interview);
        given(contactPersonService.getAllByIds(anyList())).willReturn(new ArrayList<>());
        given(auditService.getAuditById(anyInt())).willReturn(new Audit());
        given(interviewContactPersonService.create(anyInt(), anyInt(), anyString())).willReturn(new InterviewContactPerson());

        CreateInterviewRequest request = new CreateInterviewRequest();
        request.setAuditId(0);
        request.setStartDate(Date.valueOf("2020-05-25"));
        request.setEndDate(Date.valueOf("2020-05-25"));
        request.setInterviewedPeople(new HashMap<>());

        String requestAsJson = buildJson(request);

        restService.perform(post("/interview")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createInterviewWithAuditIdIsNull_returns400() throws Exception {
        Interview interview = new Interview();
        interview.setAnswersById(new ArrayList<>());
        interview.setInterviewContactPeopleById(new ArrayList<>());
        given(interviewService.createInterview(anyInt(), any(), any())).willReturn(interview);
        given(contactPersonService.getAllByIds(anyList())).willReturn(new ArrayList<>());
        given(auditService.getAuditById(anyInt())).willReturn(new Audit());
        given(interviewContactPersonService.create(anyInt(), anyInt(), anyString())).willReturn(new InterviewContactPerson());

        CreateInterviewRequest request = new CreateInterviewRequest();
        request.setStartDate(Date.valueOf("2020-05-25"));
        request.setEndDate(Date.valueOf("2020-05-25"));
        request.setInterviewedPeople(new HashMap<>());

        String requestAsJson = buildJson(request);

        restService.perform(post("/interview")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createInterviewWithAuditDoesNotExist_returns404() throws Exception {
        Interview interview = new Interview();
        interview.setAnswersById(new ArrayList<>());
        interview.setInterviewContactPeopleById(new ArrayList<>());
        given(interviewService.createInterview(anyInt(), any(), any())).willReturn(interview);
        given(contactPersonService.getAllByIds(anyList())).willReturn(new ArrayList<>());
        given(auditService.getAuditById(anyInt())).willThrow(new NotFoundException(""));
        given(interviewContactPersonService.create(anyInt(), anyInt(), anyString())).willReturn(new InterviewContactPerson());

        CreateInterviewRequest request = new CreateInterviewRequest();
        request.setAuditId(1000);
        request.setStartDate(Date.valueOf("2020-05-25"));
        request.setEndDate(Date.valueOf("2020-05-25"));
        request.setInterviewedPeople(new HashMap<>());

        String requestAsJson = buildJson(request);

        restService.perform(post("/interview")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createInterviewWithStartDateIsNull_returns400() throws Exception {
        Interview interview = new Interview();
        interview.setAnswersById(new ArrayList<>());
        interview.setInterviewContactPeopleById(new ArrayList<>());
        given(interviewService.createInterview(anyInt(), any(), any())).willReturn(interview);
        given(contactPersonService.getAllByIds(anyList())).willReturn(new ArrayList<>());
        given(auditService.getAuditById(anyInt())).willReturn(new Audit());
        given(interviewContactPersonService.create(anyInt(), anyInt(), anyString())).willReturn(new InterviewContactPerson());

        CreateInterviewRequest request = new CreateInterviewRequest();
        request.setAuditId(1);
        request.setStartDate(null);
        request.setEndDate(Date.valueOf("2020-05-25"));
        request.setInterviewedPeople(new HashMap<>());

        String requestAsJson = buildJson(request);

        restService.perform(post("/interview")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createInterviewWithStartDateIsAfterEndDate_returns400() throws Exception {
        Interview interview = new Interview();
        interview.setAnswersById(new ArrayList<>());
        interview.setInterviewContactPeopleById(new ArrayList<>());
        given(interviewService.createInterview(anyInt(), any(), any())).willReturn(interview);
        given(contactPersonService.getAllByIds(anyList())).willReturn(new ArrayList<>());
        given(auditService.getAuditById(anyInt())).willReturn(new Audit());
        given(interviewContactPersonService.create(anyInt(), anyInt(), anyString())).willReturn(new InterviewContactPerson());

        CreateInterviewRequest request = new CreateInterviewRequest();
        request.setAuditId(1);
        request.setStartDate(Date.valueOf("2020-05-26"));
        request.setEndDate(Date.valueOf("2020-05-25"));
        request.setInterviewedPeople(new HashMap<>());

        String requestAsJson = buildJson(request);

        restService.perform(post("/interview")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createInterviewWithContactPeopleIdIsInvalid_returns400() throws Exception {
        Interview interview = new Interview();
        interview.setAnswersById(new ArrayList<>());
        interview.setInterviewContactPeopleById(new ArrayList<>());
        given(interviewService.createInterview(anyInt(), any(), any())).willReturn(interview);
        given(contactPersonService.getAllByIds(anyList())).willReturn(new ArrayList<>());
        given(auditService.getAuditById(anyInt())).willReturn(new Audit());
        given(interviewContactPersonService.create(anyInt(), anyInt(), anyString())).willReturn(new InterviewContactPerson());

        HashMap<Integer, String> map = new HashMap<>();
        map.put(-1, "role");

        CreateInterviewRequest request = new CreateInterviewRequest();
        request.setAuditId(1);
        request.setStartDate(Date.valueOf("2020-05-25"));
        request.setEndDate(Date.valueOf("2020-05-25"));
        request.setInterviewedPeople(map);

        String requestAsJson = buildJson(request);

        restService.perform(post("/interview")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createInterviewWithContactPersonNotExisting_returns404() throws Exception {
        Interview interview = new Interview();
        interview.setAnswersById(new ArrayList<>());
        interview.setInterviewContactPeopleById(new ArrayList<>());
        given(interviewService.createInterview(anyInt(), any(), any())).willReturn(interview);
        given(contactPersonService.getAllByIds(anyList())).willThrow(new NotFoundException(""));
        given(auditService.getAuditById(anyInt())).willReturn(new Audit());
        given(interviewContactPersonService.create(anyInt(), anyInt(), anyString())).willReturn(new InterviewContactPerson());

        HashMap<Integer, String> map = new HashMap<>();
        map.put(100000, "role");

        CreateInterviewRequest request = new CreateInterviewRequest();
        request.setAuditId(1);
        request.setStartDate(Date.valueOf("2020-05-16"));
        request.setEndDate(Date.valueOf("2020-05-25"));
        request.setInterviewedPeople(map);

        String requestAsJson = buildJson(request);

        restService.perform(post("/interview")
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
