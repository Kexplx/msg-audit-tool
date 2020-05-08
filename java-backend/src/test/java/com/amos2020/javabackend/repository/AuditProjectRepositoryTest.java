package com.amos2020.javabackend.repository;

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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;

import java.sql.Date;
import java.util.Optional;

/**
 * Test class for the ContactPersonRepository
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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
        Assert.assertTrue(toTest.getId() > 0);
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

        Optional<AuditProjectEntity> optional = repository.findById(toTest.getId());
        Assert.assertTrue(optional.isPresent());

        repository.delete(toTest);

        optional = repository.findById(toTest.getId());
        Assert.assertFalse(optional.isPresent());
    }

    @Test
    public void changeAuditProjectWithValidName_isSuccessful() {
        toTest = new AuditProjectEntity();
        toTest.setName(TEST_NAME);
        toTest.setStartDate(TEST_START_DATE);
        toTest.setEndDate(TEST_END_DATE);
        toTest.setCustomerByCustomerId(customerEntity);
        AuditProjectEntity auditProjectEntity = repository.save(toTest);
        Assert.assertNotNull(customerEntity);

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
        Assert.assertNotNull(customerEntity);

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
        Assert.assertNotNull(customerEntity);

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
        Assert.assertNotNull(customerEntity);

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
        Assert.assertNotNull(customerEntity);

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
        Assert.assertNotNull(customerEntity);

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
        Assert.assertNotNull(customerEntity);

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
        Assert.assertNotNull(customerEntity);

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
