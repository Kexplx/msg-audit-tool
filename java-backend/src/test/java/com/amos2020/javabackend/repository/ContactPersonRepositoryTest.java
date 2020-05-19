package com.amos2020.javabackend.repository;

import com.amos2020.javabackend.entity.ContactPerson;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;

/**
 * Test class for the ContactPersonRepository
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContactPersonRepositoryTest {

    @Autowired
    private ContactPersonRepository repository;

    private ContactPerson toTest;

    private static final String TEST_TITLE = "TestTitle";
    private static final String TEST_INFORMATION = "0123456789, valid@email.com";
    private static final String TEST_FORENAME = "Jon";
    private static final String TEST_SURNAME = "Doe";
    private static final String TEST_COMPANY = "testCompany";
    private static final String TEST_DEPARTMENT = "testDepartment";
    private static final String TEST_SECTOR = "testSector";


    @Test
    public void insertValidContactPersonEntity_isSuccessful() {
        toTest = new ContactPerson();
        toTest.setTitle(TEST_TITLE);
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename(TEST_FORENAME);
        toTest.setSurname(TEST_SURNAME);
        toTest.setCompanyName(TEST_COMPANY);
        toTest.setDepartment(TEST_DEPARTMENT);
        toTest.setSector(TEST_SECTOR);

        ContactPerson entity = repository.save(toTest);

        Assert.assertTrue(repository.exists((Example.of(toTest))));
        Assert.assertEquals(entity.getContactInformation(), toTest.getContactInformation());
        Assert.assertEquals(entity.getForename(), toTest.getForename());
        Assert.assertEquals(entity.getSurname(), toTest.getSurname());
        Assert.assertEquals(entity.getTitle(), toTest.getTitle());
        Assert.assertEquals(entity.getDepartment(), toTest.getDepartment());
        Assert.assertEquals(entity.getSector(), toTest.getSector());
        Assert.assertEquals(entity.getCompanyName(), toTest.getCompanyName());
    }


    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonWithTitleIsNull_ThrowsException() {
        toTest = new ContactPerson();
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename(TEST_FORENAME);
        toTest.setSurname(TEST_SURNAME);
        toTest.setCompanyName(TEST_COMPANY);
        toTest.setDepartment(TEST_DEPARTMENT);
        toTest.setSector(TEST_SECTOR);
        repository.save(toTest);

    }

    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonWithForenameIsNull_ThrowsException() {
        toTest = new ContactPerson();
        toTest.setTitle(TEST_TITLE);
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setSurname(TEST_SURNAME);
        toTest.setCompanyName(TEST_COMPANY);
        toTest.setDepartment(TEST_DEPARTMENT);
        toTest.setSector(TEST_SECTOR);

        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonWithSurnameIsNull_ThrowsException() {
        toTest = new ContactPerson();
        toTest.setTitle(TEST_TITLE);
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename(TEST_FORENAME);
        toTest.setCompanyName(TEST_COMPANY);
        toTest.setDepartment(TEST_DEPARTMENT);
        toTest.setSector(TEST_SECTOR);

        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonWithCompanyNameIsNull_ThrowsException() {
        toTest = new ContactPerson();
        toTest.setTitle(TEST_TITLE);
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename(TEST_FORENAME);
        toTest.setSurname(TEST_SURNAME);
        toTest.setDepartment(TEST_DEPARTMENT);
        toTest.setSector(TEST_SECTOR);

        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonWithDepartmentIsNull_ThrowsException() {
        toTest = new ContactPerson();
        toTest.setTitle(TEST_TITLE);
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename(TEST_FORENAME);
        toTest.setSurname(TEST_SURNAME);
        toTest.setCompanyName(TEST_COMPANY);
        toTest.setSector(TEST_SECTOR);

        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonWithSectorIsNull_ThrowsException() {
        toTest = new ContactPerson();
        toTest.setTitle(TEST_TITLE);
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename(TEST_FORENAME);
        toTest.setSurname(TEST_SURNAME);
        toTest.setCompanyName(TEST_COMPANY);
        toTest.setDepartment(TEST_DEPARTMENT);

        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonWithTitleIsEmpty_ThrowsException() {
        toTest = new ContactPerson();
        toTest.setTitle("");
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename(TEST_FORENAME);
        toTest.setSurname(TEST_SURNAME);
        toTest.setCompanyName(TEST_COMPANY);
        toTest.setDepartment(TEST_DEPARTMENT);
        toTest.setSector(TEST_SECTOR);

        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonWithForenameIsEmpty_ThrowsException() {
        toTest = new ContactPerson();
        toTest.setTitle(TEST_TITLE);
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename("");
        toTest.setSurname(TEST_SURNAME);
        toTest.setCompanyName(TEST_COMPANY);
        toTest.setDepartment(TEST_DEPARTMENT);
        toTest.setSector(TEST_SECTOR);

        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonWithSurnameIsEmpty_ThrowsException() {
        toTest = new ContactPerson();
        toTest.setTitle(TEST_TITLE);
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename(TEST_FORENAME);
        toTest.setSurname("");
        toTest.setCompanyName(TEST_COMPANY);
        toTest.setDepartment(TEST_DEPARTMENT);
        toTest.setSector(TEST_SECTOR);

        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonWithCompanyNameIsEmpty_ThrowsException() {
        toTest = new ContactPerson();
        toTest.setTitle(TEST_TITLE);
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename(TEST_FORENAME);
        toTest.setSurname(TEST_SURNAME);
        toTest.setCompanyName("");
        toTest.setDepartment(TEST_DEPARTMENT);
        toTest.setSector(TEST_SECTOR);

        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonWithDepartmentIsEmpty_ThrowsException() {
        toTest = new ContactPerson();
        toTest.setTitle(TEST_TITLE);
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename(TEST_FORENAME);
        toTest.setSurname(TEST_SURNAME);
        toTest.setCompanyName(TEST_COMPANY);
        toTest.setDepartment("");
        toTest.setSector(TEST_SECTOR);

        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonWithSectorIsEmpty_ThrowsException() {
        toTest = new ContactPerson();
        toTest.setTitle(TEST_TITLE);
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename(TEST_FORENAME);
        toTest.setSurname(TEST_SURNAME);
        toTest.setCompanyName(TEST_COMPANY);
        toTest.setDepartment(TEST_DEPARTMENT);
        toTest.setSector("");

        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonWithTitleIsBlank_ThrowsException() {
        toTest = new ContactPerson();
        toTest.setTitle("   ");
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename(TEST_FORENAME);
        toTest.setSurname(TEST_SURNAME);
        toTest.setCompanyName(TEST_COMPANY);
        toTest.setDepartment(TEST_DEPARTMENT);
        toTest.setSector(TEST_SECTOR);
        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonWithForenameIsBlank_ThrowsException() {
        toTest = new ContactPerson();
        toTest.setTitle(TEST_TITLE);
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename("   ");
        toTest.setSurname(TEST_SURNAME);
        toTest.setCompanyName(TEST_COMPANY);
        toTest.setDepartment(TEST_DEPARTMENT);
        toTest.setSector(TEST_SECTOR);
        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonWithSurnameIsBlank_ThrowsException() {
        toTest = new ContactPerson();
        toTest.setTitle(TEST_TITLE);
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename(TEST_FORENAME);
        toTest.setSurname("  ");
        toTest.setCompanyName(TEST_COMPANY);
        toTest.setDepartment(TEST_DEPARTMENT);
        toTest.setSector(TEST_SECTOR);
        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonWithCompanyNameIsBlank_ThrowsException() {
        toTest = new ContactPerson();
        toTest.setTitle(TEST_TITLE);
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename(TEST_FORENAME);
        toTest.setSurname(TEST_SURNAME);
        toTest.setCompanyName("   ");
        toTest.setDepartment(TEST_DEPARTMENT);
        toTest.setSector(TEST_SECTOR);
        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonWithDepartmentIsBlank_ThrowsException() {
        toTest = new ContactPerson();
        toTest.setTitle(TEST_TITLE);
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename(TEST_FORENAME);
        toTest.setSurname(TEST_SURNAME);
        toTest.setCompanyName(TEST_COMPANY);
        toTest.setDepartment("   ");
        toTest.setSector(TEST_SECTOR);
        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonWithSectorIsBlank_ThrowsException() {
        toTest = new ContactPerson();
        toTest.setTitle(TEST_TITLE);
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename(TEST_FORENAME);
        toTest.setSurname(TEST_SURNAME);
        toTest.setCompanyName(TEST_COMPANY);
        toTest.setDepartment(TEST_DEPARTMENT);
        toTest.setSector("   ");
        repository.save(toTest);
    }

    @Test
    public void deleteExistingContactPerson_isSuccessful() {
        toTest = new ContactPerson();
        toTest.setTitle(TEST_TITLE);
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename(TEST_FORENAME);
        toTest.setSurname(TEST_SURNAME);
        toTest.setCompanyName(TEST_COMPANY);
        toTest.setDepartment(TEST_DEPARTMENT);
        toTest.setSector(TEST_SECTOR);
        repository.save(toTest);

        Assert.assertTrue(repository.exists((Example.of(toTest))));

        repository.delete(toTest);
        Assert.assertFalse(repository.exists((Example.of(toTest))));

    }

    @Test
    public void changeContactPersonWithValidTitle_isSuccessfully() {
        toTest = new ContactPerson();
        toTest.setTitle(TEST_TITLE);
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename(TEST_FORENAME);
        toTest.setSurname(TEST_SURNAME);
        toTest.setCompanyName(TEST_COMPANY);
        toTest.setDepartment(TEST_DEPARTMENT);
        toTest.setSector(TEST_SECTOR);
        ContactPerson entity = repository.save(toTest);

        Assert.assertTrue(repository.exists((Example.of(toTest))));

        entity.setTitle("newTitle");
        ContactPerson changedEntity = repository.save(entity);
        Assert.assertEquals(entity, changedEntity);
    }

    @Test(expected = TransactionSystemException.class)
    public void changeContactPersonWithInvalidTitle_throwsException() {
        toTest = new ContactPerson();
        toTest.setTitle(TEST_TITLE);
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename(TEST_FORENAME);
        toTest.setSurname(TEST_SURNAME);
        toTest.setCompanyName(TEST_COMPANY);
        toTest.setDepartment(TEST_DEPARTMENT);
        toTest.setSector(TEST_SECTOR);
        ContactPerson entity = repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));

        entity.setTitle("  ");
        repository.save(entity);
    }

    @Test
    public void changeContactPersonWithValidForename_isSuccessfully() {
        toTest = new ContactPerson();
        toTest.setTitle(TEST_TITLE);
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename(TEST_FORENAME);
        toTest.setSurname(TEST_SURNAME);
        toTest.setCompanyName(TEST_COMPANY);
        toTest.setDepartment(TEST_DEPARTMENT);
        toTest.setSector(TEST_SECTOR);
        ContactPerson entity = repository.save(toTest);

        Assert.assertTrue(repository.exists((Example.of(toTest))));

        entity.setForename("Max");
        ContactPerson changedEntity = repository.save(entity);
        Assert.assertEquals(entity, changedEntity);
    }

    @Test(expected = TransactionSystemException.class)
    public void changeContactPersonWithInvalidForename_throwsException() {
        toTest = new ContactPerson();
        toTest.setTitle(TEST_TITLE);
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename(TEST_FORENAME);
        toTest.setSurname(TEST_SURNAME);
        toTest.setCompanyName(TEST_COMPANY);
        toTest.setDepartment(TEST_DEPARTMENT);
        toTest.setSector(TEST_SECTOR);

        ContactPerson entity = repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));

        entity.setForename("  ");
        repository.save(entity);
    }

    @Test
    public void changeContactPersonWithValidSurname_isSuccessfully() {
        toTest = new ContactPerson();
        toTest.setTitle(TEST_TITLE);
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename(TEST_FORENAME);
        toTest.setSurname(TEST_SURNAME);
        toTest.setCompanyName(TEST_COMPANY);
        toTest.setDepartment(TEST_DEPARTMENT);
        toTest.setSector(TEST_SECTOR);
        ContactPerson entity = repository.save(toTest);

        Assert.assertTrue(repository.exists((Example.of(toTest))));

        entity.setForename("Max");
        ContactPerson changedEntity = repository.save(entity);
        Assert.assertEquals(entity, changedEntity);
    }

    @Test(expected = TransactionSystemException.class)
    public void changeContactPersonWithInvalidSurname_throwsException() {
        toTest = new ContactPerson();
        toTest.setTitle(TEST_TITLE);
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename(TEST_FORENAME);
        toTest.setSurname(TEST_SURNAME);
        toTest.setCompanyName(TEST_COMPANY);
        toTest.setDepartment(TEST_DEPARTMENT);
        toTest.setSector(TEST_SECTOR);
        ContactPerson entity = repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));

        entity.setSurname("  ");
        repository.save(entity);
    }

    @After
    public void tearDown() {
        repository.delete(toTest);
    }
}
