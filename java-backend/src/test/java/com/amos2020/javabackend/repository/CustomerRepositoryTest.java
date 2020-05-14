package com.amos2020.javabackend.repository;

import com.amos2020.javabackend.JavaBackendApplication;
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

/**
 * Test class for the CustomerRepository
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JavaBackendApplication.class)
public class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository repository;

    @Autowired
    private ContactPersonRepository contactPersonRepository;

    private CustomerEntity toTest;
    private ContactPersonEntity contactPersonEntity;

    @Before
    public void setup() {
        contactPersonEntity = new ContactPersonEntity();
        contactPersonEntity.setName("Jon Doe");
        contactPersonEntity.setTitle("Doctor");
        contactPersonEntity.setEmail("jon.doe@gmail.com");
        contactPersonEntity.setPhoneNumber("0123456789");
    }

    @Test
    public void insertValidCustomer_isSuccessful() {
        toTest = new CustomerEntity();
        toTest.setDepartment("IT");
        toTest.setCompanyName("TestCompany");
        toTest.setSector("Finance");
        toTest.setContactPersonByContactPersonId(contactPersonEntity);
        CustomerEntity customerEntity = repository.save(toTest);

        Assert.assertTrue(repository.exists((Example.of(toTest))));
        Assert.assertEquals(customerEntity.getCompanyName(), toTest.getCompanyName());
        Assert.assertEquals(customerEntity.getSector(), toTest.getSector());
        Assert.assertEquals(customerEntity.getDepartment(), toTest.getDepartment());
        Assert.assertEquals(customerEntity.getContactPersonByContactPersonId(), toTest.getContactPersonByContactPersonId());
    }


    @Test(expected = TransactionSystemException.class)
    public void insertCustomerWithDepartmentIsNull_ThrowsException() {
        toTest = new CustomerEntity();
        toTest.setCompanyName("TestCompany");
        toTest.setSector("Finance");
        toTest.setContactPersonByContactPersonId(contactPersonEntity);
        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertCustomerWithCompanyNameIsNull_ThrowsException() {
        toTest = new CustomerEntity();
        toTest.setDepartment("IT");
        toTest.setSector("Finance");
        toTest.setContactPersonByContactPersonId(contactPersonEntity);
        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertCustomerWithSectorIsNull_ThrowsException() {
        toTest = new CustomerEntity();
        toTest.setDepartment("IT");
        toTest.setCompanyName("TestCompany");
        toTest.setContactPersonByContactPersonId(contactPersonEntity);
        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertCustomerWithDepartmentIsEmpty_ThrowsException() {
        toTest = new CustomerEntity();
        toTest.setDepartment("");
        toTest.setCompanyName("TestCompany");
        toTest.setSector("Finance");
        toTest.setContactPersonByContactPersonId(contactPersonEntity);
        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertCustomerWithCompanyNameIsEmpty_ThrowsException() {
        toTest = new CustomerEntity();
        toTest.setDepartment("IT");
        toTest.setCompanyName("");
        toTest.setSector("Finance");
        toTest.setContactPersonByContactPersonId(contactPersonEntity);
        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertCustomerWithSectorIsEmpty_ThrowsException() {
        toTest = new CustomerEntity();
        toTest.setDepartment("IT");
        toTest.setCompanyName("TestCompany");
        toTest.setSector("");
        toTest.setContactPersonByContactPersonId(contactPersonEntity);
        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertCustomerWithDepartmentIsBlank_ThrowsException() {
        toTest = new CustomerEntity();
        toTest.setDepartment("   ");
        toTest.setCompanyName("TestCompany");
        toTest.setSector("Finance");
        toTest.setContactPersonByContactPersonId(contactPersonEntity);
        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertCustomerWithCompanyNameIsBlank_ThrowsException() {
        toTest = new CustomerEntity();
        toTest.setDepartment("IT");
        toTest.setCompanyName("   ");
        toTest.setSector("Finance");
        toTest.setContactPersonByContactPersonId(contactPersonEntity);
        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertCustomerWithSectorIsBlank_ThrowsException() {
        toTest = new CustomerEntity();
        toTest.setDepartment("IT");
        toTest.setCompanyName("TestCompany");
        toTest.setSector("   ");
        toTest.setContactPersonByContactPersonId(contactPersonEntity);
        repository.save(toTest);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void insertCustomerWithoutContactPerson_throwsException() {
        toTest = new CustomerEntity();
        toTest.setDepartment("IT");
        toTest.setCompanyName("TestCompany");
        toTest.setSector("Finance");
        toTest.setContactPersonByContactPersonId(null);
        repository.save(toTest);
    }

    @Test
    public void deleteExistingCustomer_isSuccessful() {
        toTest = new CustomerEntity();
        toTest.setDepartment("IT");
        toTest.setCompanyName("TestCompany");
        toTest.setSector("Finance");
        toTest.setContactPersonByContactPersonId(contactPersonEntity);
        repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));

        repository.delete(toTest);
        Assert.assertFalse(repository.exists((Example.of(toTest))));

    }

    @Test
    public void changeCustomerWithValidDepartment_isSuccessful() {
        toTest = new CustomerEntity();
        toTest.setDepartment("IT");
        toTest.setCompanyName("TestCompany");
        toTest.setSector("Finance");
        toTest.setContactPersonByContactPersonId(contactPersonEntity);
        CustomerEntity customerEntity = repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));

        customerEntity.setDepartment("HR");
        repository.save(customerEntity);
        Assert.assertNotEquals(toTest.getDepartment(), "IT");
    }

    @Test(expected = TransactionSystemException.class)
    public void changeCustomerWithInvalidDepartment_throwsException() {
        toTest = new CustomerEntity();
        toTest.setDepartment("IT");
        toTest.setCompanyName("TestCompany");
        toTest.setSector("Finance");
        toTest.setContactPersonByContactPersonId(contactPersonEntity);
        CustomerEntity customerEntity = repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));

        customerEntity.setDepartment(" ");
        repository.save(customerEntity);
    }

    @Test
    public void changeCustomerWithValidCompanyName_isSuccessful() {
        toTest = new CustomerEntity();
        toTest.setDepartment("IT");
        toTest.setCompanyName("TestCompany");
        toTest.setSector("Finance");
        toTest.setContactPersonByContactPersonId(contactPersonEntity);
        CustomerEntity customerEntity = repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));

        customerEntity.setCompanyName("NewCompanyName");
        repository.save(customerEntity);
        Assert.assertNotEquals(toTest.getCompanyName(), "TestCompany");
    }

    @Test(expected = TransactionSystemException.class)
    public void changeCustomerWithInvalidCompanyName_throwsException() {
        toTest = new CustomerEntity();
        toTest.setDepartment("IT");
        toTest.setCompanyName("TestCompany");
        toTest.setSector("Finance");
        toTest.setContactPersonByContactPersonId(contactPersonEntity);
        CustomerEntity customerEntity = repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));

        customerEntity.setCompanyName(" ");
        repository.save(customerEntity);
    }

    @Test
    public void changeCustomerWithValidSector_isSuccessful() {
        toTest = new CustomerEntity();
        toTest.setDepartment("IT");
        toTest.setCompanyName("TestCompany");
        toTest.setSector("Finance");
        toTest.setContactPersonByContactPersonId(contactPersonEntity);
        CustomerEntity customerEntity = repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));

        customerEntity.setSector("Tech");
        repository.save(customerEntity);
        Assert.assertNotEquals(toTest.getSector(), "Finance");
    }

    @Test(expected = TransactionSystemException.class)
    public void changeCustomerWithInvalidSector_throwsException() {
        toTest = new CustomerEntity();
        toTest.setDepartment("IT");
        toTest.setCompanyName("TestCompany");
        toTest.setSector("Finance");
        toTest.setContactPersonByContactPersonId(contactPersonEntity);
        CustomerEntity customerEntity = repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));

        customerEntity.setSector(" ");
        repository.save(customerEntity);
    }

    @Test
    public void changeCustomerWithValidContactPerson_isSuccessful() {
        toTest = new CustomerEntity();
        toTest.setDepartment("IT");
        toTest.setCompanyName("TestCompany");
        toTest.setSector("Finance");
        toTest.setContactPersonByContactPersonId(contactPersonEntity);
        CustomerEntity customerEntity = repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));

        contactPersonEntity.setName("Jona Doe");
        contactPersonEntity.setTitle("Mister");
        contactPersonEntity.setEmail("jona.doe@gmail.com");
        contactPersonEntity.setPhoneNumber("9876543210");

        customerEntity.setContactPersonByContactPersonId(contactPersonEntity);
        repository.save(customerEntity);
        Assert.assertNotEquals(toTest.getContactPersonByContactPersonId().getName(), "Jon Doe");
    }

    @Test(expected = TransactionSystemException.class)
    public void changeCustomerWithInvalidContactPerson_throwsException() {
        toTest = new CustomerEntity();
        toTest.setDepartment("IT");
        toTest.setCompanyName("TestCompany");
        toTest.setSector("Finance");
        toTest.setContactPersonByContactPersonId(contactPersonEntity);
        CustomerEntity customerEntity = repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));

        contactPersonEntity.setName(" ");
        customerEntity.setContactPersonByContactPersonId(contactPersonEntity);
        repository.save(customerEntity);
    }

    @After
    public void tearDown() {
        repository.delete(toTest);
        contactPersonRepository.delete(contactPersonEntity);
    }

}
