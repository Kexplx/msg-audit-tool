package com.amos2020.javabackend.repository;

import com.amos2020.javabackend.entity.ContactPerson;
import com.amos2020.javabackend.entity.Salutation;
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

    private static final Salutation TEST_SALUTATION = Salutation.MANN;
    private static final String TEST_TITLE = "TestTitle";
    private static final String TEST_INFORMATION = "0123456789, valid@email.com";
    private static final String TEST_FORENAME = "Jon";
    private static final String TEST_SURNAME = "Doe";
    private static final String TEST_COMPANY = "testCompany";
    private static final String TEST_DEPARTMENT = "testDepartment";
    private static final String TEST_SECTOR = "testSector";
    private static final String TEST_CORPORATE_DIVISION = "testDivision";

    @Test
    public void insertValidContactPersonEntity_isSuccessful() {
        toTest = new ContactPerson();
        toTest.setSalutation(TEST_SALUTATION);
        toTest.setTitle(TEST_TITLE);
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename(TEST_FORENAME);
        toTest.setSurname(TEST_SURNAME);
        toTest.setCompanyName(TEST_COMPANY);
        toTest.setDepartment(TEST_DEPARTMENT);
        toTest.setSector(TEST_SECTOR);
        toTest.setCorporateDivision(TEST_CORPORATE_DIVISION);

        ContactPerson entity = repository.save(toTest);

        Assert.assertTrue(repository.exists((Example.of(toTest))));
        Assert.assertEquals(entity.getSalutation(), toTest.getSalutation());
        Assert.assertEquals(entity.getContactInformation(), toTest.getContactInformation());
        Assert.assertEquals(entity.getForename(), toTest.getForename());
        Assert.assertEquals(entity.getSurname(), toTest.getSurname());
        Assert.assertEquals(entity.getTitle(), toTest.getTitle());
        Assert.assertEquals(entity.getDepartment(), toTest.getDepartment());
        Assert.assertEquals(entity.getSector(), toTest.getSector());
        Assert.assertEquals(entity.getCompanyName(), toTest.getCompanyName());
        Assert.assertEquals(entity.getCorporateDivision(), toTest.getCorporateDivision());
    }

    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonWithSalutationIsNull_ThrowsException() {
        toTest = new ContactPerson();
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename(TEST_FORENAME);
        toTest.setSurname(TEST_SURNAME);
        toTest.setCompanyName(TEST_COMPANY);
        toTest.setDepartment(TEST_DEPARTMENT);
        toTest.setSector(TEST_SECTOR);
        toTest.setCorporateDivision(TEST_CORPORATE_DIVISION);
        repository.save(toTest);

    }

    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonWithTitleIsNull_ThrowsException() {
        toTest = new ContactPerson();
        toTest.setSalutation(TEST_SALUTATION);
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename(TEST_FORENAME);
        toTest.setSurname(TEST_SURNAME);
        toTest.setCompanyName(TEST_COMPANY);
        toTest.setDepartment(TEST_DEPARTMENT);
        toTest.setSector(TEST_SECTOR);
        toTest.setCorporateDivision(TEST_CORPORATE_DIVISION);
        repository.save(toTest);

    }

    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonWithForenameIsNull_ThrowsException() {
        toTest = new ContactPerson();
        toTest.setSalutation(TEST_SALUTATION);
        toTest.setTitle(TEST_TITLE);
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setSurname(TEST_SURNAME);
        toTest.setCompanyName(TEST_COMPANY);
        toTest.setDepartment(TEST_DEPARTMENT);
        toTest.setSector(TEST_SECTOR);
        toTest.setCorporateDivision(TEST_CORPORATE_DIVISION);

        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonWithSurnameIsNull_ThrowsException() {
        toTest = new ContactPerson();
        toTest.setSalutation(TEST_SALUTATION);
        toTest.setTitle(TEST_TITLE);
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename(TEST_FORENAME);
        toTest.setCompanyName(TEST_COMPANY);
        toTest.setDepartment(TEST_DEPARTMENT);
        toTest.setSector(TEST_SECTOR);
        toTest.setCorporateDivision(TEST_CORPORATE_DIVISION);

        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonWithCompanyNameIsNull_ThrowsException() {
        toTest = new ContactPerson();
        toTest.setSalutation(TEST_SALUTATION);
        toTest.setTitle(TEST_TITLE);
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename(TEST_FORENAME);
        toTest.setSurname(TEST_SURNAME);
        toTest.setDepartment(TEST_DEPARTMENT);
        toTest.setSector(TEST_SECTOR);
        toTest.setCorporateDivision(TEST_CORPORATE_DIVISION);

        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonWithDepartmentIsNull_ThrowsException() {
        toTest = new ContactPerson();
        toTest.setSalutation(TEST_SALUTATION);
        toTest.setTitle(TEST_TITLE);
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename(TEST_FORENAME);
        toTest.setSurname(TEST_SURNAME);
        toTest.setCompanyName(TEST_COMPANY);
        toTest.setSector(TEST_SECTOR);
        toTest.setCorporateDivision(TEST_CORPORATE_DIVISION);

        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonWithSectorIsNull_ThrowsException() {
        toTest = new ContactPerson();
        toTest.setSalutation(TEST_SALUTATION);
        toTest.setTitle(TEST_TITLE);
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename(TEST_FORENAME);
        toTest.setSurname(TEST_SURNAME);
        toTest.setCompanyName(TEST_COMPANY);
        toTest.setDepartment(TEST_DEPARTMENT);
        toTest.setCorporateDivision(TEST_CORPORATE_DIVISION);

        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonWithCorporateDivisionIsNull_ThrowsException() {
        toTest = new ContactPerson();
        toTest.setSalutation(TEST_SALUTATION);
        toTest.setTitle(TEST_TITLE);
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename(TEST_FORENAME);
        toTest.setSurname(TEST_SURNAME);
        toTest.setCompanyName(TEST_COMPANY);
        toTest.setDepartment(TEST_DEPARTMENT);
        toTest.setSector(TEST_SECTOR);

        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonWithTitleIsEmpty_ThrowsException() {
        toTest = new ContactPerson();
        toTest.setSalutation(TEST_SALUTATION);
        toTest.setTitle("");
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename(TEST_FORENAME);
        toTest.setSurname(TEST_SURNAME);
        toTest.setCompanyName(TEST_COMPANY);
        toTest.setDepartment(TEST_DEPARTMENT);
        toTest.setSector(TEST_SECTOR);
        toTest.setCorporateDivision(TEST_CORPORATE_DIVISION);

        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonWithForenameIsEmpty_ThrowsException() {
        toTest = new ContactPerson();
        toTest.setSalutation(TEST_SALUTATION);
        toTest.setTitle(TEST_TITLE);
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename("");
        toTest.setSurname(TEST_SURNAME);
        toTest.setCompanyName(TEST_COMPANY);
        toTest.setDepartment(TEST_DEPARTMENT);
        toTest.setSector(TEST_SECTOR);
        toTest.setCorporateDivision(TEST_CORPORATE_DIVISION);

        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonWithSurnameIsEmpty_ThrowsException() {
        toTest = new ContactPerson();
        toTest.setSalutation(TEST_SALUTATION);
        toTest.setTitle(TEST_TITLE);
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename(TEST_FORENAME);
        toTest.setSurname("");
        toTest.setCompanyName(TEST_COMPANY);
        toTest.setDepartment(TEST_DEPARTMENT);
        toTest.setSector(TEST_SECTOR);
        toTest.setCorporateDivision(TEST_CORPORATE_DIVISION);

        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonWithCompanyNameIsEmpty_ThrowsException() {
        toTest = new ContactPerson();
        toTest.setSalutation(TEST_SALUTATION);
        toTest.setTitle(TEST_TITLE);
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename(TEST_FORENAME);
        toTest.setSurname(TEST_SURNAME);
        toTest.setCompanyName("");
        toTest.setDepartment(TEST_DEPARTMENT);
        toTest.setSector(TEST_SECTOR);
        toTest.setCorporateDivision(TEST_CORPORATE_DIVISION);

        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonWithDepartmentIsEmpty_ThrowsException() {
        toTest = new ContactPerson();
        toTest.setSalutation(TEST_SALUTATION);
        toTest.setTitle(TEST_TITLE);
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename(TEST_FORENAME);
        toTest.setSurname(TEST_SURNAME);
        toTest.setCompanyName(TEST_COMPANY);
        toTest.setDepartment("");
        toTest.setSector(TEST_SECTOR);
        toTest.setCorporateDivision(TEST_CORPORATE_DIVISION);

        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonWithSectorIsEmpty_ThrowsException() {
        toTest = new ContactPerson();
        toTest.setSalutation(TEST_SALUTATION);
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
    public void insertContactPersonWithCorporateDivisionIsEmpty_ThrowsException() {
        toTest = new ContactPerson();
        toTest.setSalutation(TEST_SALUTATION);
        toTest.setTitle(TEST_TITLE);
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename(TEST_FORENAME);
        toTest.setSurname(TEST_SURNAME);
        toTest.setCompanyName(TEST_COMPANY);
        toTest.setDepartment(TEST_DEPARTMENT);
        toTest.setSector(TEST_SECTOR);
        toTest.setCorporateDivision("");

        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonWithTitleIsBlank_ThrowsException() {
        toTest = new ContactPerson();
        toTest.setSalutation(TEST_SALUTATION);
        toTest.setTitle("   ");
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename(TEST_FORENAME);
        toTest.setSurname(TEST_SURNAME);
        toTest.setCompanyName(TEST_COMPANY);
        toTest.setDepartment(TEST_DEPARTMENT);
        toTest.setSector(TEST_SECTOR);
        toTest.setCorporateDivision(TEST_CORPORATE_DIVISION);
        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonWithForenameIsBlank_ThrowsException() {
        toTest = new ContactPerson();
        toTest.setSalutation(TEST_SALUTATION);
        toTest.setTitle(TEST_TITLE);
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename("   ");
        toTest.setSurname(TEST_SURNAME);
        toTest.setCompanyName(TEST_COMPANY);
        toTest.setDepartment(TEST_DEPARTMENT);
        toTest.setSector(TEST_SECTOR);
        toTest.setCorporateDivision(TEST_CORPORATE_DIVISION);
        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonWithSurnameIsBlank_ThrowsException() {
        toTest = new ContactPerson();
        toTest.setSalutation(TEST_SALUTATION);
        toTest.setTitle(TEST_TITLE);
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename(TEST_FORENAME);
        toTest.setSurname("  ");
        toTest.setCompanyName(TEST_COMPANY);
        toTest.setDepartment(TEST_DEPARTMENT);
        toTest.setSector(TEST_SECTOR);
        toTest.setCorporateDivision(TEST_CORPORATE_DIVISION);
        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonWithCompanyNameIsBlank_ThrowsException() {
        toTest = new ContactPerson();
        toTest.setSalutation(TEST_SALUTATION);
        toTest.setTitle(TEST_TITLE);
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename(TEST_FORENAME);
        toTest.setSurname(TEST_SURNAME);
        toTest.setCompanyName("   ");
        toTest.setDepartment(TEST_DEPARTMENT);
        toTest.setSector(TEST_SECTOR);
        toTest.setCorporateDivision(TEST_CORPORATE_DIVISION);
        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonWithDepartmentIsBlank_ThrowsException() {
        toTest = new ContactPerson();
        toTest.setSalutation(TEST_SALUTATION);
        toTest.setTitle(TEST_TITLE);
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename(TEST_FORENAME);
        toTest.setSurname(TEST_SURNAME);
        toTest.setCompanyName(TEST_COMPANY);
        toTest.setDepartment("   ");
        toTest.setSector(TEST_SECTOR);
        toTest.setCorporateDivision(TEST_CORPORATE_DIVISION);
        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonWithSectorIsBlank_ThrowsException() {
        toTest = new ContactPerson();
        toTest.setSalutation(TEST_SALUTATION);
        toTest.setTitle(TEST_TITLE);
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename(TEST_FORENAME);
        toTest.setSurname(TEST_SURNAME);
        toTest.setCompanyName(TEST_COMPANY);
        toTest.setDepartment(TEST_DEPARTMENT);
        toTest.setSector("   ");
        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonWithCorporateDivisionIsBlank_ThrowsException() {
        toTest = new ContactPerson();
        toTest.setSalutation(TEST_SALUTATION);
        toTest.setTitle(TEST_TITLE);
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename(TEST_FORENAME);
        toTest.setSurname(TEST_SURNAME);
        toTest.setCompanyName(TEST_COMPANY);
        toTest.setDepartment(TEST_DEPARTMENT);
        toTest.setSector(TEST_SECTOR);
        toTest.setCorporateDivision("   ");

        repository.save(toTest);
    }

    @Test
    public void deleteExistingContactPerson_isSuccessful() {
        toTest = new ContactPerson();
        toTest.setSalutation(TEST_SALUTATION);
        toTest.setTitle(TEST_TITLE);
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename(TEST_FORENAME);
        toTest.setSurname(TEST_SURNAME);
        toTest.setCompanyName(TEST_COMPANY);
        toTest.setDepartment(TEST_DEPARTMENT);
        toTest.setSector(TEST_SECTOR);
        toTest.setCorporateDivision(TEST_CORPORATE_DIVISION);
        repository.save(toTest);

        Assert.assertTrue(repository.exists((Example.of(toTest))));

        repository.delete(toTest);
        Assert.assertFalse(repository.exists((Example.of(toTest))));

    }

    @Test
    public void updateContactPersonWithValidSalutation_isSuccessfully() {
        toTest = new ContactPerson();
        toTest.setSalutation(TEST_SALUTATION);
        toTest.setTitle(TEST_TITLE);
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename(TEST_FORENAME);
        toTest.setSurname(TEST_SURNAME);
        toTest.setCompanyName(TEST_COMPANY);
        toTest.setDepartment(TEST_DEPARTMENT);
        toTest.setSector(TEST_SECTOR);
        toTest.setCorporateDivision(TEST_CORPORATE_DIVISION);
        ContactPerson entity = repository.save(toTest);

        Assert.assertTrue(repository.exists((Example.of(toTest))));

        entity.setSalutation(Salutation.FRAU);
        ContactPerson updatedEntity = repository.save(entity);
        Assert.assertEquals(entity, updatedEntity);
    }

    @Test(expected = TransactionSystemException.class)
    public void updateContactPersonWithInvalidSalutation_throwsException() {
        toTest = new ContactPerson();
        toTest.setSalutation(TEST_SALUTATION);
        toTest.setTitle(TEST_TITLE);
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename(TEST_FORENAME);
        toTest.setSurname(TEST_SURNAME);
        toTest.setCompanyName(TEST_COMPANY);
        toTest.setDepartment(TEST_DEPARTMENT);
        toTest.setSector(TEST_SECTOR);
        toTest.setCorporateDivision(TEST_CORPORATE_DIVISION);
        ContactPerson entity = repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));

        entity.setSalutation(null);
        repository.save(entity);
    }

    @Test
    public void updateContactPersonWithValidTitle_isSuccessfully() {
        toTest = new ContactPerson();
        toTest.setSalutation(TEST_SALUTATION);
        toTest.setTitle(TEST_TITLE);
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename(TEST_FORENAME);
        toTest.setSurname(TEST_SURNAME);
        toTest.setCompanyName(TEST_COMPANY);
        toTest.setDepartment(TEST_DEPARTMENT);
        toTest.setSector(TEST_SECTOR);
        toTest.setCorporateDivision(TEST_CORPORATE_DIVISION);
        ContactPerson entity = repository.save(toTest);

        Assert.assertTrue(repository.exists((Example.of(toTest))));

        entity.setTitle("newTitle");
        ContactPerson updatedEntity = repository.save(entity);
        Assert.assertEquals(entity, updatedEntity);
    }

    @Test(expected = TransactionSystemException.class)
    public void updateContactPersonWithInvalidTitle_throwsException() {
        toTest = new ContactPerson();
        toTest.setSalutation(TEST_SALUTATION);
        toTest.setTitle(TEST_TITLE);
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename(TEST_FORENAME);
        toTest.setSurname(TEST_SURNAME);
        toTest.setCompanyName(TEST_COMPANY);
        toTest.setDepartment(TEST_DEPARTMENT);
        toTest.setSector(TEST_SECTOR);
        toTest.setCorporateDivision(TEST_CORPORATE_DIVISION);
        ContactPerson entity = repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));

        entity.setTitle("  ");
        repository.save(entity);
    }

    @Test
    public void updateContactPersonWithValidForename_isSuccessfully() {
        toTest = new ContactPerson();
        toTest.setSalutation(TEST_SALUTATION);
        toTest.setTitle(TEST_TITLE);
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename(TEST_FORENAME);
        toTest.setSurname(TEST_SURNAME);
        toTest.setCompanyName(TEST_COMPANY);
        toTest.setDepartment(TEST_DEPARTMENT);
        toTest.setSector(TEST_SECTOR);
        toTest.setCorporateDivision(TEST_CORPORATE_DIVISION);
        ContactPerson entity = repository.save(toTest);

        Assert.assertTrue(repository.exists((Example.of(toTest))));

        entity.setForename("Max");
        ContactPerson updatedEntity = repository.save(entity);
        Assert.assertEquals(entity, updatedEntity);
    }

    @Test(expected = TransactionSystemException.class)
    public void updateContactPersonWithInvalidForename_throwsException() {
        toTest = new ContactPerson();
        toTest.setSalutation(TEST_SALUTATION);
        toTest.setTitle(TEST_TITLE);
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename(TEST_FORENAME);
        toTest.setSurname(TEST_SURNAME);
        toTest.setCompanyName(TEST_COMPANY);
        toTest.setDepartment(TEST_DEPARTMENT);
        toTest.setSector(TEST_SECTOR);
        toTest.setCorporateDivision(TEST_CORPORATE_DIVISION);

        ContactPerson entity = repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));

        entity.setForename("  ");
        repository.save(entity);
    }

    @Test
    public void updateContactPersonWithValidSurname_isSuccessfully() {
        toTest = new ContactPerson();
        toTest.setSalutation(TEST_SALUTATION);
        toTest.setTitle(TEST_TITLE);
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename(TEST_FORENAME);
        toTest.setSurname(TEST_SURNAME);
        toTest.setCompanyName(TEST_COMPANY);
        toTest.setDepartment(TEST_DEPARTMENT);
        toTest.setSector(TEST_SECTOR);
        toTest.setCorporateDivision(TEST_CORPORATE_DIVISION);
        ContactPerson entity = repository.save(toTest);

        Assert.assertTrue(repository.exists((Example.of(toTest))));

        entity.setForename("Max");
        ContactPerson updatedEntity = repository.save(entity);
        Assert.assertEquals(entity, updatedEntity);
    }

    @Test(expected = TransactionSystemException.class)
    public void updateContactPersonWithInvalidSurname_throwsException() {
        toTest = new ContactPerson();
        toTest.setSalutation(TEST_SALUTATION);
        toTest.setTitle(TEST_TITLE);
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename(TEST_FORENAME);
        toTest.setSurname(TEST_SURNAME);
        toTest.setCompanyName(TEST_COMPANY);
        toTest.setDepartment(TEST_DEPARTMENT);
        toTest.setSector(TEST_SECTOR);
        toTest.setCorporateDivision(TEST_CORPORATE_DIVISION);
        ContactPerson entity = repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));

        entity.setSurname("  ");
        repository.save(entity);
    }

    @Test
    public void updateContactPersonWithValidCompanyName_isSuccessfully() {
        toTest = new ContactPerson();
        toTest.setSalutation(TEST_SALUTATION);
        toTest.setTitle(TEST_TITLE);
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename(TEST_FORENAME);
        toTest.setSurname(TEST_SURNAME);
        toTest.setCompanyName(TEST_COMPANY);
        toTest.setDepartment(TEST_DEPARTMENT);
        toTest.setSector(TEST_SECTOR);
        toTest.setCorporateDivision(TEST_CORPORATE_DIVISION);
        ContactPerson entity = repository.save(toTest);

        Assert.assertTrue(repository.exists((Example.of(toTest))));

        entity.setCompanyName("newCompanyName");
        ContactPerson updatedEntity = repository.save(entity);
        Assert.assertEquals(entity, updatedEntity);
    }

    @Test(expected = TransactionSystemException.class)
    public void updateContactPersonWithInvalidCompanyName_throwsException() {
        toTest = new ContactPerson();
        toTest.setSalutation(TEST_SALUTATION);
        toTest.setTitle(TEST_TITLE);
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename(TEST_FORENAME);
        toTest.setSurname(TEST_SURNAME);
        toTest.setCompanyName(TEST_COMPANY);
        toTest.setDepartment(TEST_DEPARTMENT);
        toTest.setSector(TEST_SECTOR);
        toTest.setCorporateDivision(TEST_CORPORATE_DIVISION);
        ContactPerson entity = repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));

        entity.setCompanyName("   ");
        repository.save(entity);
    }

    @Test
    public void updateContactPersonWithValidDepartment_isSuccessfully() {
        toTest = new ContactPerson();
        toTest.setSalutation(TEST_SALUTATION);
        toTest.setTitle(TEST_TITLE);
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename(TEST_FORENAME);
        toTest.setSurname(TEST_SURNAME);
        toTest.setCompanyName(TEST_COMPANY);
        toTest.setDepartment(TEST_DEPARTMENT);
        toTest.setSector(TEST_SECTOR);
        toTest.setCorporateDivision(TEST_CORPORATE_DIVISION);
        ContactPerson entity = repository.save(toTest);

        Assert.assertTrue(repository.exists((Example.of(toTest))));

        entity.setDepartment("newDepartment");
        ContactPerson updatedEntity = repository.save(entity);
        Assert.assertEquals(entity, updatedEntity);
    }

    @Test(expected = TransactionSystemException.class)
    public void updateContactPersonWithInvalidDepartment_throwsException() {
        toTest = new ContactPerson();
        toTest.setSalutation(TEST_SALUTATION);
        toTest.setTitle(TEST_TITLE);
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename(TEST_FORENAME);
        toTest.setSurname(TEST_SURNAME);
        toTest.setCompanyName(TEST_COMPANY);
        toTest.setDepartment(TEST_DEPARTMENT);
        toTest.setSector(TEST_SECTOR);
        toTest.setCorporateDivision(TEST_CORPORATE_DIVISION);
        ContactPerson entity = repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));

        entity.setDepartment("  ");
        repository.save(entity);
    }

    @Test
    public void updateContactPersonWithValidSector_isSuccessfully() {
        toTest = new ContactPerson();
        toTest.setSalutation(TEST_SALUTATION);
        toTest.setTitle(TEST_TITLE);
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename(TEST_FORENAME);
        toTest.setSurname(TEST_SURNAME);
        toTest.setCompanyName(TEST_COMPANY);
        toTest.setDepartment(TEST_DEPARTMENT);
        toTest.setSector(TEST_SECTOR);
        toTest.setCorporateDivision(TEST_CORPORATE_DIVISION);
        ContactPerson entity = repository.save(toTest);

        Assert.assertTrue(repository.exists((Example.of(toTest))));

        entity.setSector("newSector");
        ContactPerson updatedEntity = repository.save(entity);
        Assert.assertEquals(entity, updatedEntity);
    }

    @Test(expected = TransactionSystemException.class)
    public void updateContactPersonWithInvalidSector_throwsException() {
        toTest = new ContactPerson();
        toTest.setSalutation(TEST_SALUTATION);
        toTest.setTitle(TEST_TITLE);
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename(TEST_FORENAME);
        toTest.setSurname(TEST_SURNAME);
        toTest.setCompanyName(TEST_COMPANY);
        toTest.setDepartment(TEST_DEPARTMENT);
        toTest.setSector(TEST_SECTOR);
        toTest.setCorporateDivision(TEST_CORPORATE_DIVISION);
        ContactPerson entity = repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));

        entity.setSector("  ");
        repository.save(entity);
    }

    @Test
    public void updateContactPersonWithValidCorporateDivision_isSuccessfully() {
        toTest = new ContactPerson();
        toTest.setSalutation(TEST_SALUTATION);
        toTest.setTitle(TEST_TITLE);
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename(TEST_FORENAME);
        toTest.setSurname(TEST_SURNAME);
        toTest.setCompanyName(TEST_COMPANY);
        toTest.setDepartment(TEST_DEPARTMENT);
        toTest.setSector(TEST_SECTOR);
        toTest.setCorporateDivision(TEST_CORPORATE_DIVISION);
        ContactPerson entity = repository.save(toTest);

        Assert.assertTrue(repository.exists((Example.of(toTest))));

        entity.setCorporateDivision("newCorporateDivision");
        ContactPerson updatedEntity = repository.save(entity);
        Assert.assertEquals(entity, updatedEntity);
    }

    @Test(expected = TransactionSystemException.class)
    public void updateContactPersonWithInvalidCorporateDivision_throwsException() {
        toTest = new ContactPerson();
        toTest.setSalutation(TEST_SALUTATION);
        toTest.setTitle(TEST_TITLE);
        toTest.setContactInformation(TEST_INFORMATION);
        toTest.setForename(TEST_FORENAME);
        toTest.setSurname(TEST_SURNAME);
        toTest.setCompanyName(TEST_COMPANY);
        toTest.setDepartment(TEST_DEPARTMENT);
        toTest.setSector(TEST_SECTOR);
        toTest.setCorporateDivision(TEST_CORPORATE_DIVISION);
        ContactPerson entity = repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));

        entity.setCorporateDivision("  ");
        repository.save(entity);
    }

    @After
    public void tearDown() {
        repository.delete(toTest);
    }
}
