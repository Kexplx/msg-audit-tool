package com.amos2020.javabackend.repository;


import com.amos2020.javabackend.entity.Audit;
import com.amos2020.javabackend.entity.AuditStatus;
import com.amos2020.javabackend.entity.ContactPerson;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;

/**
 * Test class for the AuditRepository
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuditRepositoryTest {

    @Autowired
    private AuditRepository repository;
    @Autowired
    private ContactPersonRepository contactPersonRepository;
    private Audit toTest;
    private ContactPerson cancellationContactPerson;

    private static final String TEST_NAME = "TestName";
    private static final Date TEST_START_DATE = Date.valueOf("2019-10-10");
    private static final Date TEST_END_DATE = Date.valueOf("2020-02-08");
    private static final Date TEST_CANCELLATION_DATE = Date.valueOf("2020-02-10");
    private static final String TEST_CANCELLATION_REASON = "Test Reason";
    private static final String TEST_TITLE = "TestTitle";
    private static final String TEST_INFORMATION = "0123456789, valid@email.com";
    private static final String TEST_FORENAME = "Jon";
    private static final String TEST_SURNAME = "Doe";
    private static final String TEST_COMPANY = "testCompany";
    private static final String TEST_DEPARTMENT = "testDepartment";
    private static final String TEST_SECTOR = "testSector";

    @Before
    public void setUp() {
        cancellationContactPerson = new ContactPerson();
        cancellationContactPerson.setTitle(TEST_TITLE);
        cancellationContactPerson.setContactInformation(TEST_INFORMATION);
        cancellationContactPerson.setForename(TEST_FORENAME);
        cancellationContactPerson.setSurname(TEST_SURNAME);
        cancellationContactPerson.setCompanyName(TEST_COMPANY);
        cancellationContactPerson.setDepartment(TEST_DEPARTMENT);
        cancellationContactPerson.setSector(TEST_SECTOR);
        contactPersonRepository.save(cancellationContactPerson);
    }


    @Test
    public void insertAuditWithValidData_isSuccessful() {
        toTest = new Audit();
        toTest.setName(TEST_NAME);
        toTest.setStartDate(TEST_START_DATE);
        toTest.setEndDate(TEST_END_DATE);
        toTest.setStatus(AuditStatus.ACTIVE);
        toTest.setCreationDate(Timestamp.from(Instant.now()));
        repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));
    }

    @Test(expected = TransactionSystemException.class)
    public void insertAuditWithNameIsNull_throwsException() {
        toTest = new Audit();
        toTest.setStartDate(TEST_START_DATE);
        toTest.setEndDate(TEST_END_DATE);
        toTest.setStatus(AuditStatus.ACTIVE);
        toTest.setCreationDate(Timestamp.from(Instant.now()));
        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertAuditWithNameIsEmpty_throwsException() {
        toTest = new Audit();
        toTest.setName("");
        toTest.setStartDate(TEST_START_DATE);
        toTest.setEndDate(TEST_END_DATE);
        toTest.setStatus(AuditStatus.ACTIVE);
        toTest.setCreationDate(Timestamp.from(Instant.now()));
        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertAuditWithNameIsBlank_throwsException() {
        toTest = new Audit();
        toTest.setName("   ");
        toTest.setStartDate(TEST_START_DATE);
        toTest.setEndDate(TEST_END_DATE);
        toTest.setStatus(AuditStatus.ACTIVE);
        toTest.setCreationDate(Timestamp.from(Instant.now()));
        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertAuditWithStartDateIsNull_throwsException() {
        toTest = new Audit();
        toTest.setName(TEST_NAME);
        toTest.setEndDate(TEST_END_DATE);
        toTest.setStatus(AuditStatus.ACTIVE);
        toTest.setCreationDate(Timestamp.from(Instant.now()));
        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertAuditWithStatusIsNull_throwsException() {
        toTest = new Audit();
        toTest.setName(TEST_NAME);
        toTest.setStartDate(TEST_START_DATE);
        toTest.setEndDate(TEST_END_DATE);
        toTest.setCreationDate(Timestamp.from(Instant.now()));
        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertAuditWithCreationDateIsNull_throwsException() {
        toTest = new Audit();
        toTest.setName(TEST_NAME);
        toTest.setStartDate(TEST_START_DATE);
        toTest.setEndDate(TEST_END_DATE);
        toTest.setStatus(AuditStatus.ACTIVE);
        repository.save(toTest);
    }

    @Test
    public void insertCanceledAudit_isSuccessful() {
        toTest = new Audit();
        toTest.setName(TEST_NAME);
        toTest.setStartDate(TEST_START_DATE);
        toTest.setEndDate(TEST_END_DATE);
        toTest.setStatus(AuditStatus.CANCELED);
        toTest.setCreationDate(Timestamp.from(Instant.now()));
        toTest.setCancellationContactPerson(cancellationContactPerson.getId());
        toTest.setCancellationDate(TEST_CANCELLATION_DATE);
        toTest.setCancellationReason(TEST_CANCELLATION_REASON);
        repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));

    }

    @Test
    public void deleteExistingAudit_isSuccessful() {
        toTest = new Audit();
        toTest.setName(TEST_NAME);
        toTest.setStartDate(TEST_START_DATE);
        toTest.setEndDate(TEST_END_DATE);
        toTest.setStatus(AuditStatus.ACTIVE);
        toTest.setCreationDate(Timestamp.from(Instant.now()));
        repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));

        repository.delete(toTest);
        Assert.assertFalse(repository.exists((Example.of(toTest))));

    }

    @Test
    public void updateAuditWithValidName_isSuccessful() {
        toTest = new Audit();
        toTest.setName(TEST_NAME);
        toTest.setStartDate(TEST_START_DATE);
        toTest.setEndDate(TEST_END_DATE);
        toTest.setStatus(AuditStatus.ACTIVE);
        toTest.setCreationDate(Timestamp.from(Instant.now()));
        Audit AuditEntity = repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));

        AuditEntity.setName("New Name");
        repository.save(AuditEntity);
        Assert.assertNotEquals(TEST_NAME, toTest.getName());
    }

    @Test(expected = TransactionSystemException.class)
    public void updateAuditWithInvalidName_throwsException() {
        toTest = new Audit();
        toTest.setName(TEST_NAME);
        toTest.setStartDate(TEST_START_DATE);
        toTest.setEndDate(TEST_END_DATE);
        toTest.setStatus(AuditStatus.ACTIVE);
        toTest.setCreationDate(Timestamp.from(Instant.now()));
        Audit AuditEntity = repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));

        AuditEntity.setName("  ");
        repository.save(AuditEntity);
    }

    @Test
    public void updateAuditWithValidStartDate_isSuccessful() {
        toTest = new Audit();
        toTest.setName(TEST_NAME);
        toTest.setStartDate(TEST_START_DATE);
        toTest.setEndDate(TEST_END_DATE);
        toTest.setStatus(AuditStatus.ACTIVE);
        toTest.setCreationDate(Timestamp.from(Instant.now()));
        Audit AuditEntity = repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));

        AuditEntity.setStartDate(Date.valueOf("2019-10-12"));
        repository.save(AuditEntity);
        Assert.assertNotEquals(TEST_START_DATE, toTest.getStartDate());
    }

    @Test(expected = TransactionSystemException.class)
    public void updateAuditWithInvalidStartDate_throwsException() {
        toTest = new Audit();
        toTest.setName(TEST_NAME);
        toTest.setStartDate(TEST_START_DATE);
        toTest.setEndDate(TEST_END_DATE);
        toTest.setStatus(AuditStatus.ACTIVE);
        toTest.setCreationDate(Timestamp.from(Instant.now()));
        Audit AuditEntity = repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));

        AuditEntity.setStartDate(null);
        repository.save(AuditEntity);
    }

    @Test
    public void updateAuditWithValidEndDate_isSuccessful() {
        toTest = new Audit();
        toTest.setName(TEST_NAME);
        toTest.setStartDate(TEST_START_DATE);
        toTest.setEndDate(TEST_END_DATE);
        toTest.setStatus(AuditStatus.ACTIVE);
        toTest.setCreationDate(Timestamp.from(Instant.now()));
        Audit AuditEntity = repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));

        AuditEntity.setEndDate(Date.valueOf("2012-04-04"));
        repository.save(AuditEntity);
        Assert.assertNotEquals(TEST_END_DATE, toTest.getEndDate());
    }

    @Test
    public void updateAuditWithEndDateIsNull_isSuccessful() {
        toTest = new Audit();
        toTest.setName(TEST_NAME);
        toTest.setStartDate(TEST_START_DATE);
        toTest.setEndDate(TEST_END_DATE);
        toTest.setStatus(AuditStatus.ACTIVE);
        toTest.setCreationDate(Timestamp.from(Instant.now()));
        Audit AuditEntity = repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));

        AuditEntity.setEndDate(null);
        repository.save(AuditEntity);
        Assert.assertNotEquals(TEST_END_DATE, toTest.getEndDate());
    }

    @Test
    public void updateAuditWithValidStatus_isSuccessful() {
        toTest = new Audit();
        toTest.setName(TEST_NAME);
        toTest.setStartDate(TEST_START_DATE);
        toTest.setEndDate(TEST_END_DATE);
        toTest.setStatus(AuditStatus.ACTIVE);
        toTest.setCreationDate(Timestamp.from(Instant.now()));
        Audit AuditEntity = repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));

        AuditEntity.setStatus(AuditStatus.FINISHED);
        repository.save(AuditEntity);
        Assert.assertNotEquals(AuditStatus.ACTIVE, toTest.getStatus());
    }

    @Test(expected = TransactionSystemException.class)
    public void updateAuditWithInvalidStatus_throwsException() {
        toTest = new Audit();
        toTest.setName(TEST_NAME);
        toTest.setStartDate(TEST_START_DATE);
        toTest.setEndDate(TEST_END_DATE);
        toTest.setStatus(AuditStatus.ACTIVE);
        toTest.setCreationDate(Timestamp.from(Instant.now()));
        Audit AuditEntity = repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));

        AuditEntity.setStatus(null);
        repository.save(AuditEntity);
    }

    @After
    public void tearDown() {
        repository.delete(toTest);
        contactPersonRepository.delete(cancellationContactPerson);
    }
}
