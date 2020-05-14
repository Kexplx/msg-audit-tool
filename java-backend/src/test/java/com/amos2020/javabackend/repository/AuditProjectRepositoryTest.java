package com.amos2020.javabackend.repository;

import com.amos2020.javabackend.JavaBackendApplication;
import com.amos2020.javabackend.entity.AuditProjectEntity;
import com.amos2020.javabackend.entity.ContactPersonEntity;
import com.amos2020.javabackend.entity.CustomerEntity;
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

/**
 * Test class for the ContactPersonRepository
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JavaBackendApplication.class)
public class AuditProjectRepositoryTest {

    @Autowired
    private AuditProjectRepository repository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ContactPersonRepository contactPersonRepository;

    private AuditProjectEntity toTest;
    private CustomerEntity customerEntity;
    private ContactPersonEntity contactPersonEntity;

    private static final String TEST_NAME = "TestName";
    private static final Date TEST_START_DATE = Date.valueOf("2019-10-10");
    private static final Date TEST_END_DATE = Date.valueOf("2020-02-02");

    @Before
    public void setup() {
        contactPersonEntity = new ContactPersonEntity();
        contactPersonEntity.setName("Jon Doe");
        contactPersonEntity.setTitle("Doctor");
        contactPersonEntity.setEmail("jon.doe@gmail.com");
        contactPersonEntity.setPhoneNumber("0123456789");

        customerEntity = new CustomerEntity();
        customerEntity.setDepartment("IT");
        customerEntity.setCompanyName("TestCompany");
        customerEntity.setSector("Finance");
        customerEntity.setContactPersonByContactPersonId(contactPersonEntity);
    }

    @Test
    public void insertAuditProjectWithValidData_isSuccessful() {
        toTest = new AuditProjectEntity();
        toTest.setName(TEST_NAME);
        toTest.setStartDate(TEST_START_DATE);
        toTest.setEndDate(TEST_END_DATE);
        toTest.setCustomerByCustomerId(customerEntity);
        repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));
    }

    @Test(expected = TransactionSystemException.class)
    public void insertAuditProjectWithNameIsNull_throwsException() {
        toTest = new AuditProjectEntity();
        toTest.setStartDate(TEST_START_DATE);
        toTest.setEndDate(TEST_END_DATE);
        toTest.setCustomerByCustomerId(customerEntity);
        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertAuditProjectWithNameIsEmpty_throwsException() {
        toTest = new AuditProjectEntity();
        toTest.setName("");
        toTest.setStartDate(TEST_START_DATE);
        toTest.setEndDate(TEST_END_DATE);
        toTest.setCustomerByCustomerId(customerEntity);
        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertAuditProjectWithNameIsBlank_throwsException() {
        toTest = new AuditProjectEntity();
        toTest.setName("   ");
        toTest.setStartDate(TEST_START_DATE);
        toTest.setEndDate(TEST_END_DATE);
        toTest.setCustomerByCustomerId(customerEntity);
        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertAuditProjectWithStartDateIsNull_throwsException() {
        toTest = new AuditProjectEntity();
        toTest.setName(TEST_NAME);
        toTest.setEndDate(TEST_END_DATE);
        toTest.setCustomerByCustomerId(customerEntity);
        repository.save(toTest);
    }


    @Test(expected = TransactionSystemException.class)
    public void insertAuditProjectWithEndDateIsNull_throwsException() {
        toTest = new AuditProjectEntity();
        toTest.setName(TEST_NAME);
        toTest.setStartDate(TEST_START_DATE);
        toTest.setCustomerByCustomerId(customerEntity);
        repository.save(toTest);
    }


    @Test(expected = DataIntegrityViolationException.class)
    public void insertAuditProjectWithCustomerIsNull_throwsException() {
        toTest = new AuditProjectEntity();
        toTest.setName(TEST_NAME);
        toTest.setStartDate(TEST_START_DATE);
        toTest.setEndDate(TEST_END_DATE);
        repository.save(toTest);
    }

    @Test
    public void deleteExistingAuditProject_isSuccessful() {
        toTest = new AuditProjectEntity();
        toTest.setName(TEST_NAME);
        toTest.setStartDate(TEST_START_DATE);
        toTest.setEndDate(TEST_END_DATE);
        toTest.setCustomerByCustomerId(customerEntity);
        repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));

        repository.delete(toTest);
        Assert.assertFalse(repository.exists((Example.of(toTest))));

    }

    @Test
    public void changeAuditProjectWithValidName_isSuccessful() {
        toTest = new AuditProjectEntity();
        toTest.setName(TEST_NAME);
        toTest.setStartDate(TEST_START_DATE);
        toTest.setEndDate(TEST_END_DATE);
        toTest.setCustomerByCustomerId(customerEntity);
        AuditProjectEntity auditProjectEntity = repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));

        auditProjectEntity.setName("New Name");
        repository.save(auditProjectEntity);
        Assert.assertNotEquals(TEST_NAME, toTest.getName());
    }

    @Test(expected = TransactionSystemException.class)
    public void changeAuditProjectWithInvalidName_throwsException() {
        toTest = new AuditProjectEntity();
        toTest.setName(TEST_NAME);
        toTest.setStartDate(TEST_START_DATE);
        toTest.setEndDate(TEST_END_DATE);
        toTest.setCustomerByCustomerId(customerEntity);
        AuditProjectEntity auditProjectEntity = repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));

        auditProjectEntity.setName("  ");
        repository.save(auditProjectEntity);
    }

    @Test

    public void changeAuditProjectWithValidStartDate_isSuccessful() {
        toTest = new AuditProjectEntity();
        toTest.setName(TEST_NAME);
        toTest.setStartDate(TEST_START_DATE);
        toTest.setEndDate(TEST_END_DATE);
        toTest.setCustomerByCustomerId(customerEntity);
        AuditProjectEntity auditProjectEntity = repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));

        auditProjectEntity.setStartDate(Date.valueOf("2019-10-12"));
        repository.save(auditProjectEntity);
        Assert.assertNotEquals(TEST_START_DATE, toTest.getStartDate());
    }

    @Test(expected = TransactionSystemException.class)
    public void changeAuditProjectWithInvalidStartDate_throwsException() {
        toTest = new AuditProjectEntity();
        toTest.setName(TEST_NAME);
        toTest.setStartDate(TEST_START_DATE);
        toTest.setEndDate(TEST_END_DATE);
        toTest.setCustomerByCustomerId(customerEntity);
        AuditProjectEntity auditProjectEntity = repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));

        auditProjectEntity.setStartDate(null);
        repository.save(auditProjectEntity);
    }

    @Test
    public void changeAuditProjectWithValidEndDate_isSuccessful() {
        toTest = new AuditProjectEntity();
        toTest.setName(TEST_NAME);
        toTest.setStartDate(TEST_START_DATE);
        toTest.setEndDate(TEST_END_DATE);
        toTest.setCustomerByCustomerId(customerEntity);
        AuditProjectEntity auditProjectEntity = repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));

        auditProjectEntity.setEndDate(Date.valueOf("2012-04-04"));
        repository.save(auditProjectEntity);
        Assert.assertNotEquals(TEST_END_DATE, toTest.getEndDate());
    }

    @Test(expected = TransactionSystemException.class)
    public void changeAuditProjectWithInvalidEndDate_throwsException() {
        toTest = new AuditProjectEntity();
        toTest.setName(TEST_NAME);
        toTest.setStartDate(TEST_START_DATE);
        toTest.setEndDate(TEST_END_DATE);
        toTest.setCustomerByCustomerId(customerEntity);
        AuditProjectEntity auditProjectEntity = repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));

        auditProjectEntity.setEndDate(null);
        repository.save(auditProjectEntity);
    }

    @Test
    public void changeAuditProjectWithValidCustomer_isSuccessful() {
        toTest = new AuditProjectEntity();
        toTest.setName(TEST_NAME);
        toTest.setStartDate(TEST_START_DATE);
        toTest.setEndDate(TEST_END_DATE);
        toTest.setCustomerByCustomerId(customerEntity);
        AuditProjectEntity auditProjectEntity = repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));

        auditProjectEntity.getCustomerByCustomerId().setCompanyName("NewCompanyName");
        repository.save(auditProjectEntity);
        Assert.assertNotEquals("TestCompany", toTest.getCustomerByCustomerId().getCompanyName());
    }

    @Test(expected = TransactionSystemException.class)
    public void changeAuditProjectWithInvalidCustomer_throwsException() {
        toTest = new AuditProjectEntity();
        toTest.setName(TEST_NAME);
        toTest.setStartDate(TEST_START_DATE);
        toTest.setEndDate(TEST_END_DATE);
        toTest.setCustomerByCustomerId(customerEntity);
        AuditProjectEntity auditProjectEntity = repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));

        auditProjectEntity.getCustomerByCustomerId().setCompanyName("");
        repository.save(auditProjectEntity);
    }


    @After
    public void tearDown() {
        repository.delete(toTest);
        customerRepository.delete(customerEntity);
        contactPersonRepository.delete(contactPersonEntity);
    }
}
