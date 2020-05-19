package com.amos2020.javabackend.repository;

import com.amos2020.javabackend.entity.Audit;
import com.amos2020.javabackend.entity.ContactPerson;
import com.amos2020.javabackend.entity.Interview;
import com.amos2020.javabackend.entity.InterviewContactPerson;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.PersistenceUnit;
import java.sql.Date;
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class InterviewContactPersonRepositoryTest {

    @Autowired
    private InterviewContactPersonRepository repository;
    @Autowired
    private ContactPersonRepository contactPersonRepository;
    @Autowired
    private InterviewRepository interviewRepository;
    @Autowired
    private AuditRepository auditRepository;

    private ContactPerson contactPerson;
    private Interview interview;
    private Audit audit;


    private static final String TEST_NAME = "TestName";
    private static final Date TEST_START_DATE = Date.valueOf("2019-10-10");
    private static final Date TEST_EXPECTED_END_DATE = Date.valueOf("2020-02-02");
    private static final Date TEST_END_DATE = Date.valueOf("2020-02-08");
    private static final String TEST_TITLE = "TestTitle";
    private static final String TEST_INFORMATION = "0123456789, valid@email.com";
    private static final String TEST_FORENAME = "Jon";
    private static final String TEST_SURNAME = "Doe";
    private static final String TEST_COMPANY = "testCompany";
    private static final String TEST_DEPARTMENT = "testDepartment";
    private static final String TEST_SECTOR = "testSector";



    @Before
    public void before() {

        audit = new Audit();
        audit.setName(TEST_NAME);
        audit.setStartDate(TEST_START_DATE);
        audit.setEndDate(TEST_END_DATE);
        audit.setExpectedEndDate(TEST_EXPECTED_END_DATE);
        auditRepository.save(audit);
        Assert.assertTrue(auditRepository.exists((Example.of(audit))));

        contactPerson = new ContactPerson();
        contactPerson.setTitle(TEST_TITLE);
        contactPerson.setContactInformation(TEST_INFORMATION);
        contactPerson.setForename(TEST_FORENAME);
        contactPerson.setSurname(TEST_SURNAME);
        contactPerson.setCompanyName(TEST_COMPANY);
        contactPerson.setDepartment(TEST_DEPARTMENT);
        contactPerson.setSector(TEST_SECTOR);
        contactPersonRepository.save(contactPerson);
        Assert.assertTrue(contactPersonRepository.exists((Example.of(contactPerson))));

        interview = new Interview();
        interview.setInterviewAuditId(audit.getId());
        interview.setInterviewDate(Date.valueOf("2020-05-19"));
        interviewRepository.save(interview);
        Assert.assertTrue(interviewRepository.exists((Example.of(interview))));
    }

    @Test
    public void insertValid() {
        InterviewContactPerson interviewContactPerson = new InterviewContactPerson();
        interviewContactPerson.setInterviewcontactpersonContactpersonId(contactPerson.getId());
        interviewContactPerson.setInterviewcontactpersonInterviewId(interview.getInterviewId());
        repository.save(interviewContactPerson);
        Assert.assertTrue(repository.exists((Example.of(interviewContactPerson))));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void insertInvalidInterview() {
        InterviewContactPerson interviewContactPerson = new InterviewContactPerson();
        interviewContactPerson.setInterviewcontactpersonContactpersonId(contactPerson.getId());
        interviewContactPerson.setInterviewcontactpersonInterviewId(9999);
        repository.save(interviewContactPerson);
        Assert.assertFalse(repository.exists((Example.of(interviewContactPerson))));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void insertInvalidContactPerson() {
        InterviewContactPerson interviewContactPerson = new InterviewContactPerson();
        interviewContactPerson.setInterviewcontactpersonContactpersonId(9999);
        interviewContactPerson.setInterviewcontactpersonInterviewId(interview.getInterviewId());
        repository.save(interviewContactPerson);
        Assert.assertFalse(repository.exists((Example.of(interviewContactPerson))));
    }

    @Test
    public void changeValidContactPerson() {
        ContactPerson contactPerson_new = new ContactPerson();
        contactPerson_new.setTitle(TEST_TITLE);
        contactPerson_new.setContactInformation(TEST_INFORMATION);
        contactPerson_new.setForename(TEST_FORENAME + "new");
        contactPerson_new.setSurname(TEST_SURNAME + "new");
        contactPerson_new.setCompanyName(TEST_COMPANY + "new");
        contactPerson_new.setDepartment(TEST_DEPARTMENT + "new");
        contactPerson_new.setSector(TEST_SECTOR + "new");
        contactPersonRepository.save(contactPerson_new);
        Assert.assertTrue(contactPersonRepository.exists((Example.of(contactPerson_new))));

        InterviewContactPerson interviewContactPerson = new InterviewContactPerson();
        interviewContactPerson.setInterviewcontactpersonContactpersonId(contactPerson.getId());
        interviewContactPerson.setInterviewcontactpersonInterviewId(interview.getInterviewId());
        InterviewContactPerson tmp = repository.save(interviewContactPerson);
        Assert.assertTrue(repository.exists((Example.of(interviewContactPerson))));

        tmp.setInterviewcontactpersonContactpersonId(contactPerson_new.getId());
        repository.save(tmp);
        Assert.assertTrue(repository.exists((Example.of(tmp))));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void changeInvalidContactPerson() {
        InterviewContactPerson interviewContactPerson = new InterviewContactPerson();
        interviewContactPerson.setInterviewcontactpersonContactpersonId(contactPerson.getId());
        interviewContactPerson.setInterviewcontactpersonInterviewId(interview.getInterviewId());
        InterviewContactPerson tmp = repository.save(interviewContactPerson);
        Assert.assertTrue(repository.exists((Example.of(interviewContactPerson))));

        tmp.setInterviewcontactpersonContactpersonId(9999);
        repository.save(tmp);

    }

    @Test
    public void changeValidInterview() {

        Interview interview_new = new Interview();
        interview_new.setInterviewAuditId(audit.getId());
        interview_new.setInterviewDate(Date.valueOf("2020-05-20"));
        interviewRepository.save(interview_new);
        Assert.assertTrue(interviewRepository.exists((Example.of(interview_new))));

        InterviewContactPerson interviewContactPerson = new InterviewContactPerson();
        interviewContactPerson.setInterviewcontactpersonContactpersonId(contactPerson.getId());
        interviewContactPerson.setInterviewcontactpersonInterviewId(interview.getInterviewId());
        InterviewContactPerson tmp = repository.save(interviewContactPerson);
        Assert.assertTrue(repository.exists((Example.of(interviewContactPerson))));

        tmp.setInterviewcontactpersonInterviewId(interview_new.getInterviewId());
        repository.save(tmp);
        Assert.assertTrue(repository.exists((Example.of(tmp))));

    }

    @Test(expected = DataIntegrityViolationException.class)
    public void changeInvalidInterview() {
        InterviewContactPerson interviewContactPerson = new InterviewContactPerson();
        interviewContactPerson.setInterviewcontactpersonContactpersonId(contactPerson.getId());
        interviewContactPerson.setInterviewcontactpersonInterviewId(interview.getInterviewId());
        InterviewContactPerson tmp = repository.save(interviewContactPerson);
        Assert.assertTrue(repository.exists((Example.of(interviewContactPerson))));

        tmp.setInterviewcontactpersonInterviewId(9999);
        repository.save(tmp);


    }
}
