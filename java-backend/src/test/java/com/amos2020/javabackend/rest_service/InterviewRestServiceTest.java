package com.amos2020.javabackend.rest_service;

import com.amos2020.javabackend.entity.Interview;
import com.amos2020.javabackend.entity.InterviewStatus;
import com.amos2020.javabackend.rest_service.controller.InterviewController;
import com.amos2020.javabackend.rest_service.request.interview.CreateInterviewRequest;
import com.amos2020.javabackend.rest_service.request.interview.UpdateInterviewRequest;
import com.amos2020.javabackend.rest_service.response.BasicInterviewResponse;
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
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(InterviewRestService.class)
public class InterviewRestServiceTest {
    @Autowired
    MockMvc restService;

    @MockBean
    private InterviewController interviewController;

    @Test
    public void getInterviewWithValidId_returnsOk() throws Exception {
        Interview interview = new Interview();
        interview.setAnswersById(new ArrayList<>());
        interview.setInterviewContactPeopleById(new ArrayList<>());
        given(interviewController.getInterviewById(anyInt())).willReturn(new BasicInterviewResponse(interview, new ArrayList<>()));

        restService.perform(get("/interviews/1")).andExpect(status().isOk());
    }

    @Test
    public void getInterviewWithInvalidId_returnsNotFound() throws Exception {
        Interview interview = new Interview();
        interview.setAnswersById(new ArrayList<>());
        interview.setInterviewContactPeopleById(new ArrayList<>());
        given(interviewController.getInterviewById(anyInt())).willReturn(new BasicInterviewResponse(interview, new ArrayList<>()));

        restService.perform(get("/interviews/0")).andExpect(status().isOk());
    }

    @Test
    public void getAllInterviews() throws Exception {
        Interview interview = new Interview();
        interview.setAnswersById(new ArrayList<>());
        interview.setInterviewContactPeopleById(new ArrayList<>());
        List<BasicInterviewResponse> list = new ArrayList<>();
        list.add(new BasicInterviewResponse(interview, new ArrayList<>()));
        given(interviewController.getAllInterviews()).willReturn(list);

        restService.perform(get("/interviews")).andExpect(status().isOk());
    }

