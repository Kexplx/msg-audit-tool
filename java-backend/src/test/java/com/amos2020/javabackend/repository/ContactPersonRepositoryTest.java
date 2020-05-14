package com.amos2020.javabackend.repository;

import com.amos2020.javabackend.JavaBackendApplication;
import com.amos2020.javabackend.entity.ContactPersonEntity;
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
@SpringBootTest(classes = JavaBackendApplication.class)
public class ContactPersonRepositoryTest {

    @Autowired
    private ContactPersonRepository repository;

    private ContactPersonEntity toTest;

    @Test
    public void insertValidContactPersonEntity_isSuccessful() {
        toTest = new ContactPersonEntity();
        toTest.setTitle("TestTitle");
        toTest.setPhoneNumber("0123456789");
        toTest.setEmail("valid@email.com");
        toTest.setName("Jon Doe");
        ContactPersonEntity entity = repository.save(toTest);

        Assert.assertTrue(repository.exists((Example.of(toTest))));
        Assert.assertEquals(entity.getEmail(), toTest.getEmail());
        Assert.assertEquals(entity.getName(), toTest.getName());
        Assert.assertEquals(entity.getPhoneNumber(), toTest.getPhoneNumber());
        Assert.assertEquals(entity.getTitle(), toTest.getTitle());
    }

    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonEntityWithInvalidEmail_ThrowsException() {
        toTest = new ContactPersonEntity();
        toTest.setTitle("TestTitle");
        toTest.setPhoneNumber("0123456789");
        toTest.setEmail("invalidemail.com");
        toTest.setName("Jon Doe");
        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonEntityWithTitleIsNull_ThrowsException() {
        toTest = new ContactPersonEntity();
        toTest.setPhoneNumber("0123456789");
        toTest.setEmail("valid@email.com");
        toTest.setName("Jon Doe");
        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonEntityWithNameIsNull_ThrowsException() {
        toTest = new ContactPersonEntity();
        toTest.setTitle("TestTitle");
        toTest.setPhoneNumber("0123456789");
        toTest.setEmail("valid@email.com");
        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonEntityWithTitleIsEmpty_ThrowsException() {
        toTest = new ContactPersonEntity();
        toTest.setTitle("");
        toTest.setPhoneNumber("0123456789");
        toTest.setEmail("valid@email.com");
        toTest.setName("Jon Doe");
        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonEntityWithNameIsEmpty_ThrowsException() {
        toTest = new ContactPersonEntity();
        toTest.setName("");
        toTest.setTitle("TestTitle");
        toTest.setPhoneNumber("0123456789");
        toTest.setEmail("valid@email.com");
        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonEntityWithTitleIsBlank_ThrowsException() {
        toTest = new ContactPersonEntity();
        toTest.setTitle("   ");
        toTest.setPhoneNumber("0123456789");
        toTest.setEmail("valid@email.com");
        toTest.setName("Jon Doe");
        repository.save(toTest);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertContactPersonEntityWithNameIsBlank_ThrowsException() {
        toTest = new ContactPersonEntity();
        toTest.setName("   ");
        toTest.setTitle("TestTitle");
        toTest.setPhoneNumber("0123456789");
        toTest.setEmail("valid@email.com");
        repository.save(toTest);
    }

    @Test
    public void deleteExistingContactPerson_isSuccessful() {
        toTest = new ContactPersonEntity();
        toTest.setTitle("TestTitle");
        toTest.setPhoneNumber("0123456789");
        toTest.setEmail("valid@email.com");
        toTest.setName("Jon Doe");
        repository.save(toTest);

        Assert.assertTrue(repository.exists((Example.of(toTest))));

        repository.delete(toTest);
        Assert.assertFalse(repository.exists((Example.of(toTest))));

    }

    @Test
    public void changeContactPersonWithValidTitle_isSuccessfully() {
        toTest = new ContactPersonEntity();
        toTest.setTitle("TestTitle");
        toTest.setPhoneNumber("0123456789");
        toTest.setEmail("valid@email.com");
        toTest.setName("Jon Doe");
        ContactPersonEntity entity = repository.save(toTest);

        Assert.assertTrue(repository.exists((Example.of(toTest))));

        entity.setTitle("newTitle");
        ContactPersonEntity changedEntity = repository.save(entity);
        Assert.assertEquals(entity, changedEntity);
    }

    @Test(expected = TransactionSystemException.class)
    public void changeContactPersonWithInvalidTitle_throwsException() {
        toTest = new ContactPersonEntity();
        toTest.setTitle("TestTitle");
        toTest.setPhoneNumber("0123456789");
        toTest.setEmail("valid@email.com");
        toTest.setName("Jon Doe");
        ContactPersonEntity entity = repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));

        entity.setTitle("  ");
        repository.save(entity);
    }

    @Test
    public void changeContactPersonWithValidName_isSuccessfully() {
        toTest = new ContactPersonEntity();
        toTest.setTitle("TestTitle");
        toTest.setPhoneNumber("0123456789");
        toTest.setEmail("valid@email.com");
        toTest.setName("Jon Doe");
        ContactPersonEntity entity = repository.save(toTest);

        Assert.assertTrue(repository.exists((Example.of(toTest))));

        entity.setName("New Jon");
        ContactPersonEntity changedEntity = repository.save(entity);
        Assert.assertEquals(entity, changedEntity);
    }

    @Test(expected = TransactionSystemException.class)
    public void changeContactPersonWithInvalidName_throwsException() {
        toTest = new ContactPersonEntity();
        toTest.setTitle("TestTitle");
        toTest.setPhoneNumber("0123456789");
        toTest.setEmail("valid@email.com");
        toTest.setName(" ");
        ContactPersonEntity entity = repository.save(toTest);
        Assert.assertTrue(repository.exists((Example.of(toTest))));

        entity.setName("  ");
        repository.save(entity);
    }

    @After
    public void tearDown() {
        repository.delete(toTest);
    }
}
