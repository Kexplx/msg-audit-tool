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

    @Test
    public void insertValidContactPersonEntity_isSuccessful() {
        toTest = new ContactPerson();
        toTest.setContactPersonTitle("TestTitle");
        toTest.setContactPersonContactInformation("0123456789, valid@email.com");
        toTest.setContactPersonForename("Jon");
        toTest.setContactPersonSurname("Doe");
        toTest.setContactPersonCompanyName("testCompany");
        toTest.setContactPersonDepartment("testDepartment");
        toTest.setContactPersonSector("testSector");

        ContactPerson entity = repository.save(toTest);

        Assert.assertTrue(repository.exists((Example.of(toTest))));
        Assert.assertEquals(entity.getContactPersonContactInformation(), toTest.getContactPersonContactInformation());
        Assert.assertEquals(entity.getContactPersonForename(), toTest.getContactPersonForename());
        Assert.assertEquals(entity.getContactPersonSurname(), toTest.getContactPersonSurname());
        Assert.assertEquals(entity.getContactPersonTitle(), toTest.getContactPersonTitle());
        Assert.assertEquals(entity.getContactPersonDepartment(), toTest.getContactPersonDepartment());
        Assert.assertEquals(entity.getContactPersonSector(), toTest.getContactPersonSector());
        Assert.assertEquals(entity.getContactPersonCompanyName(), toTest.getContactPersonCompanyName());
    }


    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonWithTitleIsNull_ThrowsException() {
        toTest = new ContactPerson();
        toTest.setContactPersonContactInformation("0123456789, valid@email.com");
        toTest.setContactPersonForename("Jon");
        toTest.setContactPersonSurname("Doe");
        toTest.setContactPersonCompanyName("testCompany");
        toTest.setContactPersonDepartment("testDepartment");
        toTest.setContactPersonSector("testSector");
        repository.save(toTest);

    }

    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonWithForenameIsNull_ThrowsException() {
        toTest = new ContactPerson();
        toTest.setContactPersonTitle("TestTitle");
        toTest.setContactPersonContactInformation("0123456789, valid@email.com");
        toTest.setContactPersonSurname("Doe");
        toTest.setContactPersonCompanyName("testCompany");
        toTest.setContactPersonDepartment("testDepartment");
        toTest.setContactPersonSector("testSector");

        repository.save(toTest);
    }
    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonWithSurnameIsNull_ThrowsException() {
        toTest = new ContactPerson();
        toTest.setContactPersonTitle("TestTitle");
        toTest.setContactPersonContactInformation("0123456789, valid@email.com");
        toTest.setContactPersonForename("Jon");
        toTest.setContactPersonCompanyName("testCompany");
        toTest.setContactPersonDepartment("testDepartment");
        toTest.setContactPersonSector("testSector");

        repository.save(toTest);
    }
    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonWithCompanyNameIsNull_ThrowsException() {
        toTest = new ContactPerson();
        toTest.setContactPersonTitle("TestTitle");
        toTest.setContactPersonContactInformation("0123456789, valid@email.com");
        toTest.setContactPersonForename("Jon");
        toTest.setContactPersonSurname("Doe");
        toTest.setContactPersonDepartment("testDepartment");
        toTest.setContactPersonSector("testSector");

        repository.save(toTest);
    }
    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonWithDepartmentIsNull_ThrowsException() {
        toTest = new ContactPerson();
        toTest.setContactPersonTitle("TestTitle");
        toTest.setContactPersonContactInformation("0123456789, valid@email.com");
        toTest.setContactPersonForename("Jon");
        toTest.setContactPersonSurname("Doe");
        toTest.setContactPersonCompanyName("testCompany");
        toTest.setContactPersonSector("testSector");

        repository.save(toTest);
    }
    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonWithSectorIsNull_ThrowsException() {
        toTest = new ContactPerson();
        toTest.setContactPersonTitle("TestTitle");
        toTest.setContactPersonContactInformation("0123456789, valid@email.com");
        toTest.setContactPersonForename("Jon");
        toTest.setContactPersonSurname("Doe");
        toTest.setContactPersonCompanyName("testCompany");
        toTest.setContactPersonDepartment("testDepartment");

        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonWithTitleIsEmpty_ThrowsException() {
        toTest = new ContactPerson();
        toTest.setContactPersonTitle("");
        toTest.setContactPersonContactInformation("0123456789, valid@email.com");
        toTest.setContactPersonForename("Jon");
        toTest.setContactPersonSurname("Doe");
        toTest.setContactPersonCompanyName("testCompany");
        toTest.setContactPersonDepartment("testDepartment");
        toTest.setContactPersonSector("testSector");

        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonWithForenameIsEmpty_ThrowsException() {
        toTest = new ContactPerson();
        toTest.setContactPersonTitle("TestTitle");
        toTest.setContactPersonContactInformation("0123456789, valid@email.com");
        toTest.setContactPersonForename("");
        toTest.setContactPersonSurname("Doe");
        toTest.setContactPersonCompanyName("testCompany");
        toTest.setContactPersonDepartment("testDepartment");
        toTest.setContactPersonSector("testSector");

        repository.save(toTest);
    }
    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonWithSurnameIsEmpty_ThrowsException() {
        toTest = new ContactPerson();
        toTest.setContactPersonTitle("TestTitle");
        toTest.setContactPersonContactInformation("0123456789, valid@email.com");
        toTest.setContactPersonForename("Jon");
        toTest.setContactPersonSurname("");
        toTest.setContactPersonCompanyName("testCompany");
        toTest.setContactPersonDepartment("testDepartment");
        toTest.setContactPersonSector("testSector");

        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonWithCompanyNameIsEmpty_ThrowsException() {
        toTest = new ContactPerson();
        toTest.setContactPersonTitle("TestTitle");
        toTest.setContactPersonContactInformation("0123456789, valid@email.com");
        toTest.setContactPersonForename("Jon");
        toTest.setContactPersonSurname("Doe");
        toTest.setContactPersonCompanyName("");
        toTest.setContactPersonDepartment("testDepartment");
        toTest.setContactPersonSector("testSector");

        repository.save(toTest);
    }
    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonWithDepartmentIsEmpty_ThrowsException() {
        toTest = new ContactPerson();
        toTest.setContactPersonTitle("TestTitle");
        toTest.setContactPersonContactInformation("0123456789, valid@email.com");
        toTest.setContactPersonForename("Jon");
        toTest.setContactPersonSurname("Doe");
        toTest.setContactPersonCompanyName("testCompany");
        toTest.setContactPersonDepartment("");
        toTest.setContactPersonSector("testSector");

        repository.save(toTest);
    }
    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonWithSectorIsEmpty_ThrowsException() {
        toTest = new ContactPerson();
        toTest.setContactPersonTitle("TestTitle");
        toTest.setContactPersonContactInformation("0123456789, valid@email.com");
        toTest.setContactPersonForename("Jon");
        toTest.setContactPersonSurname("Doe");
        toTest.setContactPersonCompanyName("testCompany");
        toTest.setContactPersonDepartment("testDepartment");
        toTest.setContactPersonSector("");

        repository.save(toTest);
    }
    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonWithTitleIsBlank_ThrowsException() {
        toTest = new ContactPerson();
        toTest.setContactPersonTitle("   ");
        toTest.setContactPersonContactInformation("0123456789, valid@email.com");
        toTest.setContactPersonForename("Jon");
        toTest.setContactPersonSurname("Doe");
        toTest.setContactPersonCompanyName("testCompany");
        toTest.setContactPersonDepartment("testDepartment");
        toTest.setContactPersonSector("testSector");
        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonWithForenameIsBlank_ThrowsException() {
        toTest = new ContactPerson();
        toTest.setContactPersonTitle("TestTitle");
        toTest.setContactPersonContactInformation("0123456789, valid@email.com");
        toTest.setContactPersonForename("   ");
        toTest.setContactPersonSurname("Doe");
        toTest.setContactPersonCompanyName("testCompany");
        toTest.setContactPersonDepartment("testDepartment");
        toTest.setContactPersonSector("testSector");
        repository.save(toTest);
    }
    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonWithSurnameIsBlank_ThrowsException() {
        toTest = new ContactPerson();
        toTest.setContactPersonTitle("TestTitle");
        toTest.setContactPersonContactInformation("0123456789, valid@email.com");
        toTest.setContactPersonForename("Jon");
        toTest.setContactPersonSurname("  ");
        toTest.setContactPersonCompanyName("testCompany");
        toTest.setContactPersonDepartment("testDepartment");
        toTest.setContactPersonSector("testSector");
        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonWithCompanyNameIsBlank_ThrowsException() {
        toTest = new ContactPerson();
        toTest.setContactPersonTitle("TestTitle");
        toTest.setContactPersonContactInformation("0123456789, valid@email.com");
        toTest.setContactPersonForename("Jon");
        toTest.setContactPersonSurname("Doe");
        toTest.setContactPersonCompanyName("   ");
        toTest.setContactPersonDepartment("testDepartment");
        toTest.setContactPersonSector("testSector");
        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonWithDepartmentIsBlank_ThrowsException() {
        toTest = new ContactPerson();
        toTest.setContactPersonTitle("TestTitle");
        toTest.setContactPersonContactInformation("0123456789, valid@email.com");
        toTest.setContactPersonForename("Jon");
        toTest.setContactPersonSurname("Doe");
        toTest.setContactPersonCompanyName("testCompany");
        toTest.setContactPersonDepartment("   ");
        toTest.setContactPersonSector("testSector");
        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonWithSectorIsBlank_ThrowsException() {
        toTest = new ContactPerson();
        toTest.setContactPersonTitle("TestTitle");
        toTest.setContactPersonContactInformation("0123456789, valid@email.com");
        toTest.setContactPersonForename("Jon");
        toTest.setContactPersonSurname("Doe");
        toTest.setContactPersonCompanyName("testCompany");
        toTest.setContactPersonDepartment("testDepartment");
        toTest.setContactPersonSector("   ");
        repository.save(toTest);
    }

    @Test
    public void deleteExistingContactPerson_isSuccessful() {
        toTest = new ContactPerson();
        toTest.setContactPersonTitle("TestTitle");
        toTest.setContactPersonContactInformation("0123456789, valid@email.com");
        toTest.setContactPersonForename("Jon");
        toTest.setContactPersonSurname("Doe");
        toTest.setContactPersonCompanyName("testCompany");
        toTest.setContactPersonDepartment("testDepartment");
        toTest.setContactPersonSector("testSector");
        repository.save(toTest);

        Assert.assertTrue(repository.exists((Example.of(toTest))));

        repository.delete(toTest);
        Assert.assertFalse(repository.exists((Example.of(toTest))));

    }

    @Test
    public void changeContactPersonWithValidTitle_isSuccessfully() {
        toTest = new ContactPerson();
        toTest.setContactPersonTitle("TestTitle");
        toTest.setContactPersonContactInformation("0123456789, valid@email.com");
        toTest.setContactPersonForename("Jon");
        toTest.setContactPersonSurname("Doe");
        toTest.setContactPersonCompanyName("testCompany");
        toTest.setContactPersonDepartment("testDepartment");
        toTest.setContactPersonSector("testSector");
        ContactPerson entity = repository.save(toTest);

        Assert.assertTrue(repository.exists((Example.of(toTest))));

        entity.setContactPersonTitle("newTitle");
        ContactPerson changedEntity = repository.save(entity);
        Assert.assertEquals(entity, changedEntity);
    }

    @Test(expected = TransactionSystemException.class)
    public void changeContactPersonWithInvalidTitle_throwsException() {
        toTest = new ContactPerson();
        toTest.setContactPersonTitle("TestTitle");
        toTest.setContactPersonContactInformation("0123456789, valid@email.com");
        toTest.setContactPersonForename("Jon");
        toTest.setContactPersonSurname("Doe");
        toTest.setContactPersonCompanyName("testCompany");
        toTest.setContactPersonDepartment("testDepartment");
        toTest.setContactPersonSector("testSector");
        ContactPerson entity = repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));

        entity.setContactPersonTitle("  ");
        repository.save(entity);
    }

    @Test
    public void changeContactPersonWithValidForename_isSuccessfully() {
        toTest = new ContactPerson();
        toTest.setContactPersonTitle("TestTitle");
        toTest.setContactPersonContactInformation("0123456789, valid@email.com");
        toTest.setContactPersonForename("Jon");
        toTest.setContactPersonSurname("Doe");
        toTest.setContactPersonCompanyName("testCompany");
        toTest.setContactPersonDepartment("testDepartment");
        toTest.setContactPersonSector("testSector");
        ContactPerson entity = repository.save(toTest);

        Assert.assertTrue(repository.exists((Example.of(toTest))));

        entity.setContactPersonForename("Max");
        ContactPerson changedEntity = repository.save(entity);
        Assert.assertEquals(entity, changedEntity);
    }

    @Test(expected = TransactionSystemException.class)
    public void changeContactPersonWithInvalidForename_throwsException() {
        toTest = new ContactPerson();
        toTest.setContactPersonTitle("TestTitle");
        toTest.setContactPersonContactInformation("0123456789, valid@email.com");
        toTest.setContactPersonForename("Jon");
        toTest.setContactPersonSurname("Doe");
        toTest.setContactPersonCompanyName("testCompany");
        toTest.setContactPersonDepartment("testDepartment");
        toTest.setContactPersonSector("testSector");

        ContactPerson entity = repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));

        entity.setContactPersonForename("  ");
        repository.save(entity);
    }
    @Test
    public void changeContactPersonWithValidSurname_isSuccessfully() {
        toTest = new ContactPerson();
        toTest.setContactPersonTitle("TestTitle");
        toTest.setContactPersonContactInformation("0123456789, valid@email.com");
        toTest.setContactPersonForename("Jon");
        toTest.setContactPersonSurname("Doe");
        toTest.setContactPersonCompanyName("testCompany");
        toTest.setContactPersonDepartment("testDepartment");
        toTest.setContactPersonSector("testSector");
        ContactPerson entity = repository.save(toTest);

        Assert.assertTrue(repository.exists((Example.of(toTest))));

        entity.setContactPersonForename("Max");
        ContactPerson changedEntity = repository.save(entity);
        Assert.assertEquals(entity, changedEntity);
    }
    @Test(expected = TransactionSystemException.class)
    public void changeContactPersonWithInvalidSurname_throwsException() {
        toTest = new ContactPerson();
        toTest.setContactPersonTitle("TestTitle");
        toTest.setContactPersonContactInformation("0123456789, valid@email.com");
        toTest.setContactPersonForename("Jon");
        toTest.setContactPersonSurname("Doe");
        toTest.setContactPersonCompanyName("testCompany");
        toTest.setContactPersonDepartment("testDepartment");
        toTest.setContactPersonSector("testSector");
        ContactPerson entity = repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));

        entity.setContactPersonSurname("  ");
        repository.save(entity);
    }
    @After
    public void tearDown() {
        repository.delete(toTest);
    }
}
