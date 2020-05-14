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
public class QuestionRepositoryTest {


    @Autowired
    private QuestionRepository repository;
    @Autowired
    private CriteriaRepository repository_criteria;


    private QuestionEntity testQuestion;
    private CriteriaEntity criteria;
    private FactorEntity factor;

    private static final String TEST_NAME = "TestQuestion";

    @Before
    public void setup() {
        factor = new FactorEntity();
        factor.setName(TEST_NAME);
        factor.setIsoName("25010");

        criteria = new CriteriaEntity();
        criteria.setName(TEST_NAME);
        criteria.setFactorByFactorId(factor);
    }

    @Test
    public void insert() {
        testQuestion = new QuestionEntity();
        testQuestion.setQuestion(TEST_NAME);
        testQuestion.setCriteriaByCriteriaId(criteria);
        repository.save(testQuestion);
        Assert.assertTrue(repository.exists((Example.of(testQuestion))));
    }

/*
    @Test(expected = TransactionSystemException.class)
    public void insertQuestionNull() {
        testQuestion = new QuestionEntity();
        testQuestion.setQuestion(null);
        testQuestion.setCriteriaByCriteriaId(criteria);
        repository.save(testQuestion);
    }
*/

    @Test(expected = DataIntegrityViolationException.class)
    public void insertCriteriaNull() {
        testQuestion = new QuestionEntity();
        testQuestion.setQuestion(TEST_NAME);
        testQuestion.setCriteriaByCriteriaId(null);
        repository.save(testQuestion);
    }

    @Test
    public void update() {
        testQuestion = new QuestionEntity();
        testQuestion.setQuestion(TEST_NAME);
        testQuestion.setCriteriaByCriteriaId(criteria);
        QuestionEntity tmp = repository.save(testQuestion);
        tmp.setQuestion("update");
        repository.save(tmp);
        Assert.assertEquals(tmp.getQuestion(), testQuestion.getQuestion());
    }


    @Test(expected = TransactionSystemException.class)
    public void updateQuestionNull() {
        testQuestion = new QuestionEntity();
        testQuestion.setQuestion(TEST_NAME);
        testQuestion.setCriteriaByCriteriaId(criteria);
        QuestionEntity tmp = repository.save(testQuestion);
        tmp.setQuestion(null);
        repository.save(tmp);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public  void updateCriteriaNull() {
        testQuestion = new QuestionEntity();
        testQuestion.setQuestion(TEST_NAME);
        testQuestion.setCriteriaByCriteriaId(criteria);
        QuestionEntity tmp = repository.save(testQuestion);
        tmp.setCriteriaByCriteriaId(null);
        repository.save(tmp);
    }

    @Test
    public void delete() {
        testQuestion = new QuestionEntity();
        testQuestion.setQuestion(TEST_NAME);
        testQuestion.setCriteriaByCriteriaId(criteria);
        repository.save(testQuestion);
        Assert.assertTrue(repository.exists((Example.of(testQuestion))));
        repository.delete(testQuestion);
        Assert.assertFalse(repository.exists((Example.of(testQuestion))));
    }
}
