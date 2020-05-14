package com.amos2020.javabackend.repository;

import com.amos2020.javabackend.JavaBackendApplication;
import com.amos2020.javabackend.entity.CriteriaEntity;
import com.amos2020.javabackend.entity.FactorEntity;
import com.amos2020.javabackend.entity.QuestionEntity;
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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JavaBackendApplication.class)
public class CriteriaRepositoryTest {

    @Autowired
    private CriteriaRepository repository;

    @Autowired
    private FactorRepository repository_factor;


    @Autowired
    private QuestionRepository repository_question;


    private CriteriaEntity testCriteria;
    private FactorEntity factor;
    private QuestionEntity question;

    private static final String TEST_NAME = "TestCriteria";

    @Before
    public void setup() {
        factor = new FactorEntity();
        factor.setIsoName("25010");
        factor.setName(TEST_NAME);
        //repository_factor.save(factor);

        question = new QuestionEntity();
        question.setQuestion("TEST?");

    }


    @Test
    public void insert() {
        testCriteria = new CriteriaEntity();
        testCriteria.setName(TEST_NAME);
        testCriteria.setFactorByFactorId(factor);
        repository.save(testCriteria);
        Assert.assertTrue(repository.exists((Example.of(testCriteria))));
    }

    @Test(expected = TransactionSystemException.class)
    public void insertNameNull() {
        testCriteria = new CriteriaEntity();
        testCriteria.setName(null);
        testCriteria.setFactorByFactorId(factor);
        repository.save(testCriteria);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void insertFactorNull() {
        testCriteria = new CriteriaEntity();
        testCriteria.setName(TEST_NAME);
        testCriteria.setFactorByFactorId(null);
        repository.save(testCriteria);
    }

    @Test
    public void updateName() {
        testCriteria = new CriteriaEntity();
        testCriteria.setName(TEST_NAME);
        testCriteria.setFactorByFactorId(factor);
        CriteriaEntity tmp = repository.save(testCriteria);
        Assert.assertTrue(repository.exists((Example.of(testCriteria))));
        tmp.setName("update");
        repository.save(tmp);
        Assert.assertEquals(tmp.getName(), testCriteria.getName());
    }

    @Test(expected = TransactionSystemException.class)
    public void updateNameNull() {
        testCriteria = new CriteriaEntity();
        testCriteria.setName(TEST_NAME);
        testCriteria.setFactorByFactorId(factor);
        CriteriaEntity tmp = repository.save(testCriteria);
        Assert.assertTrue(repository.exists((Example.of(testCriteria))));
        tmp.setName(null);
        repository.save(tmp);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void updateFactorNull() {
        testCriteria = new CriteriaEntity();
        testCriteria.setName(TEST_NAME);
        testCriteria.setFactorByFactorId(factor);
        CriteriaEntity tmp = repository.save(testCriteria);
        Assert.assertTrue(repository.exists((Example.of(testCriteria))));
        tmp.setFactorByFactorId(null);
        repository.save(tmp);
    }



    @Test
    public void delete() {
        testCriteria = new CriteriaEntity();
        testCriteria.setName(TEST_NAME);
        testCriteria.setFactorByFactorId(factor);
        repository.save(testCriteria);
        Assert.assertTrue(repository.exists((Example.of(testCriteria))));
        repository.delete(testCriteria);
        Assert.assertFalse(repository.exists((Example.of(testCriteria))));
    }
}
