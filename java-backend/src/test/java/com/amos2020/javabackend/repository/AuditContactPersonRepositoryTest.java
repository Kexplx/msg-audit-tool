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

/**
 * Test class for the AuditContactPersonRepository
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuditContactPersonRepositoryTest {

    @Autowired
    private AuditRepository auditRepository;

    @Autowired
    private ContactPersonRepository contactPersonRepository;

    @Autowired
    private AuditContactPersonRepository repository;


    private Audit audit;
    private ContactPerson contactPerson;
    private AuditContactPerson toTest;

    private static final String TEST_NAME = "TestName";
    private static final Date TEST_START_DATE = Date.valueOf("2019-10-10");
    private static final Date TEST_END_DATE = Date.valueOf("2020-02-08");
    private static final Salutation TEST_SALUTATION = Salutation.MANN;
    private static final String TEST_TITLE = "TestTitle";
    private static final String TEST_INFORMATION = "0123456789, valid@email.com";
    private static final String TEST_FORENAME = "Jon";
    private static final String TEST_SURNAME = "Doe";
    private static final String TEST_COMPANY = "testCompany";
    private static final String TEST_DEPARTMENT = "testDepartment";
    private static final String TEST_SECTOR = "testSector";
    private static final String TEST_CORPORATE_DIVISION = "testDivision";

    @Before
    public void setUp() {
        audit = new Audit();
        audit.setName(TEST_NAME);
        audit.setStartDate(TEST_START_DATE);
        audit.setEndDate(TEST_END_DATE);
        audit.setStatus(AuditStatus.ACTIVE);
        audit.setCreationDate(Timestamp.from(Instant.now()));
        auditRepository.save(audit);
        Assert.assertTrue(auditRepository.exists((Example.of(audit))));

        contactPerson = new ContactPerson();
        contactPerson.setSalutation(TEST_SALUTATION);
        contactPerson.setTitle(TEST_TITLE);
        contactPerson.setContactInformation(TEST_INFORMATION);
        contactPerson.setForename(TEST_FORENAME);
        contactPerson.setSurname(TEST_SURNAME);
        contactPerson.setCompanyName(TEST_COMPANY);
        contactPerson.setDepartment(TEST_DEPARTMENT);
        contactPerson.setSector(TEST_SECTOR);
        contactPerson.setCorporateDivision(TEST_CORPORATE_DIVISION);
        contactPersonRepository.save(contactPerson);
        Assert.assertTrue(contactPersonRepository.exists((Example.of(contactPerson))));
    }

    @Test
    public void insertAuditContactPersonWithValidData_isSuccessful() {
        toTest = new AuditContactPerson();
        toTest.setAuditId(audit.getId());
        toTest.setContactPersonId(contactPerson.getId());
        repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));
    }

    @Test
    public void insertAuditContactPersonTwice_onlyOneIsInserted() {
        toTest = new AuditContactPerson();
        toTest.setAuditId(audit.getId());
        toTest.setContactPersonId(contactPerson.getId());
        repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));
        AuditContactPerson auditContactPerson = new AuditContactPerson();
        auditContactPerson.setAuditId(audit.getId());
        auditContactPerson.setContactPersonId(contactPerson.getId());
        repository.save(auditContactPerson);
        Assert.assertTrue(repository.exists((Example.of(auditContactPerson))));
        Assert.assertEquals(1, repository.findAll().size());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void insertAuditContactPersonWithAuditIdIsNull_throwsException() {
        toTest = new AuditContactPerson();
        toTest.setContactPersonId(contactPerson.getId());
        repository.save(toTest);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void insertAuditContactPersonWithContactPersonIdIsNull_throwsException() {
        toTest = new AuditContactPerson();
        toTest.setAuditId(audit.getId());
        repository.save(toTest);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void insertAuditContactPersonWithAuditIdIsNotExisting_throwsException() {
        toTest = new AuditContactPerson();
        toTest.setAuditId(audit.getId() + 1);
        toTest.setContactPersonId(contactPerson.getId());
        repository.save(toTest);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void insertAuditContactPersonWithContactPersonIdIsNotExisting_throwsException() {
        toTest = new AuditContactPerson();
        toTest.setAuditId(audit.getId());
        toTest.setContactPersonId(contactPerson.getId() + 1);
        repository.save(toTest);
    }

    @Test
    public void deleteAuditContactPersonWithValidData_isSuccessful() {
        toTest = new AuditContactPerson();
        toTest.setAuditId(audit.getId());
        toTest.setContactPersonId(contactPerson.getId());
        repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));

        repository.delete(toTest);
        Assert.assertFalse(repository.exists((Example.of(toTest))));
    }

    @After
    public void tearDown() {
        repository.delete(toTest);
        contactPersonRepository.delete(contactPerson);
        auditRepository.delete(audit);
    }
}
