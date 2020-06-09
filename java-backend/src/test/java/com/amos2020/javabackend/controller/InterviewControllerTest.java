package com.amos2020.javabackend.controller;

import com.amos2020.javabackend.entity.Interview;
import com.amos2020.javabackend.service.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(InterviewController.class)
public class InterviewControllerTest {
    @Autowired
    MockMvc controller;

    @MockBean
    private ContactPersonService contactPersonService;
    @MockBean
    private InterviewService interviewService;

    @Test
    public void getInterviewWithValidId_returnsOk() throws Exception {
        Interview interview = new Interview();
        interview.setAnswersById(new ArrayList<>());
        interview.setInterviewContactPeopleById(new ArrayList<>());
        given(interviewService.getInterviewById(1)).willReturn(interview);
        given(contactPersonService.getAllByIds(anyList())).willReturn(new ArrayList<>());

        controller.perform(get("/interview/1")).andExpect(status().isOk());
    }

    @Test
    public void getInterviewWithInvalidId_returnsNotFound() throws Exception {
        Interview interview = new Interview();
        interview.setAnswersById(new ArrayList<>());
        interview.setInterviewContactPeopleById(new ArrayList<>());
        given(interviewService.getInterviewById(0)).willReturn(interview);
        given(contactPersonService.getAllByIds(anyList())).willReturn(new ArrayList<>());

        controller.perform(get("/interview/0")).andExpect(status().isOk());
    }
}
