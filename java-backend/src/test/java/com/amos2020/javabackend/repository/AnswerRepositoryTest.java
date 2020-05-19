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
    private  FacCrit facCrit;
    private Audit audit;

    @Before
    public void setUp(){
        audit = new Audit();
        audit.setName("TEST_NAME");
        audit.setStartDate(Date.valueOf("2020-01-01"));
        audit.setEndDate(Date.valueOf("2020-01-02"));
        audit.setExpectedEndDate(Date.valueOf("2020-01-03"));
        auditRepository.save(audit);

        interview = new Interview();
        interview.setInterviewAuditId(audit.getId());
        interview.setInterviewDate(Date.valueOf("2020-01-01"));
        interview.setInterviewAnnotation("TestAnnotation");
        interviewRepository.save(interview);

        facCrit = new FacCrit();
        facCrit.setFaccritName("TestFaccrit");
        facCritRepository.save(facCrit);

        question = new Question();
        question.setQuestionTextDe("Testquestion?");
        questionRepository.save(question);
    }

    public void insertValidAnswerEntity(){
        answer = new Answer();
        answer.setAnswerFaccritId(facCrit.getFaccritId());
        answer.setAnswerQuestionId(question.getQuestionId());
        answer.setAnswerInterviewId(interview.getInterviewId());
        answer.setAnswerResult(true);
        answer.setAnswerResponsible(true);
        answer.setAnswerDocumentation(true);
        answer.setAnswerProcedure(true);
        answer.setAnswerReason("TestReason");
        answer.setAnswerProof("TestProof");

        Answer toTest = repository.save(answer);

        Assert.assertTrue(repository.exists(Example.of(toTest)));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void insertAnswerWithFaccritIdNull(){
        answer = new Answer();
        answer.setAnswerQuestionId(question.getQuestionId());
        answer.setAnswerInterviewId(interview.getInterviewId());
        answer.setAnswerResult(true);
        answer.setAnswerResponsible(true);
        answer.setAnswerDocumentation(true);
        answer.setAnswerProcedure(true);
        answer.setAnswerReason("TestReason");
        answer.setAnswerProof("TestProof");

        repository.save(answer);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void insertAnswerWithQuestionIdNull(){
        answer = new Answer();
        answer.setAnswerFaccritId(facCrit.getFaccritId());
        answer.setAnswerInterviewId(interview.getInterviewId());
        answer.setAnswerResult(true);
        answer.setAnswerResponsible(true);
        answer.setAnswerDocumentation(true);
        answer.setAnswerProcedure(true);
        answer.setAnswerReason("TestReason");
        answer.setAnswerProof("TestProof");

        repository.save(answer);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void insertAnswerWithInterviewIdNull(){
        answer = new Answer();
        answer.setAnswerFaccritId(facCrit.getFaccritId());
        answer.setAnswerQuestionId(question.getQuestionId());
        answer.setAnswerResult(true);
        answer.setAnswerResponsible(true);
        answer.setAnswerDocumentation(true);
        answer.setAnswerProcedure(true);
        answer.setAnswerReason("TestReason");
        answer.setAnswerProof("TestProof");

        repository.save(answer);
    }

    @Test
    public void insertAnswerWithResultNull(){
        answer = new Answer();
        answer.setAnswerFaccritId(facCrit.getFaccritId());
        answer.setAnswerQuestionId(question.getQuestionId());
        answer.setAnswerInterviewId(interview.getInterviewId());
        answer.setAnswerResponsible(true);
        answer.setAnswerDocumentation(true);
        answer.setAnswerProcedure(true);
        answer.setAnswerReason("TestReason");
        answer.setAnswerProof("TestProof");

        Answer toTest = repository.save(answer);

        Assert.assertTrue(repository.exists(Example.of(toTest)));
    }

    @Test
    public void insertAnswerWithResponsibleNull(){
        answer = new Answer();
        answer.setAnswerFaccritId(facCrit.getFaccritId());
        answer.setAnswerQuestionId(question.getQuestionId());
        answer.setAnswerInterviewId(interview.getInterviewId());
        answer.setAnswerResult(true);
        answer.setAnswerDocumentation(true);
        answer.setAnswerProcedure(true);
        answer.setAnswerReason("TestReason");
        answer.setAnswerProof("TestProof");

        Answer toTest = repository.save(answer);

        Assert.assertTrue(repository.exists(Example.of(toTest)));
    }

    @Test
    public void insertAnswerWithDocumentationNull(){
        answer = new Answer();
        answer.setAnswerFaccritId(facCrit.getFaccritId());
        answer.setAnswerQuestionId(question.getQuestionId());
        answer.setAnswerInterviewId(interview.getInterviewId());
        answer.setAnswerResult(true);
        answer.setAnswerResponsible(true);
        answer.setAnswerProcedure(true);
        answer.setAnswerReason("TestReason");
        answer.setAnswerProof("TestProof");

        Answer toTest = repository.save(answer);

        Assert.assertTrue(repository.exists(Example.of(toTest)));
    }

    @Test
    public void insertAnswerWithProcedureNull(){
        answer = new Answer();
        answer.setAnswerFaccritId(facCrit.getFaccritId());
        answer.setAnswerQuestionId(question.getQuestionId());
        answer.setAnswerInterviewId(interview.getInterviewId());
        answer.setAnswerResult(true);
        answer.setAnswerResponsible(true);
        answer.setAnswerDocumentation(true);
        answer.setAnswerReason("TestReason");
        answer.setAnswerProof("TestProof");

        Answer toTest = repository.save(answer);

        Assert.assertTrue(repository.exists(Example.of(toTest)));
    }

    @Test
    public void insertAnswerWithReasonNull(){
        answer = new Answer();
        answer.setAnswerFaccritId(facCrit.getFaccritId());
        answer.setAnswerQuestionId(question.getQuestionId());
        answer.setAnswerInterviewId(interview.getInterviewId());
        answer.setAnswerResult(true);
        answer.setAnswerResponsible(true);
        answer.setAnswerDocumentation(true);
        answer.setAnswerProcedure(true);
        answer.setAnswerProof("TestProof");

        Answer toTest = repository.save(answer);

        Assert.assertTrue(repository.exists(Example.of(toTest)));
    }

    @Test
    public void insertAnswerWithProofNull(){
        answer = new Answer();
        answer.setAnswerFaccritId(facCrit.getFaccritId());
        answer.setAnswerQuestionId(question.getQuestionId());
        answer.setAnswerInterviewId(interview.getInterviewId());
        answer.setAnswerResult(true);
        answer.setAnswerResponsible(true);
        answer.setAnswerDocumentation(true);
        answer.setAnswerProcedure(true);
        answer.setAnswerReason("TestReason");

        Answer toTest = repository.save(answer);

        Assert.assertTrue(repository.exists(Example.of(toTest)));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void changeAnswerWithFaccritIdInvalid(){
        answer = new Answer();
        answer.setAnswerFaccritId(facCrit.getFaccritId());
        answer.setAnswerQuestionId(question.getQuestionId());
        answer.setAnswerInterviewId(interview.getInterviewId());
        answer.setAnswerResult(true);
        answer.setAnswerResponsible(true);
        answer.setAnswerDocumentation(true);
        answer.setAnswerProcedure(true);
        answer.setAnswerReason("TestReason");
        answer.setAnswerProof("TestProof");
        Answer toTest = repository.save(answer);

        toTest.setAnswerFaccritId(-1);
        repository.save(toTest);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void changeAnswerWithQuestionIdInvalid(){
        answer = new Answer();
        answer.setAnswerFaccritId(facCrit.getFaccritId());
        answer.setAnswerQuestionId(question.getQuestionId());
        answer.setAnswerInterviewId(interview.getInterviewId());
        answer.setAnswerResult(true);
        answer.setAnswerResponsible(true);
        answer.setAnswerDocumentation(true);
        answer.setAnswerProcedure(true);
        answer.setAnswerReason("TestReason");
        answer.setAnswerProof("TestProof");
        Answer toTest = repository.save(answer);

        toTest.setAnswerQuestionId(-1);
        repository.save(toTest);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void changeAnswerWithInterviewIdInvalid(){
        answer = new Answer();
        answer.setAnswerFaccritId(facCrit.getFaccritId());
        answer.setAnswerQuestionId(question.getQuestionId());
        answer.setAnswerInterviewId(interview.getInterviewId());
        answer.setAnswerResult(true);
        answer.setAnswerResponsible(true);
        answer.setAnswerDocumentation(true);
        answer.setAnswerProcedure(true);
        answer.setAnswerReason("TestReason");
        answer.setAnswerProof("TestProof");
        Answer toTest = repository.save(answer);

        toTest.setAnswerInterviewId(-1);
        repository.save(toTest);
    }

    @Test
    public void changeAnswerWithResultNull(){
        answer = new Answer();
        answer.setAnswerFaccritId(facCrit.getFaccritId());
        answer.setAnswerQuestionId(question.getQuestionId());
        answer.setAnswerInterviewId(interview.getInterviewId());
        answer.setAnswerResult(true);
        answer.setAnswerResponsible(true);
        answer.setAnswerDocumentation(true);
        answer.setAnswerProcedure(true);
        answer.setAnswerReason("TestReason");
        answer.setAnswerProof("TestProof");
        Answer answer1 = repository.save(answer);

        answer1.setAnswerResult(null);
        Answer toTest = repository.save(answer1);

        Assert.assertTrue(repository.exists(Example.of(toTest)));
    }

    @Test
    public void changeAnswerWithResponsibleNull(){
        answer = new Answer();
        answer.setAnswerFaccritId(facCrit.getFaccritId());
        answer.setAnswerQuestionId(question.getQuestionId());
        answer.setAnswerInterviewId(interview.getInterviewId());
        answer.setAnswerResult(true);
        answer.setAnswerResponsible(true);
        answer.setAnswerDocumentation(true);
        answer.setAnswerProcedure(true);
        answer.setAnswerReason("TestReason");
        answer.setAnswerProof("TestProof");
        Answer answer1 = repository.save(answer);

        answer1.setAnswerResponsible(null);
        Answer toTest = repository.save(answer1);

        Assert.assertTrue(repository.exists(Example.of(toTest)));
    }

    @Test
    public void changeAnswerWithDocumentationNull(){
        answer = new Answer();
        answer.setAnswerFaccritId(facCrit.getFaccritId());
        answer.setAnswerQuestionId(question.getQuestionId());
        answer.setAnswerInterviewId(interview.getInterviewId());
        answer.setAnswerResult(true);
        answer.setAnswerResponsible(true);
        answer.setAnswerDocumentation(true);
        answer.setAnswerProcedure(true);
        answer.setAnswerReason("TestReason");
        answer.setAnswerProof("TestProof");
        Answer answer1 = repository.save(answer);

        answer1.setAnswerDocumentation(null);
        Answer toTest = repository.save(answer1);

        Assert.assertTrue(repository.exists(Example.of(toTest)));
    }

    @Test
    public void changeAnswerWithProcedureNull(){
        answer = new Answer();
        answer.setAnswerFaccritId(facCrit.getFaccritId());
        answer.setAnswerQuestionId(question.getQuestionId());
        answer.setAnswerInterviewId(interview.getInterviewId());
        answer.setAnswerResult(true);
        answer.setAnswerResponsible(true);
        answer.setAnswerDocumentation(true);
        answer.setAnswerProcedure(true);
        answer.setAnswerReason("TestReason");
        answer.setAnswerProof("TestProof");
        Answer answer1 = repository.save(answer);

        answer1.setAnswerProcedure(null);
        Answer toTest = repository.save(answer1);

        Assert.assertTrue(repository.exists(Example.of(toTest)));
    }

    @Test
    public void changeAnswerWithReasonNull(){
        answer = new Answer();
        answer.setAnswerFaccritId(facCrit.getFaccritId());
        answer.setAnswerQuestionId(question.getQuestionId());
        answer.setAnswerInterviewId(interview.getInterviewId());
        answer.setAnswerResult(true);
        answer.setAnswerResponsible(true);
        answer.setAnswerDocumentation(true);
        answer.setAnswerProcedure(true);
        answer.setAnswerReason("TestReason");
        answer.setAnswerProof("TestProof");
        Answer answer1 = repository.save(answer);

        answer1.setAnswerReason(null);
        Answer toTest = repository.save(answer1);

        Assert.assertTrue(repository.exists(Example.of(toTest)));
    }

    @Test
    public void changeAnswerWithProofNull(){
        answer = new Answer();
        answer.setAnswerFaccritId(facCrit.getFaccritId());
        answer.setAnswerQuestionId(question.getQuestionId());
        answer.setAnswerInterviewId(interview.getInterviewId());
        answer.setAnswerResult(true);
        answer.setAnswerResponsible(true);
        answer.setAnswerDocumentation(true);
        answer.setAnswerProcedure(true);
        answer.setAnswerReason("TestReason");
        answer.setAnswerProof("TestProof");
        Answer answer1 = repository.save(answer);

        answer1.setAnswerProof(null);
        Answer toTest = repository.save(answer1);

        Assert.assertTrue(repository.exists(Example.of(toTest)));
    }

    @Test
    public void changeAnswerResult(){
        answer = new Answer();
        answer.setAnswerFaccritId(facCrit.getFaccritId());
        answer.setAnswerQuestionId(question.getQuestionId());
        answer.setAnswerInterviewId(interview.getInterviewId());
        answer.setAnswerResult(true);
        answer.setAnswerResponsible(true);
        answer.setAnswerDocumentation(true);
        answer.setAnswerProcedure(true);
        answer.setAnswerReason("TestReason");
        answer.setAnswerProof("TestProof");
        Answer answer1 = repository.save(answer);

        answer1.setAnswerResult(!answer.getAnswerResult());
        Answer toTest = repository.save(answer1);

        Assert.assertTrue(repository.exists(Example.of(toTest)));
    }

    @Test
    public void changeAnswerResponsible(){
        answer = new Answer();
        answer.setAnswerFaccritId(facCrit.getFaccritId());
        answer.setAnswerQuestionId(question.getQuestionId());
        answer.setAnswerInterviewId(interview.getInterviewId());
        answer.setAnswerResult(true);
        answer.setAnswerResponsible(true);
        answer.setAnswerDocumentation(true);
        answer.setAnswerProcedure(true);
        answer.setAnswerReason("TestReason");
        answer.setAnswerProof("TestProof");
        Answer answer1 = repository.save(answer);

        answer1.setAnswerResponsible(!answer.getAnswerResponsible());
        Answer toTest = repository.save(answer1);

        Assert.assertTrue(repository.exists(Example.of(toTest)));
    }

    @Test
    public void changeAnswerDocumentation(){
        answer = new Answer();
        answer.setAnswerFaccritId(facCrit.getFaccritId());
        answer.setAnswerQuestionId(question.getQuestionId());
        answer.setAnswerInterviewId(interview.getInterviewId());
        answer.setAnswerResult(true);
        answer.setAnswerResponsible(true);
        answer.setAnswerDocumentation(true);
        answer.setAnswerProcedure(true);
        answer.setAnswerReason("TestReason");
        answer.setAnswerProof("TestProof");
        Answer answer1 = repository.save(answer);

        answer1.setAnswerDocumentation(!answer.getAnswerDocumentation());
        Answer toTest = repository.save(answer1);

        Assert.assertTrue(repository.exists(Example.of(toTest)));
    }

    @Test
    public void changeAnswerProcedure(){
        answer = new Answer();
        answer.setAnswerFaccritId(facCrit.getFaccritId());
        answer.setAnswerQuestionId(question.getQuestionId());
        answer.setAnswerInterviewId(interview.getInterviewId());
        answer.setAnswerResult(true);
        answer.setAnswerResponsible(true);
        answer.setAnswerDocumentation(true);
        answer.setAnswerProcedure(true);
        answer.setAnswerReason("TestReason");
        answer.setAnswerProof("TestProof");
        Answer answer1 = repository.save(answer);

        answer1.setAnswerProcedure(!answer.getAnswerProcedure());
        Answer toTest = repository.save(answer1);

        Assert.assertTrue(repository.exists(Example.of(toTest)));
    }

    @Test
    public void changeAnswerReason(){
        answer = new Answer();
        answer.setAnswerFaccritId(facCrit.getFaccritId());
        answer.setAnswerQuestionId(question.getQuestionId());
        answer.setAnswerInterviewId(interview.getInterviewId());
        answer.setAnswerResult(true);
        answer.setAnswerResponsible(true);
        answer.setAnswerDocumentation(true);
        answer.setAnswerProcedure(true);
        answer.setAnswerReason("TestReason");
        answer.setAnswerProof("TestProof");
        Answer answer1 = repository.save(answer);

        answer1.setAnswerReason("NewTestReason");
        Answer toTest = repository.save(answer1);

        Assert.assertTrue(repository.exists(Example.of(toTest)));
    }

    @Test
    public void changeAnswerProof(){
        answer = new Answer();
        answer.setAnswerFaccritId(facCrit.getFaccritId());
        answer.setAnswerQuestionId(question.getQuestionId());
        answer.setAnswerInterviewId(interview.getInterviewId());
        answer.setAnswerResult(true);
        answer.setAnswerResponsible(true);
        answer.setAnswerDocumentation(true);
        answer.setAnswerProcedure(true);
        answer.setAnswerReason("TestReason");
        answer.setAnswerProof("TestProof");
        Answer answer1 = repository.save(answer);

        answer1.setAnswerProof("NewTestProof");
        Answer toTest = repository.save(answer1);

        Assert.assertTrue(repository.exists(Example.of(toTest)));
    }

    @Test
    public void deleteAnswerEntity(){
        answer = new Answer();
        answer.setAnswerFaccritId(facCrit.getFaccritId());
        answer.setAnswerQuestionId(question.getQuestionId());
        answer.setAnswerInterviewId(interview.getInterviewId());
        answer.setAnswerResult(true);
        answer.setAnswerResponsible(true);
        answer.setAnswerDocumentation(true);
        answer.setAnswerProcedure(true);
        answer.setAnswerReason("TestReason");
        answer.setAnswerProof("TestProof");

        Answer toTest = repository.save(answer);
        repository.delete(toTest);

        Assert.assertFalse(repository.exists(Example.of(toTest)));
    }

    @After
    public void tearDown(){
        repository.delete(answer);
        interviewRepository.delete(interview);
        facCritRepository.delete(facCrit);
        questionRepository.delete(question);
    }
}
