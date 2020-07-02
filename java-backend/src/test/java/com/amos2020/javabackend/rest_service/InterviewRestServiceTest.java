package com.amos2020.javabackend.rest_service;

import com.amos2020.javabackend.entity.Interview;
import com.amos2020.javabackend.entity.InterviewStatus;
import com.amos2020.javabackend.rest_service.controller.InterviewController;
import com.amos2020.javabackend.rest_service.request.interview.CreateInterviewRequest;
import com.amos2020.javabackend.rest_service.request.interview.InterviewAddContactPersonRequest;
import com.amos2020.javabackend.rest_service.request.interview.InterviewPerson;
import com.amos2020.javabackend.rest_service.request.interview.UpdateInterviewRequest;
import com.amos2020.javabackend.rest_service.response.BasicInterviewResponse;
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

        restService.perform(get("/interviews/0")).andExpect(status().isBadRequest());
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
        given(interviewController.createInterview(anyInt(), any(), any(), anyString(), any(), anyList())).willReturn(new BasicInterviewResponse(interview, new ArrayList<>()));

        CreateInterviewRequest request = new CreateInterviewRequest();
        request.setAuditId(1);
        request.setStartDate(Date.valueOf("2020-05-25"));
        request.setEndDate(Date.valueOf("2020-05-25"));
        request.setGoal("testGoal");
        request.setInterviewedContactPersons(new ArrayList<>());
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
        given(interviewController.createInterview(anyInt(), any(), any(), anyString(), any(), anyList())).willReturn(new BasicInterviewResponse(interview, new ArrayList<>()));


        CreateInterviewRequest request = new CreateInterviewRequest();
        request.setAuditId(0);
        request.setStartDate(Date.valueOf("2020-05-25"));
        request.setEndDate(Date.valueOf("2020-05-25"));
        request.setInterviewedContactPersons(new ArrayList<>());
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
        given(interviewController.createInterview(anyInt(), any(), any(), anyString(), any(), anyList())).willReturn(new BasicInterviewResponse(interview, new ArrayList<>()));


        CreateInterviewRequest request = new CreateInterviewRequest();
        request.setStartDate(Date.valueOf("2020-05-25"));
        request.setEndDate(Date.valueOf("2020-05-25"));
        request.setInterviewedContactPersons(new ArrayList<>());
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
        given(interviewController.createInterview(anyInt(), any(), any(), anyString(), any(), anyList())).willThrow(NotFoundException.class);


        CreateInterviewRequest request = new CreateInterviewRequest();
        request.setAuditId(1000);
        request.setStartDate(Date.valueOf("2020-05-25"));
        request.setEndDate(Date.valueOf("2020-05-25"));
        request.setInterviewedContactPersons(new ArrayList<>());
        request.setGoal("testGoal");
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
        given(interviewController.createInterview(anyInt(), any(), any(), anyString(), any(), anyList())).willReturn(new BasicInterviewResponse(interview, new ArrayList<>()));


        CreateInterviewRequest request = new CreateInterviewRequest();
        request.setAuditId(1);
        request.setStartDate(null);
        request.setEndDate(Date.valueOf("2020-05-25"));
        request.setGoal("testGoal");
        request.setInterviewedContactPersons(new ArrayList<>());
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
        given(interviewController.createInterview(anyInt(), any(), any(), anyString(), any(), anyList())).willReturn(new BasicInterviewResponse(interview, new ArrayList<>()));


        CreateInterviewRequest request = new CreateInterviewRequest();
        request.setAuditId(1);
        request.setStartDate(Date.valueOf("2020-05-26"));
        request.setEndDate(Date.valueOf("2020-05-25"));
        request.setGoal("testGoal");
        request.setInterviewedContactPersons(new ArrayList<>());
        request.setInterviewScope(new ArrayList<>());

        String requestAsJson = buildJson(request);

        restService.perform(post("/interviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createInterviewWithContactPersonIdIsInvalid_returns400() throws Exception {
        Interview interview = new Interview();
        interview.setAnswersById(new ArrayList<>());
        interview.setInterviewContactPeopleById(new ArrayList<>());
        given(interviewController.createInterview(anyInt(), any(), any(), anyString(), any(), anyList())).willReturn(new BasicInterviewResponse(interview, new ArrayList<>()));


        List<InterviewPerson> map = new ArrayList<>();
        map.add(new InterviewPerson(-1, "Role"));

        CreateInterviewRequest request = new CreateInterviewRequest();
        request.setAuditId(1);
        request.setStartDate(Date.valueOf("2020-05-25"));
        request.setEndDate(Date.valueOf("2020-05-25"));
        request.setGoal("testGoal");
        request.setInterviewedContactPersons(map);
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
        given(interviewController.createInterview(anyInt(), any(), any(), anyString(), any(), anyList())).willThrow(NotFoundException.class);


        List<InterviewPerson> map = new ArrayList<>();
        map.add(new InterviewPerson(100000, "Role"));

        CreateInterviewRequest request = new CreateInterviewRequest();
        request.setAuditId(1);
        request.setStartDate(Date.valueOf("2020-05-16"));
        request.setEndDate(Date.valueOf("2020-05-25"));
        request.setGoal("testGoal");
        request.setInterviewedContactPersons(map);
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
        given(interviewController.createInterview(anyInt(), any(), any(), anyString(), any(), anyList())).willReturn(new BasicInterviewResponse(interview, new ArrayList<>()));

        CreateInterviewRequest request = new CreateInterviewRequest();
        request.setAuditId(1);
        request.setStartDate(Date.valueOf("2020-05-25"));
        request.setEndDate(Date.valueOf("2020-05-25"));
        request.setGoal("testGoal");
        request.setInterviewedContactPersons(new ArrayList<>());
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
    public void createInterviewWithGoalIsNull_returns400() throws Exception {
        Interview interview = new Interview();
        interview.setAnswersById(new ArrayList<>());
        interview.setInterviewContactPeopleById(new ArrayList<>());
        given(interviewController.createInterview(anyInt(), any(), any(), anyString(), any(), anyList())).willReturn(new BasicInterviewResponse(interview, new ArrayList<>()));

        CreateInterviewRequest request = new CreateInterviewRequest();
        request.setAuditId(1);
        request.setStartDate(Date.valueOf("2020-05-25"));
        request.setEndDate(Date.valueOf("2020-05-25"));
        request.setGoal(null);
        request.setInterviewedContactPersons(new ArrayList<>());
        request.setInterviewScope(new ArrayList<>());

        String requestAsJson = buildJson(request);

        restService.perform(post("/interviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createInterviewWithGoalIsTooLong_returns400() throws Exception {
        Interview interview = new Interview();
        interview.setAnswersById(new ArrayList<>());
        interview.setInterviewContactPeopleById(new ArrayList<>());
        given(interviewController.createInterview(anyInt(), any(), any(), anyString(), any(), anyList())).willReturn(new BasicInterviewResponse(interview, new ArrayList<>()));

        CreateInterviewRequest request = new CreateInterviewRequest();
        request.setAuditId(1);
        request.setStartDate(Date.valueOf("2020-05-25"));
        request.setEndDate(Date.valueOf("2020-05-25"));
        request.setGoal(StringUtils.repeat("*", 1025));
        request.setInterviewedContactPersons(new ArrayList<>());
        request.setInterviewScope(new ArrayList<>());

        String requestAsJson = buildJson(request);

        restService.perform(post("/interviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createInterviewWithGoalIsMaximum_returns200() throws Exception {
        Interview interview = new Interview();
        interview.setAnswersById(new ArrayList<>());
        interview.setInterviewContactPeopleById(new ArrayList<>());
        given(interviewController.createInterview(anyInt(), any(), any(), anyString(), any(), anyList())).willReturn(new BasicInterviewResponse(interview, new ArrayList<>()));

        CreateInterviewRequest request = new CreateInterviewRequest();
        request.setAuditId(1);
        request.setStartDate(Date.valueOf("2020-05-25"));
        request.setEndDate(Date.valueOf("2020-05-25"));
        request.setGoal(StringUtils.repeat("*", 1024));
        request.setInterviewedContactPersons(new ArrayList<>());
        request.setInterviewScope(new ArrayList<>());

        String requestAsJson = buildJson(request);

        restService.perform(post("/interviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isOk());
    }

    @Test
    public void updateInterviewWithValidRequest_returns200() throws Exception {
        Interview interview = new Interview();
        interview.setAnswersById(new ArrayList<>());
        interview.setInterviewContactPeopleById(new ArrayList<>());
        given(interviewController.updateInterview(anyInt(), any(), any(), any(), anyString())).willReturn(new BasicInterviewResponse(interview, new ArrayList<>()));

        UpdateInterviewRequest request = new UpdateInterviewRequest();
        request.setStartDate(Date.valueOf("2020-05-25"));
        request.setEndDate(Date.valueOf("2020-05-25"));
        request.setStatus(InterviewStatus.ACTIVE);
        request.setGoal("testgoal");

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
        given(interviewController.updateInterview(anyInt(), any(), any(), any(), anyString())).willReturn(new BasicInterviewResponse(interview, new ArrayList<>()));

        UpdateInterviewRequest request = new UpdateInterviewRequest();
        request.setEndDate(Date.valueOf("2020-05-25"));
        request.setStatus(InterviewStatus.ACTIVE);
        request.setGoal("testgoal");

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
        given(interviewController.updateInterview(anyInt(), any(), any(), any(), anyString())).willReturn(new BasicInterviewResponse(interview, new ArrayList<>()));

        UpdateInterviewRequest request = new UpdateInterviewRequest();
        request.setStartDate(Date.valueOf("2020-05-26"));
        request.setEndDate(Date.valueOf("2020-05-25"));
        request.setStatus(InterviewStatus.ACTIVE);
        request.setGoal("testgoal");

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
        given(interviewController.updateInterview(anyInt(), any(), any(), any(), anyString())).willReturn(new BasicInterviewResponse(interview, new ArrayList<>()));

        UpdateInterviewRequest request = new UpdateInterviewRequest();
        request.setStartDate(Date.valueOf("2020-05-25"));
        request.setEndDate(Date.valueOf("2020-05-25"));
        request.setGoal("testgoal");

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
        given(interviewController.updateInterview(eq(10000), any(), any(), any(), anyString())).willThrow(NotFoundException.class);

        UpdateInterviewRequest request = new UpdateInterviewRequest();
        request.setStartDate(Date.valueOf("2020-05-25"));
        request.setEndDate(Date.valueOf("2020-05-25"));
        request.setStatus(InterviewStatus.ACTIVE);
        request.setGoal("testgoal");

        String requestAsJson = buildJson(request);

        restService.perform(put("/interviews/10000")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateInterviewWithGoalIsNull_returns400() throws Exception {
        Interview interview = new Interview();
        interview.setAnswersById(new ArrayList<>());
        interview.setInterviewContactPeopleById(new ArrayList<>());
        given(interviewController.updateInterview(anyInt(), any(), any(), any(), anyString())).willReturn(new BasicInterviewResponse(interview, new ArrayList<>()));

        UpdateInterviewRequest request = new UpdateInterviewRequest();
        request.setStartDate(Date.valueOf("2020-05-25"));
        request.setEndDate(Date.valueOf("2020-05-25"));
        request.setStatus(InterviewStatus.ACTIVE);
        request.setGoal(null);

        String requestAsJson = buildJson(request);

        restService.perform(put("/interviews/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateInterviewWithGoalIsTooLong_returns400() throws Exception {
        Interview interview = new Interview();
        interview.setAnswersById(new ArrayList<>());
        interview.setInterviewContactPeopleById(new ArrayList<>());
        given(interviewController.updateInterview(anyInt(), any(), any(), any(), anyString())).willReturn(new BasicInterviewResponse(interview, new ArrayList<>()));

        UpdateInterviewRequest request = new UpdateInterviewRequest();
        request.setStartDate(Date.valueOf("2020-05-25"));
        request.setEndDate(Date.valueOf("2020-05-25"));
        request.setStatus(InterviewStatus.ACTIVE);
        request.setGoal(StringUtils.repeat("*", 1025));

        String requestAsJson = buildJson(request);

        restService.perform(put("/interviews/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateInterviewWithGoalIsMaximum_returns200() throws Exception {
        Interview interview = new Interview();
        interview.setAnswersById(new ArrayList<>());
        interview.setInterviewContactPeopleById(new ArrayList<>());
        given(interviewController.updateInterview(anyInt(), any(), any(), any(), anyString())).willReturn(new BasicInterviewResponse(interview, new ArrayList<>()));

        UpdateInterviewRequest request = new UpdateInterviewRequest();
        request.setStartDate(Date.valueOf("2020-05-25"));
        request.setEndDate(Date.valueOf("2020-05-25"));
        request.setStatus(InterviewStatus.ACTIVE);
        request.setGoal(StringUtils.repeat("*", 1024));

        String requestAsJson = buildJson(request);

        restService.perform(put("/interviews/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isOk());
    }

    @Test
    public void addContactPersonToInterviewWithValidRequest_returns200() throws Exception {
        Interview interview = new Interview();
        interview.setAnswersById(new ArrayList<>());
        interview.setInterviewContactPeopleById(new ArrayList<>());
        given(interviewController.addContactPersonToInterview(anyInt(), anyInt(), anyString())).willReturn(new BasicInterviewResponse(interview, new ArrayList<>()));

        InterviewAddContactPersonRequest request = new InterviewAddContactPersonRequest();
        request.setContactPersonId(1);
        request.setRole("New role");

        String requestAsJson = buildJson(request);

        restService.perform(put("/interviews/1/add/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isOk());
    }

    @Test
    public void addContactPersonToInterviewWithInvalidInterviewId_returns400() throws Exception {
        Interview interview = new Interview();
        interview.setAnswersById(new ArrayList<>());
        interview.setInterviewContactPeopleById(new ArrayList<>());
        given(interviewController.addContactPersonToInterview(anyInt(), anyInt(), anyString())).willReturn(new BasicInterviewResponse(interview, new ArrayList<>()));

        InterviewAddContactPersonRequest request = new InterviewAddContactPersonRequest();
        request.setContactPersonId(1);
        request.setRole("New role");

        String requestAsJson = buildJson(request);

        restService.perform(put("/interviews/0/add/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addContactPersonToInterviewWithInvalidContactPersonId_returns400() throws Exception {
        Interview interview = new Interview();
        interview.setAnswersById(new ArrayList<>());
        interview.setInterviewContactPeopleById(new ArrayList<>());
        given(interviewController.addContactPersonToInterview(anyInt(), anyInt(), anyString())).willReturn(new BasicInterviewResponse(interview, new ArrayList<>()));

        InterviewAddContactPersonRequest request = new InterviewAddContactPersonRequest();
        request.setContactPersonId(0);
        request.setRole("New role");

        String requestAsJson = buildJson(request);

        restService.perform(put("/interviews/1/add/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addContactPersonToInterviewWithContactPersonIdIsNull_returns400() throws Exception {
        Interview interview = new Interview();
        interview.setAnswersById(new ArrayList<>());
        interview.setInterviewContactPeopleById(new ArrayList<>());
        given(interviewController.addContactPersonToInterview(anyInt(), anyInt(), anyString())).willReturn(new BasicInterviewResponse(interview, new ArrayList<>()));

        InterviewAddContactPersonRequest request = new InterviewAddContactPersonRequest();
        request.setRole("New role");

        String requestAsJson = buildJson(request);

        restService.perform(put("/interviews/1/add/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addContactPersonToInterviewWithRoleIsNull_returns400() throws Exception {
        Interview interview = new Interview();
        interview.setAnswersById(new ArrayList<>());
        interview.setInterviewContactPeopleById(new ArrayList<>());
        given(interviewController.addContactPersonToInterview(anyInt(), anyInt(), anyString())).willReturn(new BasicInterviewResponse(interview, new ArrayList<>()));

        InterviewAddContactPersonRequest request = new InterviewAddContactPersonRequest();
        request.setContactPersonId(1);

        String requestAsJson = buildJson(request);

        restService.perform(put("/interviews/1/add/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addContactPersonToInterviewWithRoleIsBlank_returns400() throws Exception {
        Interview interview = new Interview();
        interview.setAnswersById(new ArrayList<>());
        interview.setInterviewContactPeopleById(new ArrayList<>());
        given(interviewController.addContactPersonToInterview(anyInt(), anyInt(), anyString())).willReturn(new BasicInterviewResponse(interview, new ArrayList<>()));

        InterviewAddContactPersonRequest request = new InterviewAddContactPersonRequest();
        request.setContactPersonId(1);
        request.setRole("     ");
        String requestAsJson = buildJson(request);

        restService.perform(put("/interviews/1/add/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addContactPersonToInterviewWithRoleIsToLong_returns400() throws Exception {
        Interview interview = new Interview();
        interview.setAnswersById(new ArrayList<>());
        interview.setInterviewContactPeopleById(new ArrayList<>());
        given(interviewController.addContactPersonToInterview(anyInt(), anyInt(), anyString())).willReturn(new BasicInterviewResponse(interview, new ArrayList<>()));

        InterviewAddContactPersonRequest request = new InterviewAddContactPersonRequest();
        request.setContactPersonId(1);
        request.setRole(StringUtils.repeat("*", 257));
        String requestAsJson = buildJson(request);

        restService.perform(put("/interviews/1/add/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addContactPersonToInterviewWithRoleIsToMaximum_returns200() throws Exception {
        Interview interview = new Interview();
        interview.setAnswersById(new ArrayList<>());
        interview.setInterviewContactPeopleById(new ArrayList<>());
        given(interviewController.addContactPersonToInterview(anyInt(), anyInt(), anyString())).willReturn(new BasicInterviewResponse(interview, new ArrayList<>()));

        InterviewAddContactPersonRequest request = new InterviewAddContactPersonRequest();
        request.setContactPersonId(1);
        request.setRole(StringUtils.repeat("*", 256));
        String requestAsJson = buildJson(request);

        restService.perform(put("/interviews/1/add/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isOk());
    }

    @Test
    public void addContactPersonToInterviewWithInterviewNotExisting_returns404() throws Exception {
        Interview interview = new Interview();
        interview.setAnswersById(new ArrayList<>());
        interview.setInterviewContactPeopleById(new ArrayList<>());
        given(interviewController.addContactPersonToInterview(eq(10000), anyInt(), anyString())).willThrow(NotFoundException.class);

        InterviewAddContactPersonRequest request = new InterviewAddContactPersonRequest();
        request.setContactPersonId(1);
        request.setRole("test Role");
        String requestAsJson = buildJson(request);

        restService.perform(put("/interviews/10000/add/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isNotFound());
    }

    @Test
    public void addContactPersonToInterviewWithContactPersonNotExisting_returns404() throws Exception {
        Interview interview = new Interview();
        interview.setAnswersById(new ArrayList<>());
        interview.setInterviewContactPeopleById(new ArrayList<>());
        given(interviewController.addContactPersonToInterview(anyInt(), eq(100), anyString())).willThrow(NotFoundException.class);

        InterviewAddContactPersonRequest request = new InterviewAddContactPersonRequest();
        request.setContactPersonId(100);
        request.setRole("test Role");
        String requestAsJson = buildJson(request);

        restService.perform(put("/interviews/1/add/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isNotFound());
    }

    @Test
    public void removeContactPersonFromInterviewWithValidParameters_returns200() throws Exception {
        Interview interview = new Interview();
        interview.setAnswersById(new ArrayList<>());
        interview.setInterviewContactPeopleById(new ArrayList<>());
        given(interviewController.removeContactPersonFromInterview(anyInt(), anyInt())).willReturn(new BasicInterviewResponse(interview, new ArrayList<>()));

        restService.perform(delete("/interviews/1/delete/person/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void removeContactPersonFromInterviewWithInvalidInterviewId_returns404() throws Exception {
        Interview interview = new Interview();
        interview.setAnswersById(new ArrayList<>());
        interview.setInterviewContactPeopleById(new ArrayList<>());
        given(interviewController.removeContactPersonFromInterview(anyInt(), anyInt())).willThrow(NotFoundException.class);

        restService.perform(delete("/interviews/1/delete/person/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void removeContactPersonFromInterviewWithInvalidContactPersonId_returns404() throws Exception {
        Interview interview = new Interview();
        interview.setAnswersById(new ArrayList<>());
        interview.setInterviewContactPeopleById(new ArrayList<>());
        given(interviewController.removeContactPersonFromInterview(anyInt(), anyInt())).willThrow(NotFoundException.class);

        restService.perform(delete("/interviews/1/delete/person/1"))
                .andExpect(status().isNotFound());
    }

    private String buildJson(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        return mapper.writer().withDefaultPrettyPrinter().writeValueAsString(object);
    }
}
