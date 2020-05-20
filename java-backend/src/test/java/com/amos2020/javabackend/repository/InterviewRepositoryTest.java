package com.amos2020.javabackend.repository;

import com.amos2020.javabackend.entity.Audit;
import com.amos2020.javabackend.entity.Interview;
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
        interview.setInterviewAuditId(audit.getId());
        interview.setInterviewDate(Date.valueOf("2020-01-01"));
        interview.setInterviewAnnotation("TestAnnotation");

        repository.save(interview);

        Assert.assertTrue(repository.exists(Example.of(interview)));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void insertInterviewWithAuditIdNull(){
        interview = new Interview();
        interview.setInterviewDate(Date.valueOf("2020-01-01"));
        interview.setInterviewAnnotation("TestAnnotation");

        repository.save(interview);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertInterviewWithDateNull(){
        interview = new Interview();
        interview.setInterviewAuditId(audit.getId());
        interview.setInterviewAnnotation("TestAnnotation");

        repository.save(interview);
    }

    @Test
    public void insertInterviewAnnotationWithNull(){
        interview = new Interview();
        interview.setInterviewAuditId(audit.getId());
        interview.setInterviewDate(Date.valueOf("2020-01-01"));

        repository.save(interview);

        Assert.assertTrue(repository.exists(Example.of(interview)));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void changeInterviewWithAuditIdInvalid(){
        interview = new Interview();
        interview.setInterviewAuditId(audit.getId());
        interview.setInterviewDate(Date.valueOf("2020-01-01"));
        interview.setInterviewAnnotation("TestAnnotation");
        Interview toTest = repository.save(interview);

        toTest.setInterviewAuditId(-1);
        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void changeInterviewWithDateNull(){
        interview = new Interview();
        interview.setInterviewAuditId(audit.getId());
        interview.setInterviewDate(Date.valueOf("2020-01-01"));
        interview.setInterviewAnnotation("TestAnnotation");
        Interview toTest = repository.save(interview);

        toTest.setInterviewDate(null);
        repository.save(toTest);
    }

    public void changeInterviewDate(){
        interview = new Interview();
        interview.setInterviewAuditId(audit.getId());
        interview.setInterviewDate(Date.valueOf("2020-01-01"));
        interview.setInterviewAnnotation("TestAnnotation");
        Interview interview1 = repository.save(interview);

        interview1.setInterviewDate(Date.valueOf("2020-01-05"));
        Interview toTest = repository.save(interview1);

        Assert.assertTrue(repository.exists(Example.of(toTest)));
    }

    @Test
    public void changeInterviewAnnotationWithNull(){
        interview = new Interview();
        interview.setInterviewAuditId(audit.getId());
        interview.setInterviewDate(Date.valueOf("2020-01-01"));
        interview.setInterviewAnnotation("TestAnnotation");
        Interview interview1 = repository.save(interview);

        interview1.setInterviewAnnotation(null);
        Interview toTest = repository.save(interview1);

        Assert.assertTrue(repository.exists(Example.of(toTest)));
    }

    @Test
    public void changeInterviewAnnotation(){
        interview = new Interview();
        interview.setInterviewAuditId(audit.getId());
        interview.setInterviewDate(Date.valueOf("2020-01-01"));
        interview.setInterviewAnnotation("TestAnnotation");
        Interview interview1 = repository.save(interview);

        interview1.setInterviewAnnotation("NewTestAnnotation");
        Interview toTest = repository.save(interview1);

        Assert.assertTrue(repository.exists(Example.of(toTest)));
    }

    @Test
    public void deleteInterviewEntity(){
        interview = new Interview();
        interview.setInterviewAuditId(audit.getId());
        interview.setInterviewDate(Date.valueOf("2020-01-01"));
        interview.setInterviewAnnotation("TestAnnotation");
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
