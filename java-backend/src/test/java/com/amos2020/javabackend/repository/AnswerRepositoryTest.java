package com.amos2020.javabackend.repository;

import com.amos2020.javabackend.entity.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AnswerRepositoryTest {

    @Autowired
    AnswerRepository repository;
    @Autowired
    InterviewRepository interviewRepository;
    @Autowired
    FacCritRepository facCritRepository;
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    AuditRepository auditRepository;

    private Answer answer;
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
        facCrit.setGoal("TestGoal");
        facCritRepository.save(facCrit);

        question = new Question();
        question.setTextDe("Testquestion");
        question.setFaccritId(facCrit.getId());
        questionRepository.save(question);
    }

    public void insertValidAnswerEntity() {
        answer = new Answer();
        answer.setQuestionId(question.getId());
        answer.setInterviewId(interview.getId());
        answer.setFaccritId(question.getFaccritId());
        answer.setResult(true);
        answer.setResponsible(true);
        answer.setDocumentation(true);
        answer.setProcedure(true);
        answer.setReason("TestReason");
        answer.setProof("TestProof");
        answer.setAnnotation("TestAnno");

        Answer toTest = repository.save(answer);

        Assert.assertTrue(repository.exists(Example.of(toTest)));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void insertAnswerWithQuestionIdNull() {
        answer = new Answer();
        answer.setInterviewId(interview.getId());
        answer.setFaccritId(question.getFaccritId());
        answer.setResult(true);
        answer.setResponsible(true);
        answer.setDocumentation(true);
        answer.setProcedure(true);
        answer.setReason("TestReason");
        answer.setProof("TestProof");
        answer.setAnnotation("TestAnno");

        repository.save(answer);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void insertAnswerWithInterviewIdNull() {
        answer = new Answer();
        answer.setQuestionId(question.getId());
        answer.setFaccritId(question.getFaccritId());
        answer.setResult(true);
        answer.setResponsible(true);
        answer.setDocumentation(true);
        answer.setProcedure(true);
        answer.setReason("TestReason");
        answer.setProof("TestProof");
        answer.setAnnotation("TestAnno");

        repository.save(answer);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void insertAnswerWithFaccritIdNull() {
        answer = new Answer();
        answer.setQuestionId(question.getId());
        answer.setInterviewId(interview.getId());
        answer.setResult(true);
        answer.setResponsible(true);
        answer.setDocumentation(true);
        answer.setProcedure(true);
        answer.setReason("TestReason");
        answer.setProof("TestProof");
        answer.setAnnotation("TestAnno");

        repository.save(answer);
    }

    @Test
    public void insertAnswerWithResultNull() {
        answer = new Answer();
        answer.setQuestionId(question.getId());
        answer.setInterviewId(interview.getId());
        answer.setFaccritId(question.getFaccritId());
        answer.setResponsible(true);
        answer.setDocumentation(true);
        answer.setProcedure(true);
        answer.setReason("TestReason");
        answer.setProof("TestProof");
        answer.setAnnotation("TestAnno");

        Answer toTest = repository.save(answer);

        Assert.assertTrue(repository.exists(Example.of(toTest)));
    }

    @Test
    public void insertAnswerWithResponsibleNull() {
        answer = new Answer();
        answer.setQuestionId(question.getId());
        answer.setInterviewId(interview.getId());
        answer.setFaccritId(question.getFaccritId());
        answer.setResult(true);
        answer.setDocumentation(true);
        answer.setProcedure(true);
        answer.setReason("TestReason");
        answer.setProof("TestProof");
        answer.setAnnotation("TestAnno");

        Answer toTest = repository.save(answer);

        Assert.assertTrue(repository.exists(Example.of(toTest)));
    }

    @Test
    public void insertAnswerWithDocumentationNull() {
        answer = new Answer();
        answer.setQuestionId(question.getId());
        answer.setInterviewId(interview.getId());
        answer.setFaccritId(question.getFaccritId());
        answer.setResult(true);
        answer.setResponsible(true);
        answer.setProcedure(true);
        answer.setReason("TestReason");
        answer.setProof("TestProof");
        answer.setAnnotation("TestAnno");

        Answer toTest = repository.save(answer);

        Assert.assertTrue(repository.exists(Example.of(toTest)));
    }

    @Test
    public void insertAnswerWithProcedureNull() {
        answer = new Answer();
        answer.setQuestionId(question.getId());
        answer.setInterviewId(interview.getId());
        answer.setFaccritId(question.getFaccritId());
        answer.setResult(true);
        answer.setResponsible(true);
        answer.setDocumentation(true);
        answer.setReason("TestReason");
        answer.setProof("TestProof");
        answer.setAnnotation("TestAnno");

        Answer toTest = repository.save(answer);

        Assert.assertTrue(repository.exists(Example.of(toTest)));
    }

    @Test
    public void insertAnswerWithReasonNull() {
        answer = new Answer();
        answer.setQuestionId(question.getId());
        answer.setInterviewId(interview.getId());
        answer.setFaccritId(question.getFaccritId());
        answer.setResult(true);
        answer.setResponsible(true);
        answer.setDocumentation(true);
        answer.setProcedure(true);
        answer.setProof("TestProof");
        answer.setAnnotation("TestAnno");

        Answer toTest = repository.save(answer);

        Assert.assertTrue(repository.exists(Example.of(toTest)));
    }

    @Test
    public void insertAnswerWithProofNull() {
        answer = new Answer();
        answer.setQuestionId(question.getId());
        answer.setInterviewId(interview.getId());
        answer.setFaccritId(question.getFaccritId());
        answer.setResult(true);
        answer.setResponsible(true);
        answer.setDocumentation(true);
        answer.setProcedure(true);
        answer.setReason("TestReason");
        answer.setAnnotation("TestAnno");

        Answer toTest = repository.save(answer);

        Assert.assertTrue(repository.exists(Example.of(toTest)));
    }

    @Test
    public void insertAnswerWithAnnotationNull() {
        answer = new Answer();
        answer.setQuestionId(question.getId());
        answer.setInterviewId(interview.getId());
        answer.setFaccritId(question.getFaccritId());
        answer.setResult(true);
        answer.setResponsible(true);
        answer.setDocumentation(true);
        answer.setProcedure(true);
        answer.setReason("TestReason");

        Answer toTest = repository.save(answer);

        Assert.assertTrue(repository.exists(Example.of(toTest)));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void changeAnswerWithQuestionIdInvalid() {
        answer = new Answer();
        answer.setQuestionId(question.getId());
        answer.setInterviewId(interview.getId());
        answer.setFaccritId(question.getFaccritId());
        answer.setResult(true);
        answer.setResponsible(true);
        answer.setDocumentation(true);
        answer.setProcedure(true);
        answer.setReason("TestReason");
        answer.setProof("TestProof");
        answer.setAnnotation("TestAnno");
        Answer toTest = repository.save(answer);

        toTest.setQuestionId(-1);
        repository.save(toTest);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void changeAnswerWithInterviewIdInvalid() {
        answer = new Answer();
        answer.setQuestionId(question.getId());
        answer.setInterviewId(interview.getId());
        answer.setFaccritId(question.getFaccritId());
        answer.setResult(true);
        answer.setResponsible(true);
        answer.setDocumentation(true);
        answer.setProcedure(true);
        answer.setReason("TestReason");
        answer.setProof("TestProof");
        answer.setAnnotation("TestAnno");
        Answer toTest = repository.save(answer);

        toTest.setInterviewId(-1);
        repository.save(toTest);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void changeAnswerWithFaccritIdInvalid() {
        answer = new Answer();
        answer.setQuestionId(question.getId());
        answer.setInterviewId(interview.getId());
        answer.setFaccritId(question.getFaccritId());
        answer.setResult(true);
        answer.setResponsible(true);
        answer.setDocumentation(true);
        answer.setProcedure(true);
        answer.setReason("TestReason");
        answer.setProof("TestProof");
        answer.setAnnotation("TestAnno");
        Answer toTest = repository.save(answer);

        toTest.setFaccritId(-1);
        repository.save(toTest);
    }

    @Test
    public void changeAnswerWithResultNull() {
        answer = new Answer();
        answer.setQuestionId(question.getId());
        answer.setInterviewId(interview.getId());
        answer.setFaccritId(question.getFaccritId());
        answer.setResult(true);
        answer.setResponsible(true);
        answer.setDocumentation(true);
        answer.setProcedure(true);
        answer.setReason("TestReason");
        answer.setProof("TestProof");
        answer.setAnnotation("TestAnno");
        Answer answer1 = repository.save(answer);

        answer1.setResult(null);
        Answer toTest = repository.save(answer1);

        Assert.assertTrue(repository.exists(Example.of(toTest)));
    }

    @Test
    public void changeAnswerWithResponsibleNull() {
        answer = new Answer();
        answer.setQuestionId(question.getId());
        answer.setInterviewId(interview.getId());
        answer.setFaccritId(question.getFaccritId());
        answer.setResult(true);
        answer.setResponsible(true);
        answer.setDocumentation(true);
        answer.setProcedure(true);
        answer.setReason("TestReason");
        answer.setProof("TestProof");
        answer.setAnnotation("TestAnno");
        Answer answer1 = repository.save(answer);

        answer1.setResponsible(null);
        Answer toTest = repository.save(answer1);

        Assert.assertTrue(repository.exists(Example.of(toTest)));
    }

    @Test
    public void changeAnswerWithDocumentationNull() {
        answer = new Answer();
        answer.setQuestionId(question.getId());
        answer.setInterviewId(interview.getId());
        answer.setFaccritId(question.getFaccritId());
        answer.setResult(true);
        answer.setResponsible(true);
        answer.setDocumentation(true);
        answer.setProcedure(true);
        answer.setReason("TestReason");
        answer.setProof("TestProof");
        answer.setAnnotation("TestAnno");
        Answer answer1 = repository.save(answer);

        answer1.setDocumentation(null);
        Answer toTest = repository.save(answer1);

        Assert.assertTrue(repository.exists(Example.of(toTest)));
    }

    @Test
    public void changeAnswerWithProcedureNull() {
        answer = new Answer();
        answer.setQuestionId(question.getId());
        answer.setInterviewId(interview.getId());
        answer.setFaccritId(question.getFaccritId());
        answer.setResult(true);
        answer.setResponsible(true);
        answer.setDocumentation(true);
        answer.setProcedure(true);
        answer.setReason("TestReason");
        answer.setProof("TestProof");
        answer.setAnnotation("TestAnno");
        Answer answer1 = repository.save(answer);

        answer1.setProcedure(null);
        Answer toTest = repository.save(answer1);

        Assert.assertTrue(repository.exists(Example.of(toTest)));
    }

    @Test
    public void changeAnswerWithReasonNull() {
        answer = new Answer();
        answer.setQuestionId(question.getId());
        answer.setInterviewId(interview.getId());
        answer.setFaccritId(question.getFaccritId());
        answer.setResult(true);
        answer.setResponsible(true);
        answer.setDocumentation(true);
        answer.setProcedure(true);
        answer.setReason("TestReason");
        answer.setProof("TestProof");
        answer.setAnnotation("TestAnno");
        Answer answer1 = repository.save(answer);

        answer1.setReason(null);
        Answer toTest = repository.save(answer1);

        Assert.assertTrue(repository.exists(Example.of(toTest)));
    }

    @Test
    public void changeAnswerWithProofNull() {
        answer = new Answer();
        answer.setQuestionId(question.getId());
        answer.setInterviewId(interview.getId());
        answer.setFaccritId(question.getFaccritId());
        answer.setResult(true);
        answer.setResponsible(true);
        answer.setDocumentation(true);
        answer.setProcedure(true);
        answer.setReason("TestReason");
        answer.setProof("TestProof");
        answer.setAnnotation("TestAnno");
        Answer answer1 = repository.save(answer);

        answer1.setProof(null);
        Answer toTest = repository.save(answer1);

        Assert.assertTrue(repository.exists(Example.of(toTest)));
    }

    @Test
    public void changeAnswerWithAnnotationNull() {
        answer = new Answer();
        answer.setQuestionId(question.getId());
        answer.setInterviewId(interview.getId());
        answer.setFaccritId(question.getFaccritId());
        answer.setResult(true);
        answer.setResponsible(true);
        answer.setDocumentation(true);
        answer.setProcedure(true);
        answer.setReason("TestReason");
        answer.setProof("TestProof");
        answer.setAnnotation("TestAnno");
        Answer answer1 = repository.save(answer);

        answer1.setAnnotation(null);
        Answer toTest = repository.save(answer1);

        Assert.assertTrue(repository.exists(Example.of(toTest)));
    }

    @Test
    public void changeAnswerResult() {
        answer = new Answer();
        answer.setQuestionId(question.getId());
        answer.setInterviewId(interview.getId());
        answer.setFaccritId(question.getFaccritId());
        answer.setResult(true);
        answer.setResponsible(true);
        answer.setDocumentation(true);
        answer.setProcedure(true);
        answer.setReason("TestReason");
        answer.setProof("TestProof");
        answer.setAnnotation("TestAnno");
        Answer answer1 = repository.save(answer);

        answer1.setResult(!answer.getResult());
        Answer toTest = repository.save(answer1);

        Assert.assertTrue(repository.exists(Example.of(toTest)));
    }

    @Test
    public void changeAnswerResponsible() {
        answer = new Answer();
        answer.setQuestionId(question.getId());
        answer.setInterviewId(interview.getId());
        answer.setFaccritId(question.getFaccritId());
        answer.setResult(true);
        answer.setResponsible(true);
        answer.setDocumentation(true);
        answer.setProcedure(true);
        answer.setReason("TestReason");
        answer.setProof("TestProof");
        answer.setAnnotation("TestAnno");
        Answer answer1 = repository.save(answer);

        answer1.setResponsible(!answer.getResponsible());
        Answer toTest = repository.save(answer1);

        Assert.assertTrue(repository.exists(Example.of(toTest)));
    }

    @Test
    public void changeAnswerDocumentation() {
        answer = new Answer();
        answer.setQuestionId(question.getId());
        answer.setInterviewId(interview.getId());
        answer.setFaccritId(question.getFaccritId());
        answer.setResult(true);
        answer.setResponsible(true);
        answer.setDocumentation(true);
        answer.setProcedure(true);
        answer.setReason("TestReason");
        answer.setProof("TestProof");
        answer.setAnnotation("TestAnno");
        Answer answer1 = repository.save(answer);

        answer1.setDocumentation(!answer.getDocumentation());
        Answer toTest = repository.save(answer1);

        Assert.assertTrue(repository.exists(Example.of(toTest)));
    }

    @Test
    public void changeAnswerProcedure() {
        answer = new Answer();
        answer.setQuestionId(question.getId());
        answer.setInterviewId(interview.getId());
        answer.setFaccritId(question.getFaccritId());
        answer.setResult(true);
        answer.setResponsible(true);
        answer.setDocumentation(true);
        answer.setProcedure(true);
        answer.setReason("TestReason");
        answer.setProof("TestProof");
        answer.setAnnotation("TestAnno");
        Answer answer1 = repository.save(answer);

        answer1.setProcedure(!answer.getProcedure());
        Answer toTest = repository.save(answer1);

        Assert.assertTrue(repository.exists(Example.of(toTest)));
    }

    @Test
    public void changeAnswerReason() {
        answer = new Answer();
        answer.setQuestionId(question.getId());
        answer.setInterviewId(interview.getId());
        answer.setFaccritId(question.getFaccritId());
        answer.setResult(true);
        answer.setResponsible(true);
        answer.setDocumentation(true);
        answer.setProcedure(true);
        answer.setReason("TestReason");
        answer.setProof("TestProof");
        answer.setAnnotation("TestAnno");
        Answer answer1 = repository.save(answer);

        answer1.setReason("NewTestReason");
        Answer toTest = repository.save(answer1);

        Assert.assertTrue(repository.exists(Example.of(toTest)));
    }

    @Test
    public void changeAnswerProof() {
        answer = new Answer();
        answer.setQuestionId(question.getId());
        answer.setInterviewId(interview.getId());
        answer.setFaccritId(question.getFaccritId());
        answer.setResult(true);
        answer.setResponsible(true);
        answer.setDocumentation(true);
        answer.setProcedure(true);
        answer.setReason("TestReason");
        answer.setProof("TestProof");
        answer.setAnnotation("TestAnno");
        Answer answer1 = repository.save(answer);

        answer1.setProof("NewTestProof");
        Answer toTest = repository.save(answer1);

        Assert.assertTrue(repository.exists(Example.of(toTest)));
    }

    @Test
    public void changeAnswerAnnotation() {
        answer = new Answer();
        answer.setQuestionId(question.getId());
        answer.setInterviewId(interview.getId());
        answer.setInterviewId(interview.getId());
        answer.setFaccritId(question.getFaccritId());
        answer.setResult(true);
        answer.setResponsible(true);
        answer.setDocumentation(true);
        answer.setProcedure(true);
        answer.setReason("TestReason");
        answer.setProof("TestProof");
        answer.setAnnotation("TestAnno");
        Answer answer1 = repository.save(answer);

        answer1.setAnnotation("NewDocu");
        Answer toTest = repository.save(answer1);

        Assert.assertTrue(repository.exists(Example.of(toTest)));
    }

    @Test
    public void deleteAnswerEntity() {
        answer = new Answer();
        answer.setQuestionId(question.getId());
        answer.setInterviewId(interview.getId());
        answer.setFaccritId(question.getFaccritId());
        answer.setResult(true);
        answer.setResponsible(true);
        answer.setDocumentation(true);
        answer.setProcedure(true);
        answer.setReason("TestReason");
        answer.setProof("TestProof");

        Answer toTest = repository.save(answer);
        repository.delete(toTest);

        Assert.assertFalse(repository.exists(Example.of(toTest)));
    }

    @After
    public void tearDown() {
        repository.delete(answer);
        interviewRepository.delete(interview);
        questionRepository.delete(question);
        facCritRepository.delete(facCrit);
    }
}
