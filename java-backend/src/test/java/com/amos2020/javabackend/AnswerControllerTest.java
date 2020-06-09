package com.amos2020.javabackend;

import com.amos2020.javabackend.controller.AnswerController;
import com.amos2020.javabackend.entity.Answer;
import com.amos2020.javabackend.service.AnswerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.*;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AnswerController.class)
public class AnswerControllerTest {

    @Autowired
    MockMvc controller;

    @MockBean
    private AnswerService  answerService;

    @Test
    public void getAnswerByIds_returnsOk() throws Exception{
        Answer answer = new Answer();
        answer.setInterviewId(1);
        answer.setQuestionId(1);
        given(answerService.getAnswerByIds(1,1)).willReturn(answer);

        controller.perform(get("/answer/interview/1/question/1")).andExpect(status().isOk());
    }

    @Test
    public void getAnswersByInterviewId_returnOK() throws Exception{
        given(answerService.getAnswersByInterviewId(1)).willReturn(new ArrayList<>());

        controller.perform(get("/answer/interview/1")).andExpect(status().isOk());
    }


}
