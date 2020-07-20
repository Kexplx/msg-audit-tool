package com.amos2020.javabackend.repository;

import com.amos2020.javabackend.entity.FacCrit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacCritRepositoryTest {

    @Autowired
    private FacCritRepository repository;
    private FacCrit factor, criteria;
    private static String TOO_LONG = new String(new char[20000]).replace('\0', 'H');

    @Test
    public void insertFactor() {
        factor = new FacCrit();
        factor.setName("TestFaktor");
        factor.setGoal("TestZiel");
        factor.setReferenceId(null);
        repository.save(factor);
        Assert.assertTrue(repository.exists((Example.of(factor))));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void insertFactorWithNameTooLong() {
        factor = new FacCrit();
        factor.setName(TOO_LONG);
        factor.setGoal("TestZiel");
        factor.setReferenceId(null);
        repository.save(factor);
    }

    @Test
    public void insertCriteria() {
        factor = new FacCrit();
        factor.setName("TestFaktor");
        factor.setGoal("TestZiel");
        repository.save(factor);
        Assert.assertTrue(repository.exists((Example.of(factor))));

        criteria = new FacCrit();
        criteria.setName("TestKriterium");
        criteria.setGoal("TestZiel");
        criteria.setReferenceId(factor.getId());
        repository.save(criteria);
        Assert.assertTrue(repository.exists((Example.of(criteria))));
    }

    @Test(expected = TransactionSystemException.class)
    public void insertFactorNameNull() {
        factor = new FacCrit();
        factor.setName(null);
        factor.setGoal("TestZiel");
        repository.save(factor);
    }

    @Test
    public void insertFactorGoalNull() {
        factor = new FacCrit();
        factor.setName("TestKriterium");
        factor.setGoal(null);
        repository.save(factor);
        Assert.assertTrue(repository.exists((Example.of(factor))));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void insertCriteriaInvalidFactor() {
        criteria = new FacCrit();
        criteria.setName("TestKriterium");
        criteria.setGoal("TestZiel");
        criteria.setReferenceId(9999);
        repository.save(criteria);
        Assert.assertTrue(repository.exists((Example.of(criteria))));
    }

    @Test
    public void changeValidName() {
        factor = new FacCrit();
        factor.setName("TestFaktor");
        factor.setGoal("TestZiel");
        FacCrit tmp = repository.save(factor);
        Assert.assertTrue(repository.exists((Example.of(factor))));
        tmp.setName("TestFaktorNeu");
        repository.save(tmp);
        Assert.assertTrue(repository.exists((Example.of(tmp))));
        Assert.assertEquals(tmp.getName(), factor.getName());
    }

    @Test(expected = TransactionSystemException.class)
    public void changeNameNull() {
        factor = new FacCrit();
        factor.setName("TestFaktor");
        factor.setGoal("TestZiel");
        FacCrit tmp = repository.save(factor);
        Assert.assertTrue(repository.exists((Example.of(factor))));
        tmp.setName(null);
        repository.save(tmp);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void changeNameTooLong() {
        factor = new FacCrit();
        factor.setName("TestName");
        factor.setGoal("TestZiel");
        FacCrit tmp = repository.save(factor);
        Assert.assertTrue(repository.exists((Example.of(factor))));
        tmp.setName(TOO_LONG);
        repository.save(tmp);
    }

    @Test
    public void changeValidGoal() {
        factor = new FacCrit();
        factor.setName("TestFaktor");
        factor.setGoal("TestZiel");
        FacCrit tmp = repository.save(factor);
        Assert.assertTrue(repository.exists((Example.of(factor))));
        tmp.setGoal("TestZielNeu");
        repository.save(tmp);
        Assert.assertTrue(repository.exists((Example.of(tmp))));
        Assert.assertEquals(tmp.getName(), factor.getName());
    }

    @Test
    public void changeGoalNull() {
        factor = new FacCrit();
        factor.setName("TestFaktor");
        factor.setGoal("TestZiel");
        FacCrit tmp = repository.save(factor);
        Assert.assertTrue(repository.exists((Example.of(factor))));
        tmp.setGoal(null);
        repository.save(tmp);
        Assert.assertTrue(repository.exists((Example.of(tmp))));
        Assert.assertEquals(tmp.getGoal(), factor.getGoal());
    }

    @Test
    public void changeValidReference() {
        factor = new FacCrit();
        factor.setName("TestFaktor");
        repository.save(factor);
        Assert.assertTrue(repository.exists((Example.of(factor))));

        criteria = new FacCrit();
        criteria.setName("TestKriterium");
        FacCrit tmp = repository.save(criteria);
        Assert.assertTrue(repository.exists((Example.of(criteria))));
        tmp.setReferenceId(factor.getId());
        repository.save(tmp);
        Assert.assertTrue(repository.exists((Example.of(tmp))));
        Assert.assertEquals(tmp.getReferenceId(), criteria.getReferenceId());
        Assert.assertEquals((Integer) factor.getId(), tmp.getReferenceId());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void changeInvalidReference() {
        factor = new FacCrit();
        factor.setName("TestFaktor");
        repository.save(factor);
        Assert.assertTrue(repository.exists((Example.of(factor))));

        criteria = new FacCrit();
        criteria.setName("TestKriterium");
        criteria.setReferenceId(factor.getId());
        FacCrit tmp = repository.save(criteria);
        Assert.assertTrue(repository.exists((Example.of(criteria))));
        tmp.setReferenceId(9999);
        repository.save(tmp);
    }
}
