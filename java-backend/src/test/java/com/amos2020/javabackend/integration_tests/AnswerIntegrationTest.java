package com.amos2020.javabackend.integration_tests;

import com.amos2020.javabackend.JavaBackendApplication;
import com.amos2020.javabackend.entity.*;
import com.amos2020.javabackend.repository.*;
import com.amos2020.javabackend.rest_service.request.answer.CreateAnswerRequest;
import com.amos2020.javabackend.rest_service.request.answer.UpdateAnswerRequest;
import com.amos2020.javabackend.rest_service.response.BasicAnswerResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = JavaBackendApplication.class
)
public class AnswerIntegrationTest {

    @Autowired
    InterviewRepository interviewRepository;
    @Autowired
    FacCritRepository facCritRepository;
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    AuditRepository auditRepository;
    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    private TestRestTemplate testRestTemplate;
    private Interview interview;
    private Question question;
    private FacCrit facCrit;

    @Before
    public void setUp() {
        Audit audit = new Audit();
        audit.setName("TestAudit");
        audit.setStartDate(Date.valueOf("2000-01-02"));
        audit.setCreationDate(Timestamp.from(Instant.now()));
        audit.setStatus(AuditStatus.ACTIVE);
        auditRepository.save(audit);

        interview = new Interview();
        interview.setAuditId(audit.getId());
        interview.setStartDate(Date.valueOf("2020-01-01"));
        interview.setEndDate(Date.valueOf("2020-01-02"));
        interview.setStatus(InterviewStatus.ACTIVE);
        interviewRepository.save(interview);

        facCrit = new FacCrit();
        facCrit.setName("TestFaccrit");
        facCritRepository.save(facCrit);

        question = new Question();
        question.setTextDe("Testquestion");
        question.setFaccritId(facCrit.getId());
        questionRepository.save(question);
    }

