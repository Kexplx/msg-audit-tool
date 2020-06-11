package com.amos2020.javabackend.rest_service;

import com.amos2020.javabackend.rest_service.request.answer.CreateAnswerRequest;
import com.amos2020.javabackend.entity.Answer;
import com.amos2020.javabackend.entity.Interview;
import com.amos2020.javabackend.service.AnswerService;
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

import java.util.ArrayList;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AnswerRestService.class)
public class AnswerRestServiceTest {

    @Autowired
    MockMvc restService;

    @MockBean
    private AnswerService answerService;
    @MockBean
    private InterviewService interviewService;

    @Test
    public void getAnswerByIds_returnsOk() throws Exception {
        Answer answer = new Answer();
        answer.setInterviewId(1);
        answer.setQuestionId(1);
        given(answerService.getAnswerByIds(1, 1)).willReturn(answer);

        restService.perform(get("/answers/interview/1/question/1")).andExpect(status().isOk());
    }

    @Test
    public void getAnswersByInterviewId_returnOK() throws Exception {
        given(answerService.getAnswersByInterviewId(1)).willReturn(new ArrayList<>());

        restService.perform(get("/answers/interview/1")).andExpect(status().isOk());
    }

    @Test
    public void createAnswerByValidRequest_returnOK() throws Exception {
        CreateAnswerRequest request = new CreateAnswerRequest();
        request.setInterviewId(1);
        request.setQuestionId(1);
        given(interviewService.getInterviewById(request.getInterviewId())).willReturn(new Interview());
        given(answerService.createAnswer(request.getQuestionId(), request.getInterviewId())).willReturn(new Answer());

        String requestAsJson = buildJson(request);
        restService.perform(post("/answers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isOk());
    }

    @Test
    public void createAnswerByInvalidQuestionId_return400() throws Exception {
        CreateAnswerRequest request = new CreateAnswerRequest();
        request.setInterviewId(1);

        given(interviewService.getInterviewById(request.getInterviewId())).willReturn(new Interview());
        given(answerService.createAnswer(request.getQuestionId(), request.getInterviewId())).willReturn(new Answer());

        String requestAsJson = buildJson(request);
        restService.perform(post("/answers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createAnswerByInvalidInterviewId_return400() throws Exception {
        CreateAnswerRequest request = new CreateAnswerRequest();
        request.setQuestionId(1);

        given(interviewService.getInterviewById(request.getInterviewId())).willReturn(new Interview());
        given(answerService.createAnswer(request.getQuestionId(), request.getInterviewId())).willReturn(new Answer());

        String requestAsJson = buildJson(request);
        restService.perform(post("/answers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createAnswerByInterviewIdNotFound_return404() throws Exception {
        CreateAnswerRequest request = new CreateAnswerRequest();
        request.setQuestionId(1);
        request.setInterviewId(1);
        given(interviewService.getInterviewById(request.getInterviewId())).willThrow(new NotFoundException(""));
        given(answerService.createAnswer(request.getQuestionId(), request.getInterviewId())).willReturn(new Answer());

        String requestAsJson = buildJson(request);
        restService.perform(post("/answers")
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