    @Test
    public void createInterviewWithValidRequest_returnsOk() throws Exception {
        Interview interview = new Interview();
        interview.setAnswersById(new ArrayList<>());
        interview.setInterviewContactPeopleById(new ArrayList<>());
        given(interviewController.createInterview(anyInt(), any(), any(), any(), anyList())).willReturn(new BasicInterviewResponse(interview, new ArrayList<>()));

        CreateInterviewRequest request = new CreateInterviewRequest();
        request.setAuditId(1);
        request.setStartDate(Date.valueOf("2020-05-25"));
        request.setEndDate(Date.valueOf("2020-05-25"));
        request.setInterviewedPeople(new HashMap<>());
        request.setInterviewScope(new ArrayList<>());

        String requestAsJson = buildJson(request);

        restService.perform(post("/interviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isOk());
    }

    @Test
    public void createInterviewWithAuditIdIsInvalid_returns400() throws Exception {
        Interview interview = new Interview();
        interview.setAnswersById(new ArrayList<>());
        interview.setInterviewContactPeopleById(new ArrayList<>());
        given(interviewController.createInterview(anyInt(), any(), any(), any(), anyList())).willReturn(new BasicInterviewResponse(interview, new ArrayList<>()));


        CreateInterviewRequest request = new CreateInterviewRequest();
        request.setAuditId(0);
        request.setStartDate(Date.valueOf("2020-05-25"));
        request.setEndDate(Date.valueOf("2020-05-25"));
        request.setInterviewedPeople(new HashMap<>());
        request.setInterviewScope(new ArrayList<>());

        String requestAsJson = buildJson(request);

        restService.perform(post("/interviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createInterviewWithAuditIdIsNull_returns400() throws Exception {
        Interview interview = new Interview();
        interview.setAnswersById(new ArrayList<>());
        interview.setInterviewContactPeopleById(new ArrayList<>());
        given(interviewController.createInterview(anyInt(), any(), any(), any(), anyList())).willReturn(new BasicInterviewResponse(interview, new ArrayList<>()));


        CreateInterviewRequest request = new CreateInterviewRequest();
        request.setStartDate(Date.valueOf("2020-05-25"));
        request.setEndDate(Date.valueOf("2020-05-25"));
        request.setInterviewedPeople(new HashMap<>());
        request.setInterviewScope(new ArrayList<>());

        String requestAsJson = buildJson(request);

        restService.perform(post("/interviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createInterviewWithAuditDoesNotExist_returns404() throws Exception {
        Interview interview = new Interview();
        interview.setAnswersById(new ArrayList<>());
        interview.setInterviewContactPeopleById(new ArrayList<>());
        given(interviewController.createInterview(anyInt(), any(), any(), any(), anyList())).willThrow(NotFoundException.class);


        CreateInterviewRequest request = new CreateInterviewRequest();
        request.setAuditId(1000);
        request.setStartDate(Date.valueOf("2020-05-25"));
        request.setEndDate(Date.valueOf("2020-05-25"));
        request.setInterviewedPeople(new HashMap<>());
        request.setInterviewScope(new ArrayList<>());

        String requestAsJson = buildJson(request);

        restService.perform(post("/interviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createInterviewWithStartDateIsNull_returns400() throws Exception {
        Interview interview = new Interview();
        interview.setAnswersById(new ArrayList<>());
        interview.setInterviewContactPeopleById(new ArrayList<>());
        given(interviewController.createInterview(anyInt(), any(), any(), any(), anyList())).willReturn(new BasicInterviewResponse(interview, new ArrayList<>()));


        CreateInterviewRequest request = new CreateInterviewRequest();
        request.setAuditId(1);
        request.setStartDate(null);
        request.setEndDate(Date.valueOf("2020-05-25"));
        request.setInterviewedPeople(new HashMap<>());
        request.setInterviewScope(new ArrayList<>());

        String requestAsJson = buildJson(request);

        restService.perform(post("/interviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createInterviewWithStartDateIsAfterEndDate_returns400() throws Exception {
        Interview interview = new Interview();
        interview.setAnswersById(new ArrayList<>());
        interview.setInterviewContactPeopleById(new ArrayList<>());
        given(interviewController.createInterview(anyInt(), any(), any(), any(), anyList())).willReturn(new BasicInterviewResponse(interview, new ArrayList<>()));


        CreateInterviewRequest request = new CreateInterviewRequest();
        request.setAuditId(1);
        request.setStartDate(Date.valueOf("2020-05-26"));
        request.setEndDate(Date.valueOf("2020-05-25"));
        request.setInterviewedPeople(new HashMap<>());
        request.setInterviewScope(new ArrayList<>());

        String requestAsJson = buildJson(request);

        restService.perform(post("/interviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createInterviewWithContactPeopleIdIsInvalid_returns400() throws Exception {
        Interview interview = new Interview();
        interview.setAnswersById(new ArrayList<>());
        interview.setInterviewContactPeopleById(new ArrayList<>());
        given(interviewController.createInterview(anyInt(), any(), any(), any(), anyList())).willReturn(new BasicInterviewResponse(interview, new ArrayList<>()));


        HashMap<Integer, String> map = new HashMap<>();
        map.put(-1, "role");

        CreateInterviewRequest request = new CreateInterviewRequest();
        request.setAuditId(1);
        request.setStartDate(Date.valueOf("2020-05-25"));
        request.setEndDate(Date.valueOf("2020-05-25"));
        request.setInterviewedPeople(map);
        request.setInterviewScope(new ArrayList<>());

        String requestAsJson = buildJson(request);

        restService.perform(post("/interviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createInterviewWithContactPersonNotExisting_returns404() throws Exception {
        Interview interview = new Interview();
        interview.setAnswersById(new ArrayList<>());
        interview.setInterviewContactPeopleById(new ArrayList<>());
        given(interviewController.createInterview(anyInt(), any(), any(), any(), anyList())).willThrow(NotFoundException.class);


        HashMap<Integer, String> map = new HashMap<>();
        map.put(100000, "role");

        CreateInterviewRequest request = new CreateInterviewRequest();
        request.setAuditId(1);
        request.setStartDate(Date.valueOf("2020-05-16"));
        request.setEndDate(Date.valueOf("2020-05-25"));
        request.setInterviewedPeople(map);
        request.setInterviewScope(new ArrayList<>());

        String requestAsJson = buildJson(request);

        restService.perform(post("/interviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createInterviewWithFaccritNotValid_returns400() throws Exception {
        Interview interview = new Interview();
        interview.setAnswersById(new ArrayList<>());
        interview.setInterviewContactPeopleById(new ArrayList<>());
        given(interviewController.createInterview(anyInt(), any(), any(), any(), anyList())).willReturn(new BasicInterviewResponse(interview, new ArrayList<>()));

        CreateInterviewRequest request = new CreateInterviewRequest();
        request.setAuditId(1);
        request.setStartDate(Date.valueOf("2020-05-25"));
        request.setEndDate(Date.valueOf("2020-05-25"));
        request.setInterviewedPeople(new HashMap<>());
        ArrayList<Integer> scope = new ArrayList<>();
        scope.add(0);
        request.setInterviewScope(scope);

        String requestAsJson = buildJson(request);

        restService.perform(post("/interviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void updateInterviewWithValidRequest_returns200() throws Exception {
        Interview interview = new Interview();
        interview.setAnswersById(new ArrayList<>());
        interview.setInterviewContactPeopleById(new ArrayList<>());
        given(interviewController.updateInterview(anyInt(), any(), any(), any())).willReturn(new BasicInterviewResponse(interview, new ArrayList<>()));

        UpdateInterviewRequest request = new UpdateInterviewRequest();
        request.setStartDate(Date.valueOf("2020-05-25"));
        request.setEndDate(Date.valueOf("2020-05-25"));
        request.setStatus(InterviewStatus.ACTIVE);

        String requestAsJson = buildJson(request);

        restService.perform(put("/interviews/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isOk());
    }

    @Test
    public void updateInterviewWithStartDateIsNull_returns400() throws Exception {
        Interview interview = new Interview();
        interview.setAnswersById(new ArrayList<>());
        interview.setInterviewContactPeopleById(new ArrayList<>());
        given(interviewController.updateInterview(anyInt(), any(), any(), any())).willReturn(new BasicInterviewResponse(interview, new ArrayList<>()));

        UpdateInterviewRequest request = new UpdateInterviewRequest();
        request.setEndDate(Date.valueOf("2020-05-25"));
        request.setStatus(InterviewStatus.ACTIVE);

        String requestAsJson = buildJson(request);

        restService.perform(put("/interviews/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateInterviewWithEndDateIsBeforeStartDateIsNull_returns400() throws Exception {
        Interview interview = new Interview();
        interview.setAnswersById(new ArrayList<>());
        interview.setInterviewContactPeopleById(new ArrayList<>());
        given(interviewController.updateInterview(anyInt(), any(), any(), any())).willReturn(new BasicInterviewResponse(interview, new ArrayList<>()));

        UpdateInterviewRequest request = new UpdateInterviewRequest();
        request.setStartDate(Date.valueOf("2020-05-26"));
        request.setEndDate(Date.valueOf("2020-05-25"));
        request.setStatus(InterviewStatus.ACTIVE);

        String requestAsJson = buildJson(request);

        restService.perform(put("/interviews/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateInterviewWithInterviewStatusIsNull_returns400() throws Exception {
        Interview interview = new Interview();
        interview.setAnswersById(new ArrayList<>());
        interview.setInterviewContactPeopleById(new ArrayList<>());
        given(interviewController.updateInterview(anyInt(), any(), any(), any())).willReturn(new BasicInterviewResponse(interview, new ArrayList<>()));

        UpdateInterviewRequest request = new UpdateInterviewRequest();
        request.setStartDate(Date.valueOf("2020-05-25"));
        request.setEndDate(Date.valueOf("2020-05-25"));

        String requestAsJson = buildJson(request);

        restService.perform(put("/interviews/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateInterviewWithNoInterviewExistingForInterviewId_returns404() throws Exception {
        Interview interview = new Interview();
        interview.setAnswersById(new ArrayList<>());
        interview.setInterviewContactPeopleById(new ArrayList<>());
        given(interviewController.updateInterview(eq(10000), any(), any(), any())).willThrow(NotFoundException.class);

        UpdateInterviewRequest request = new UpdateInterviewRequest();
        request.setStartDate(Date.valueOf("2020-05-25"));
        request.setEndDate(Date.valueOf("2020-05-25"));
        request.setStatus(InterviewStatus.ACTIVE);

        String requestAsJson = buildJson(request);

        restService.perform(put("/interviews/10000")
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
