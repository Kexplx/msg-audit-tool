package com.amos2020.javabackend.repository;

import com.amos2020.javabackend.JavaBackendApplication;
import com.amos2020.javabackend.entity.FactorEntity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JavaBackendApplication.class)
public class FactorRepositoryTest {

    @Autowired
    private FactorRepository repository;

    private FactorEntity testFactor;

    private static final String TEST_NAME = "TestFactor";
    private static final String TEST_ISO = "25010";

    @Before
    public void setup() {

    }

    @Test
    public void insert() {
        testFactor = new FactorEntity();
        testFactor.setName(TEST_NAME);
        testFactor.setIsoName(TEST_ISO);
        repository.save(testFactor);
        Assert.assertTrue(repository.exists((Example.of(testFactor))));
    }


    @Test(expected = TransactionSystemException.class)
    public void insertNameNull() {
        testFactor = new FactorEntity();
        testFactor.setName(null);
        testFactor.setIsoName(TEST_ISO);
        repository.save(testFactor);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertISONull() {
        testFactor = new FactorEntity();
        testFactor.setName(TEST_NAME);
        testFactor.setIsoName(null);
        repository.save(testFactor);
    }


    @Test
    public void updateName() {
        testFactor = new FactorEntity();
        testFactor.setName(TEST_NAME);
        testFactor.setIsoName(TEST_ISO);
        FactorEntity tmp = repository.save(testFactor);
        Assert.assertTrue(repository.exists((Example.of(testFactor))));
        tmp.setName("update");
        repository.save(tmp);
        Assert.assertEquals(tmp.getName(), testFactor.getName());
    }

    @Test
    public void updateISO() {
        testFactor = new FactorEntity();
        testFactor.setName(TEST_NAME);
        testFactor.setIsoName(TEST_ISO);
        FactorEntity tmp = repository.save(testFactor);
        Assert.assertTrue(repository.exists((Example.of(testFactor))));
        tmp.setIsoName("99999");
        repository.save(tmp);
        Assert.assertEquals(tmp.getIsoName(), testFactor.getIsoName());
    }

    @Test(expected = TransactionSystemException.class)
    public void updateNameNull() {
        testFactor = new FactorEntity();
        testFactor.setName(TEST_NAME);
        testFactor.setIsoName(TEST_ISO);
        repository.save(testFactor);
        Assert.assertTrue(repository.exists((Example.of(testFactor))));
        testFactor.setName(null);
        repository.save(testFactor);
    }

    @Test(expected = TransactionSystemException.class)
    public void updateISONull() {
        testFactor = new FactorEntity();
        testFactor.setName(TEST_NAME);
        testFactor.setIsoName(TEST_ISO);
        repository.save(testFactor);
        Assert.assertTrue(repository.exists((Example.of(testFactor))));
        testFactor.setIsoName(null);
        repository.save(testFactor);
    }

    @Test
    public void delete() {
        testFactor = new FactorEntity();
        testFactor.setName(TEST_NAME+"delete");
        testFactor.setIsoName(TEST_ISO);
        repository.save(testFactor);
        Assert.assertTrue(repository.exists((Example.of(testFactor))));
        repository.delete(testFactor);
        Assert.assertFalse(repository.exists((Example.of(testFactor))));
    }
}
