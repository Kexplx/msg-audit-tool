package com.amos2020.javabackend.repository;

import com.amos2020.javabackend.entity.Audit;
import com.amos2020.javabackend.entity.Interview;
import com.amos2020.javabackend.entity.InterviewStatus;
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
import org.springframework.transaction.TransactionSystemException;

import java.sql.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class InterviewRepositoryTest {

    @Autowired
    private InterviewRepository repository;
    @Autowired
    private AuditRepository auditRepository;

    private Audit audit;
    private Interview interview;

    @Before
    public void setUp(){
        Date startDate = Date.valueOf("2000-01-01");
        Date endDate = Date.valueOf("2000-01-02");

        audit = new Audit();
        audit.setName("TestAudit");
        audit.setStartDate(startDate);
        audit.setEndDate(endDate);
        audit.setExpectedEndDate(endDate);
        auditRepository.save(audit);
    }

    @Test
    public void insertValidInterviewEntity(){
        interview = new Interview();
        interview.setAuditId(audit.getId());
        interview.setStartDate(Date.valueOf("2020-01-01"));
        interview.setEndDate(Date.valueOf("2020-01-02"));
        interview.setAnnotation("TestAnnotation");
        interview.setStatus(InterviewStatus.ACTIVE);

        repository.save(interview);

        Assert.assertTrue(repository.exists(Example.of(interview)));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void insertInterviewWithAuditIdNull(){
        interview = new Interview();
        interview.setStartDate(Date.valueOf("2020-01-01"));
        interview.setEndDate(Date.valueOf("2020-01-02"));
        interview.setAnnotation("TestAnnotation");
        interview.setStatus(InterviewStatus.ACTIVE);

        repository.save(interview);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertInterviewWithStartDateNull(){
        interview = new Interview();
        interview.setAuditId(audit.getId());
        interview.setEndDate(Date.valueOf("2020-01-02"));
        interview.setAnnotation("TestAnnotation");
        interview.setStatus(InterviewStatus.ACTIVE);

        repository.save(interview);
    }

    public void insertInterviewWithEndDateNull(){
        interview = new Interview();
        interview.setAuditId(audit.getId());
        interview.setStartDate(Date.valueOf("2020-01-01"));
        interview.setAnnotation("TestAnnotation");
        interview.setStatus(InterviewStatus.ACTIVE);

        repository.save(interview);

        Assert.assertTrue(repository.exists(Example.of(interview)));
    }

    @Test(expected = TransactionSystemException.class)
    public void insertInterviewWithStatusNull(){
        interview = new Interview();
        interview.setAuditId(audit.getId());
        interview.setStartDate(Date.valueOf("2020-01-01"));
        interview.setAnnotation("TestAnnotation");

        repository.save(interview);
    }

    @Test
    public void insertInterviewAnnotationWithNull(){
        interview = new Interview();
        interview.setAuditId(audit.getId());
        interview.setStartDate(Date.valueOf("2020-01-01"));
        interview.setEndDate(Date.valueOf("2020-01-02"));
        interview.setStatus(InterviewStatus.ACTIVE);

        repository.save(interview);

        Assert.assertTrue(repository.exists(Example.of(interview)));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void changeInterviewWithAuditIdInvalid(){
        interview = new Interview();
        interview.setAuditId(audit.getId());
        interview.setStartDate(Date.valueOf("2020-01-01"));
        interview.setEndDate(Date.valueOf("2020-01-02"));
        interview.setAnnotation("TestAnnotation");
        interview.setStatus(InterviewStatus.ACTIVE);
        Interview toTest = repository.save(interview);

        toTest.setAuditId(-1);
        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void changeInterviewWithStartDateNull(){
        interview = new Interview();
        interview.setAuditId(audit.getId());
        interview.setStartDate(Date.valueOf("2020-01-01"));
        interview.setEndDate(Date.valueOf("2020-01-02"));
        interview.setAnnotation("TestAnnotation");
        interview.setStatus(InterviewStatus.ACTIVE);
        Interview toTest = repository.save(interview);

        toTest.setStartDate(null);
        repository.save(toTest);
    }

    public void changeInterviewWithEndDateNull(){
        interview = new Interview();
        interview.setAuditId(audit.getId());
        interview.setStartDate(Date.valueOf("2020-01-01"));
        interview.setEndDate(Date.valueOf("2020-01-02"));
        interview.setAnnotation("TestAnnotation");
        interview.setStatus(InterviewStatus.ACTIVE);
        Interview interview1 = repository.save(interview);

        interview1.setEndDate(null);
        Interview toTest = repository.save(interview1);

        Assert.assertTrue(repository.exists(Example.of(toTest)));
    }

    public void changeInterviewStartDate(){
        interview = new Interview();
        interview.setAuditId(audit.getId());
        interview.setStartDate(Date.valueOf("2020-01-01"));
        interview.setEndDate(Date.valueOf("2020-01-02"));
        interview.setAnnotation("TestAnnotation");
        interview.setStatus(InterviewStatus.ACTIVE);
        Interview interview1 = repository.save(interview);

        interview1.setStartDate(Date.valueOf("2020-01-05"));
        Interview toTest = repository.save(interview1);

        Assert.assertTrue(repository.exists(Example.of(toTest)));
    }

    public void changeInterviewEndDate(){
        interview = new Interview();
        interview.setAuditId(audit.getId());
        interview.setStartDate(Date.valueOf("2020-01-01"));
        interview.setEndDate(Date.valueOf("2020-01-02"));
        interview.setAnnotation("TestAnnotation");
        interview.setStatus(InterviewStatus.ACTIVE);
        Interview interview1 = repository.save(interview);

        interview1.setEndDate(Date.valueOf("2020-01-05"));
        Interview toTest = repository.save(interview1);

        Assert.assertTrue(repository.exists(Example.of(toTest)));
    }

    @Test
    public void changeInterviewAnnotationWithNull(){
        interview = new Interview();
        interview.setAuditId(audit.getId());
        interview.setStartDate(Date.valueOf("2020-01-01"));
        interview.setEndDate(Date.valueOf("2020-01-02"));
        interview.setAnnotation("TestAnnotation");
        interview.setStatus(InterviewStatus.ACTIVE);
        Interview interview1 = repository.save(interview);

        interview1.setAnnotation(null);
        Interview toTest = repository.save(interview1);

        Assert.assertTrue(repository.exists(Example.of(toTest)));
    }

    @Test
    public void changeInterviewAnnotation(){
        interview = new Interview();
        interview.setAuditId(audit.getId());
        interview.setStartDate(Date.valueOf("2020-01-01"));
        interview.setEndDate(Date.valueOf("2020-01-02"));
        interview.setAnnotation("TestAnnotation");
        interview.setStatus(InterviewStatus.ACTIVE);
        Interview interview1 = repository.save(interview);

        interview1.setAnnotation("NewTestAnnotation");
        Interview toTest = repository.save(interview1);

        Assert.assertTrue(repository.exists(Example.of(toTest)));
    }

    @Test(expected = TransactionSystemException.class)
    public void changeInterviewStatusWithNull(){
        interview = new Interview();
        interview.setAuditId(audit.getId());
        interview.setStartDate(Date.valueOf("2020-01-01"));
        interview.setEndDate(Date.valueOf("2020-01-02"));
        interview.setAnnotation("TestAnnotation");
        interview.setStatus(InterviewStatus.ACTIVE);
        Interview interview1 = repository.save(interview);

        interview1.setStatus(null);
        repository.save(interview1);
    }

    @Test(expected = TransactionSystemException.class)
    public void changeInterviewStatus(){
        interview = new Interview();
        interview.setAuditId(audit.getId());
        interview.setStartDate(Date.valueOf("2020-01-01"));
        interview.setEndDate(Date.valueOf("2020-01-02"));
        interview.setAnnotation("TestAnnotation");
        interview.setStatus(InterviewStatus.ACTIVE);
        Interview interview1 = repository.save(interview);

        interview1.setStatus(InterviewStatus.FINISHED);
        Interview toTest = repository.save(interview1);

        Assert.assertTrue(repository.exists(Example.of(toTest)));
    }

    @Test
    public void deleteInterviewEntity(){
        interview = new Interview();
        interview.setAuditId(audit.getId());
        interview.setStartDate(Date.valueOf("2020-01-01"));
        interview.setEndDate(Date.valueOf("2020-01-02"));
        interview.setAnnotation("TestAnnotation");
        interview.setStatus(InterviewStatus.ACTIVE);
        Interview toTest = repository.save(interview);

        repository.delete(toTest);

        Assert.assertFalse(repository.exists(Example.of(toTest)));
    }

    @After
    public void tearDown(){
        repository.delete(interview);
        auditRepository.delete(audit);
    }

}