    @Test
    public void getAllAnswers(){
        Answer answer = new Answer();
        answer.setQuestionId(question.getId());
        answer.setInterviewId(interview.getId());
        answer.setFaccritId(question.getFaccritId());
        answer.setReason("ReasonBefore");
        answerRepository.save(answer);

        String url = "/answers";

        ResponseEntity<List<BasicAnswerResponse>> response = testRestTemplate.exchange(url, HttpMethod.GET,
                null, new ParameterizedTypeReference<List<BasicAnswerResponse>>() {
                });

        Assert.assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void getAllAnswersWithNoExistingAnswer(){
        String url = "/answers";
        ResponseEntity<List<BasicAnswerResponse>> response = testRestTemplate.exchange(url, HttpMethod.GET,
                null, new ParameterizedTypeReference<List<BasicAnswerResponse>>() {
                });

        Assert.assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void getAnswersByInterviewId() {
        Answer answer = new Answer();
        answer.setQuestionId(question.getId());
        answer.setInterviewId(interview.getId());
        answer.setFaccritId(question.getFaccritId());
        answer.setReason("ReasonBefore");
        answerRepository.save(answer);
        String interviewId = String.valueOf(interview.getId());

        String url = "/answers/interview/" + interviewId;
        ResponseEntity<List<BasicAnswerResponse>> response = testRestTemplate.exchange(url, HttpMethod.GET,
                null, new ParameterizedTypeReference<List<BasicAnswerResponse>>() {
                });

        Assert.assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void getAnswersByInvalidInterviewId() {
        Answer answer = new Answer();
        answer.setQuestionId(question.getId());
        answer.setInterviewId(interview.getId());
        answer.setFaccritId(question.getFaccritId());
        answer.setReason("ReasonBefore");
        answerRepository.save(answer);

        String url = "/answers/interview/-1";
        ResponseEntity<BasicAnswerResponse> response = testRestTemplate.getForEntity(url, BasicAnswerResponse.class);
        Assert.assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    public void getAnswerByIds() {
        Answer answer = new Answer();
        answer.setQuestionId(question.getId());
        answer.setInterviewId(interview.getId());
        answer.setFaccritId(question.getFaccritId());
        answer.setReason("ReasonBefore");
        answerRepository.save(answer);
        String questionId = String.valueOf(question.getId());
        String interviewId = String.valueOf(interview.getId());

        String url = "/answers/interview/" + interviewId + "/question/" + questionId;
        ResponseEntity<BasicAnswerResponse> response = testRestTemplate.getForEntity(url, BasicAnswerResponse.class);

        Assert.assertEquals(answer.getQuestionId(), response.getBody().getQuestionId());
        Assert.assertEquals(answer.getInterviewId(), response.getBody().getInterviewId());
    }

    @Test
    public void getAnswerByInvalidIds() {
        Answer answer = new Answer();
        answer.setQuestionId(question.getId());
        answer.setInterviewId(interview.getId());
        answer.setFaccritId(question.getFaccritId());
        answer.setReason("ReasonBefore");
        answerRepository.save(answer);
        String questionId = String.valueOf(question.getId());
        String interviewId = String.valueOf(interview.getId());

        String url = "/answers/interview/" + -1 + "/question/" + questionId;
        String url2 = "/answers/interview/" + interviewId + "/question/" + -1;
        ResponseEntity<BasicAnswerResponse> response = testRestTemplate.getForEntity(url, BasicAnswerResponse.class);
        ResponseEntity<BasicAnswerResponse> response2 = testRestTemplate.getForEntity(url2, BasicAnswerResponse.class);

        Assert.assertEquals(400, response.getStatusCodeValue());
        Assert.assertEquals(400, response2.getStatusCodeValue());
    }

    @Test
    public void createAnswer() {
        CreateAnswerRequest answerRequest = new CreateAnswerRequest();
        answerRequest.setInterviewId(interview.getId());
        answerRequest.setQuestionId(question.getId());

        HttpEntity<CreateAnswerRequest> request = new HttpEntity<>(answerRequest);

        ResponseEntity<BasicAnswerResponse> response = testRestTemplate.postForEntity("/answers", request,
                BasicAnswerResponse.class);

        Assert.assertEquals(answerRequest.getInterviewId(), response.getBody().getInterviewId());
        Assert.assertEquals(answerRequest.getQuestionId(), response.getBody().getQuestionId());
        Assert.assertNotNull(response.getBody().getFaccritId());
    }

    @Test
    public void createAnswerByInvalidInterviewId() {
        CreateAnswerRequest answerRequest = new CreateAnswerRequest();
        answerRequest.setInterviewId(-1);
        answerRequest.setQuestionId(question.getId());

        HttpEntity<CreateAnswerRequest> request = new HttpEntity<>(answerRequest);

        ResponseEntity<BasicAnswerResponse> response = testRestTemplate.postForEntity("/answers", request,
                BasicAnswerResponse.class);

        Assert.assertEquals(400, response.getStatusCode().value());
    }

    @Test
    public void createAnswerByInvalidQuestionId() {
        CreateAnswerRequest answerRequest = new CreateAnswerRequest();
        answerRequest.setInterviewId(interview.getId());
        answerRequest.setQuestionId(-1);

        HttpEntity<CreateAnswerRequest> request = new HttpEntity<>(answerRequest);

        ResponseEntity<BasicAnswerResponse> response = testRestTemplate.postForEntity("/answers", request,
                BasicAnswerResponse.class);

        Assert.assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    public void updateAnswer() {
        Answer answer = new Answer();
        answer.setQuestionId(question.getId());
        answer.setInterviewId(interview.getId());
        answer.setFaccritId(question.getFaccritId());
        answer.setReason("ReasonBefore");
        answerRepository.save(answer);
        String reasonNew = "ReasonAfter";

        UpdateAnswerRequest updateAnswerRequest = new UpdateAnswerRequest();
        updateAnswerRequest.setQuestionId(question.getId());
        updateAnswerRequest.setInterviewId(interview.getId());
        updateAnswerRequest.setProof("TestProof");
        updateAnswerRequest.setAnnotation("TestAnnotation");
        updateAnswerRequest.setReason(reasonNew);
        updateAnswerRequest.setDocumentation(false);
        updateAnswerRequest.setProcedure(false);
        updateAnswerRequest.setResponsible(false);

        HttpEntity<UpdateAnswerRequest> request = new HttpEntity<>(updateAnswerRequest);

        String questionId = String.valueOf(question.getId());
        String interviewId = String.valueOf(interview.getId());

        String url = "/answers/interview/" + interviewId + "/question/" + questionId;
        ResponseEntity<BasicAnswerResponse> response = testRestTemplate.exchange(url, HttpMethod.PUT,
                request, BasicAnswerResponse.class);

        Assert.assertEquals(reasonNew, response.getBody().getReason());
    }

    @Test
    public void updateInvalidAnswer() {
        Answer answer = new Answer();
        answer.setQuestionId(question.getId());
        answer.setInterviewId(interview.getId());
        answer.setFaccritId(question.getFaccritId());
        answer.setReason("ReasonBefore");
        answerRepository.save(answer);
        String reasonNew = "ReasonAfter";

        UpdateAnswerRequest updateAnswerRequest = new UpdateAnswerRequest();
        updateAnswerRequest.setQuestionId(question.getId());
        updateAnswerRequest.setInterviewId(interview.getId());
        updateAnswerRequest.setProof("TestProof");
        updateAnswerRequest.setAnnotation("TestAnnotation");
        updateAnswerRequest.setReason(reasonNew);
        updateAnswerRequest.setDocumentation(false);
        updateAnswerRequest.setProcedure(false);
        updateAnswerRequest.setResponsible(false);

        HttpEntity<UpdateAnswerRequest> request = new HttpEntity<>(updateAnswerRequest);

        String questionId = String.valueOf(question.getId());
        String interviewId = String.valueOf(interview.getId());

        String url = "/answers/interview/" + -1 + "/question/" + questionId;
        String url2 = "/answers/interview/" + interviewId + "/question/" + -1;
        ResponseEntity<BasicAnswerResponse> response = testRestTemplate.exchange(url, HttpMethod.PUT,
                request, BasicAnswerResponse.class);
        ResponseEntity<BasicAnswerResponse> response2 = testRestTemplate.exchange(url2, HttpMethod.PUT,
                request, BasicAnswerResponse.class);

        Assert.assertEquals(400, response.getStatusCodeValue());
        Assert.assertEquals(400, response2.getStatusCodeValue());
    }
}
