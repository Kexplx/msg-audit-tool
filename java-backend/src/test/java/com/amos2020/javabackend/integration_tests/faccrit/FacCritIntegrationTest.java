package com.amos2020.javabackend.integration_tests.faccrit;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.amos2020.javabackend.JavaBackendApplication;
import com.amos2020.javabackend.entity.*;
import com.amos2020.javabackend.repository.*;
import com.amos2020.javabackend.rest_service.response.BasicAnswerResponse;
import com.amos2020.javabackend.rest_service.response.BasicFacCritResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
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
public class FacCritIntegrationTest {

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
    private Question question1;
    private Question question2;
    private Question question3;
    private Question question4;
    private Question question5;
    private Question question6;
    private FacCrit facCrit;
    private FacCrit facCrit1;
    private FacCrit facCrit2;
    private FacCrit facCrit3;
    private FacCrit facCrit4;
    private FacCrit facCrit5;
    private FacCrit facCrit6;
    private Answer answer;
    private Answer answer1;
    private Answer answer2;
    private Answer answer3;
    private Answer answer4;
    private Answer answer5;
    private Answer answer6;
    private Answer answer7;

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
        facCrit.setGoal("TestGoal");
        facCritRepository.save(facCrit);

        facCrit1 = new FacCrit();
        facCrit1.setName("TestFaccrit");
        facCrit1.setGoal("TestGoal");
        facCrit1.setReferenceId(facCrit.getId());
        facCritRepository.save(facCrit1);

        facCrit2 = new FacCrit();
        facCrit2.setName("TestFaccrit");
        facCrit2.setGoal("TestGoal");
        facCrit2.setReferenceId(facCrit.getId());
        facCritRepository.save(facCrit2);

        facCrit3 = new FacCrit();
        facCrit3.setName("TestFaccrit");
        facCrit3.setGoal("TestGoal");
        facCritRepository.save(facCrit3);

        facCrit4 = new FacCrit();
        facCrit4.setName("TestFaccrit");
        facCrit4.setGoal("TestGoal");
        facCritRepository.save(facCrit4);

        facCrit5 = new FacCrit();
        facCrit5.setName("TestFaccrit");
        facCrit5.setGoal("TestGoal");
        facCrit5.setReferenceId(facCrit4.getId());
        facCritRepository.save(facCrit5);

        facCrit6 = new FacCrit();
        facCrit6.setName("TestFaccrit");
        facCrit6.setGoal("TestGoal");
        facCrit6.setReferenceId(facCrit4.getId());
        facCritRepository.save(facCrit6);

        question = new Question();
        question.setTextDe("Testquestion");
        question.setFaccritId(facCrit.getId());
        questionRepository.save(question);

        question1 = new Question();
        question1.setTextDe("Testquestion");
        question1.setFaccritId(facCrit1.getId());
        questionRepository.save(question1);

        question2 = new Question();
        question2.setTextDe("Testquestion");
        question2.setFaccritId(facCrit2.getId());
        questionRepository.save(question2);

        question3 = new Question();
        question3.setTextDe("Testquestion");
        question3.setFaccritId(facCrit3.getId());
        questionRepository.save(question3);

        question4 = new Question();
        question4.setTextDe("Testquestion");
        question4.setFaccritId(facCrit4.getId());
        questionRepository.save(question4);

        question5 = new Question();
        question5.setTextDe("Testquestion");
        question5.setFaccritId(facCrit5.getId());
        questionRepository.save(question5);

        question6 = new Question();
        question6.setTextDe("Testquestion");
        question6.setFaccritId(facCrit6.getId());
        questionRepository.save(question6);

        answer = new Answer();
        answer.setQuestionId(question.getId());
        answer.setInterviewId(interview.getId());
        answer.setFaccritId(question.getFaccritId());
        answerRepository.save(answer);

        answer1 = new Answer();
        answer1.setQuestionId(question1.getId());
        answer1.setInterviewId(interview.getId());
        answer1.setFaccritId(question1.getFaccritId());
        answerRepository.save(answer1);

        answer2 = new Answer();
        answer2.setQuestionId(question2.getId());
        answer2.setInterviewId(interview.getId());
        answer2.setFaccritId(question2.getFaccritId());
        answerRepository.save(answer2);

        answer3 = new Answer();
        answer3.setQuestionId(question3.getId());
        answer3.setInterviewId(interview.getId());
        answer3.setFaccritId(question3.getFaccritId());
        answerRepository.save(answer3);

        answer4 = new Answer();
        answer4.setQuestionId(question4.getId());
        answer4.setInterviewId(interview.getId());
        answer4.setFaccritId(question4.getFaccritId());
        answerRepository.save(answer4);

        answer5 = new Answer();
        answer5.setQuestionId(question5.getId());
        answer5.setInterviewId(interview.getId());
        answer5.setFaccritId(question5.getFaccritId());
        answerRepository.save(answer5);

        answer6 = new Answer();
        answer6.setQuestionId(question6.getId());
        answer6.setInterviewId(interview.getId());
        answer6.setFaccritId(question6.getFaccritId());
        answerRepository.save(answer6);
    }

    @Test
    public void getAllFaccritsOrderedByInterviewId(){
        int id = interview.getId();

        String url = "/faccrits/interview/"+String.valueOf(id);

        ResponseEntity<List<BasicFacCritResponse>> response = testRestTemplate.exchange(url, HttpMethod.GET,
                null, new ParameterizedTypeReference<List<BasicFacCritResponse>>() {
                });


        Assert.assertEquals(200, response.getStatusCodeValue());
    }
}
