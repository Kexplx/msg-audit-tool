package com.amos2020.javabackend.repository;

import com.amos2020.javabackend.entity.Audit;
import com.amos2020.javabackend.entity.AuditContactPerson;
import com.amos2020.javabackend.entity.ContactPerson;
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
    public void setUp() {
        audit = new Audit();
        audit.setAuditName(TEST_NAME);
        audit.setAuditStartDate(TEST_START_DATE);
        audit.setAuditEndDate(TEST_END_DATE);
        audit.setAuditExpectedEndDate(TEST_EXPECTED_END_DATE);
        auditRepository.save(audit);
        Assert.assertTrue(auditRepository.exists((Example.of(audit))));

        contactPerson = new ContactPerson();
        contactPerson.setContactPersonTitle(TEST_TITLE);
        contactPerson.setContactPersonContactInformation(TEST_INFORMATION);
        contactPerson.setContactPersonForename(TEST_FORENAME);
        contactPerson.setContactPersonSurname(TEST_SURNAME);
        contactPerson.setContactPersonCompanyName(TEST_COMPANY);
        contactPerson.setContactPersonDepartment(TEST_DEPARTMENT);
        contactPerson.setContactPersonSector(TEST_SECTOR);
        contactPersonRepository.save(contactPerson);
        Assert.assertTrue(contactPersonRepository.exists((Example.of(contactPerson))));
    }

    @Test
    public void insertAuditContactPersonWithValidData_isSuccessful() {
        toTest = new AuditContactPerson();
        toTest.setAuditcontactpersonAuditId(audit.getAuditId());
        toTest.setAuditcontactpersonContactpersonId(contactPerson.getContactPersonId());
        repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));
    }

    @Test
    public void insertAuditContactPersonTwice_onlyOneIsInserted() {
        toTest = new AuditContactPerson();
        toTest.setAuditcontactpersonAuditId(audit.getAuditId());
        toTest.setAuditcontactpersonContactpersonId(contactPerson.getContactPersonId());
        repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));
        AuditContactPerson auditContactPerson = new AuditContactPerson();
        auditContactPerson.setAuditcontactpersonAuditId(audit.getAuditId());
        auditContactPerson.setAuditcontactpersonContactpersonId(contactPerson.getContactPersonId());
        repository.save(auditContactPerson);
        Assert.assertTrue(repository.exists((Example.of(auditContactPerson))));
        Assert.assertEquals(1, repository.findAll().size());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void insertAuditContactPersonWithAuditIdIsNull_throwsException() {
        toTest = new AuditContactPerson();
        toTest.setAuditcontactpersonContactpersonId(contactPerson.getContactPersonId());
        repository.save(toTest);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void insertAuditContactPersonWithContactPersonIdIsNull_throwsException() {
        toTest = new AuditContactPerson();
        toTest.setAuditcontactpersonAuditId(audit.getAuditId());
        repository.save(toTest);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void insertAuditContactPersonWithAuditIdIsNotExisting_throwsException() {
        toTest = new AuditContactPerson();
        toTest.setAuditcontactpersonAuditId(audit.getAuditId()+1);
        toTest.setAuditcontactpersonContactpersonId(contactPerson.getContactPersonId());
        repository.save(toTest);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void insertAuditContactPersonWithContactPersonIdIsNotExisting_throwsException() {
        toTest = new AuditContactPerson();
        toTest.setAuditcontactpersonAuditId(audit.getAuditId());
        toTest.setAuditcontactpersonContactpersonId(contactPerson.getContactPersonId()+1);
        repository.save(toTest);
    }

    @After
    public void tearDown() {
        repository.delete(toTest);
        contactPersonRepository.delete(contactPerson);
        auditRepository.delete(audit);
    }
}
