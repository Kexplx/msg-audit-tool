package com.amos2020.javabackend.repository;


import com.amos2020.javabackend.entity.Audit;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;

import java.sql.Date;

/**
 * Test class for the AuditRepository
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuditRepositoryTest {

    @Autowired
    private AuditRepository repository;

    private Audit toTest;

    private static final String TEST_NAME = "TestName";
    private static final Date TEST_START_DATE = Date.valueOf("2019-10-10");
    private static final Date TEST_EXPECTED_END_DATE = Date.valueOf("2020-02-02");
    private static final Date TEST_END_DATE = Date.valueOf("2020-02-08");


    @Test
    public void insertAuditWithValidData_isSuccessful() {
        toTest = new Audit();
        toTest.setName(TEST_NAME);
        toTest.setStartDate(TEST_START_DATE);
        toTest.setEndDate(TEST_END_DATE);
        toTest.setExpectedEndDate(TEST_EXPECTED_END_DATE);
        repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));
    }

    @Test(expected = TransactionSystemException.class)
    public void insertAuditWithNameIsNull_throwsException() {
        toTest = new Audit();
        toTest.setStartDate(TEST_START_DATE);
        toTest.setEndDate(TEST_END_DATE);
        toTest.setExpectedEndDate(TEST_EXPECTED_END_DATE);

        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertAuditWithNameIsEmpty_throwsException() {
        toTest = new Audit();
        toTest.setName("");
        toTest.setStartDate(TEST_START_DATE);
        toTest.setEndDate(TEST_END_DATE);
        toTest.setExpectedEndDate(TEST_EXPECTED_END_DATE);

        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertAuditWithNameIsBlank_throwsException() {
        toTest = new Audit();
        toTest.setName("   ");
        toTest.setStartDate(TEST_START_DATE);
        toTest.setEndDate(TEST_END_DATE);
        toTest.setExpectedEndDate(TEST_EXPECTED_END_DATE);

        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertAuditWithStartDateIsNull_throwsException() {
        toTest = new Audit();
        toTest.setName(TEST_NAME);
        toTest.setEndDate(TEST_END_DATE);
        toTest.setExpectedEndDate(TEST_EXPECTED_END_DATE);

        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertAuditWithExpectedEndDateIsNull_throwsException() {
        toTest = new Audit();
        toTest.setName(TEST_NAME);
        toTest.setStartDate(TEST_START_DATE);
        toTest.setEndDate(TEST_END_DATE);
        repository.save(toTest);
    }

    @Test
    public void deleteExistingAudit_isSuccessful() {
        toTest = new Audit();
        toTest.setName(TEST_NAME);
        toTest.setStartDate(TEST_START_DATE);
        toTest.setEndDate(TEST_END_DATE);
        toTest.setExpectedEndDate(TEST_EXPECTED_END_DATE);
        repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));

        repository.delete(toTest);
        Assert.assertFalse(repository.exists((Example.of(toTest))));

    }

    @Test
    public void changeAuditWithValidName_isSuccessful() {
        toTest = new Audit();
        toTest.setName(TEST_NAME);
        toTest.setStartDate(TEST_START_DATE);
        toTest.setEndDate(TEST_END_DATE);
        toTest.setExpectedEndDate(TEST_EXPECTED_END_DATE);

        Audit AuditEntity = repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));

        AuditEntity.setName("New Name");
        repository.save(AuditEntity);
        Assert.assertNotEquals(TEST_NAME, toTest.getName());
    }

    @Test(expected = TransactionSystemException.class)
    public void changeAuditWithInvalidName_throwsException() {
        toTest = new Audit();
        toTest.setName(TEST_NAME);
        toTest.setStartDate(TEST_START_DATE);
        toTest.setEndDate(TEST_END_DATE);
        toTest.setExpectedEndDate(TEST_EXPECTED_END_DATE);

        Audit AuditEntity = repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));

        AuditEntity.setName("  ");
        repository.save(AuditEntity);
    }

    @Test

    public void changeAuditWithValidStartDate_isSuccessful() {
        toTest = new Audit();
        toTest.setName(TEST_NAME);
        toTest.setStartDate(TEST_START_DATE);
        toTest.setEndDate(TEST_END_DATE);
        toTest.setExpectedEndDate(TEST_EXPECTED_END_DATE);

        Audit AuditEntity = repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));

        AuditEntity.setStartDate(Date.valueOf("2019-10-12"));
        repository.save(AuditEntity);
        Assert.assertNotEquals(TEST_START_DATE, toTest.getStartDate());
    }

    @Test(expected = TransactionSystemException.class)
    public void changeAuditWithInvalidStartDate_throwsException() {
        toTest = new Audit();
        toTest.setName(TEST_NAME);
        toTest.setStartDate(TEST_START_DATE);
        toTest.setEndDate(TEST_END_DATE);
        toTest.setExpectedEndDate(TEST_EXPECTED_END_DATE);

        Audit AuditEntity = repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));

        AuditEntity.setStartDate(null);
        repository.save(AuditEntity);
    }

    @Test
    public void changeAuditWithValidEndDate_isSuccessful() {
        toTest = new Audit();
        toTest.setName(TEST_NAME);
        toTest.setStartDate(TEST_START_DATE);
        toTest.setEndDate(TEST_END_DATE);
        toTest.setExpectedEndDate(TEST_EXPECTED_END_DATE);

        Audit AuditEntity = repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));

        AuditEntity.setEndDate(Date.valueOf("2012-04-04"));
        repository.save(AuditEntity);
        Assert.assertNotEquals(TEST_END_DATE, toTest.getEndDate());
    }

    @Test
    public void changeAuditWithInvalidEndDate_isSuccessful() {
        toTest = new Audit();
        toTest.setName(TEST_NAME);
        toTest.setStartDate(TEST_START_DATE);
        toTest.setEndDate(TEST_END_DATE);
        toTest.setExpectedEndDate(TEST_EXPECTED_END_DATE);

        Audit AuditEntity = repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));

        AuditEntity.setEndDate(null);
        repository.save(AuditEntity);
        Assert.assertNotEquals(TEST_END_DATE, toTest.getEndDate());
    }

    @Test
    public void changeAuditWithValidExpectedEndDate_isSuccessful() {
        toTest = new Audit();
        toTest.setName(TEST_NAME);
        toTest.setStartDate(TEST_START_DATE);
        toTest.setEndDate(TEST_END_DATE);
        toTest.setExpectedEndDate(TEST_EXPECTED_END_DATE);

        Audit AuditEntity = repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));

        AuditEntity.setExpectedEndDate(Date.valueOf("2012-04-04"));
        repository.save(AuditEntity);
        Assert.assertNotEquals("TestCompany", toTest.getExpectedEndDate());
    }

    @Test(expected = TransactionSystemException.class)
    public void changeAuditWithInvalidExpectedEndDate_throwsException() {
        toTest = new Audit();
        toTest.setName(TEST_NAME);
        toTest.setStartDate(TEST_START_DATE);
        toTest.setEndDate(TEST_END_DATE);
        toTest.setExpectedEndDate(TEST_EXPECTED_END_DATE);

        Audit AuditEntity = repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));

        AuditEntity.setExpectedEndDate(null);
        repository.save(AuditEntity);
    }


    @After
    public void tearDown() {
        repository.delete(toTest);
    }
}
