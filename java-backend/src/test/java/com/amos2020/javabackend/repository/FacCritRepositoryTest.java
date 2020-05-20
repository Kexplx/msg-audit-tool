package com.amos2020.javabackend.repository;

import com.amos2020.javabackend.entity.FacCrit;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacCritRepositoryTest {



    @Autowired
    private FacCritRepository repository;

    private FacCrit factor, criteria;

    @Test
    public void insertFactor() {
        factor = new FacCrit();
        factor.setFaccritName("TestFaktor");
        factor.setFaccritReferenceId(null);
        repository.save(factor);
        Assert.assertTrue(repository.exists((Example.of(factor))));
    }

    @Test
    public void insertCriteria() {
        factor = new FacCrit();
        factor.setFaccritName("TestFaktor");
        repository.save(factor);
        Assert.assertTrue(repository.exists((Example.of(factor))));

        criteria = new FacCrit();
        criteria.setFaccritName("TestKriterium");
        criteria.setFaccritReferenceId(factor.getFaccritId());
        repository.save(criteria);
        Assert.assertTrue(repository.exists((Example.of(criteria))));

    }

    @Test
    public void insertFactorNameNull() {
        factor = new FacCrit();
        factor.setFaccritName(null);
        repository.save(factor);
        Assert.assertTrue(repository.exists((Example.of(factor))));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void insertCriteriaInvalidFactor() {
        criteria = new FacCrit();
        criteria.setFaccritName("TestKriterium");
        criteria.setFaccritReferenceId(9999);
        repository.save(criteria);
        Assert.assertTrue(repository.exists((Example.of(criteria))));

    }


    @Test
    public void changeValidName() {
        factor = new FacCrit();
        factor.setFaccritName("TestFaktor");
        FacCrit tmp = repository.save(factor);
        Assert.assertTrue(repository.exists((Example.of(factor))));
        tmp.setFaccritName("TestFaktorNeu");
        repository.save(tmp);
        Assert.assertTrue(repository.exists((Example.of(tmp))));
        Assert.assertEquals(tmp.getFaccritName(), factor.getFaccritName());
    }


    @Test
    public void changeNameNull() {
        factor = new FacCrit();
        factor.setFaccritName("TestFaktor");
        FacCrit tmp = repository.save(factor);
        Assert.assertTrue(repository.exists((Example.of(factor))));
        tmp.setFaccritName(null);
        repository.save(tmp);
        Assert.assertTrue(repository.exists((Example.of(tmp))));
        Assert.assertNull(factor.getFaccritName());
    }

    @Test
    public void changeValidReference() {
        factor = new FacCrit();
        factor.setFaccritName("TestFaktor");
        repository.save(factor);
        Assert.assertTrue(repository.exists((Example.of(factor))));

        criteria = new FacCrit();
        criteria.setFaccritName("TestKriterium");
        FacCrit tmp =  repository.save(criteria);
        Assert.assertTrue(repository.exists((Example.of(criteria))));
        tmp.setFaccritReferenceId(factor.getFaccritId());
        repository.save(tmp);
        Assert.assertTrue(repository.exists((Example.of(tmp))));
        Assert.assertEquals(tmp.getFaccritReferenceId(), criteria.getFaccritReferenceId());
        Assert.assertEquals((Integer)factor.getFaccritId(), tmp.getFaccritReferenceId());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void changeInvalidReference() {
        factor = new FacCrit();
        factor.setFaccritName("TestFaktor");
        repository.save(factor);
        Assert.assertTrue(repository.exists((Example.of(factor))));

        criteria = new FacCrit();
        criteria.setFaccritName("TestKriterium");
        criteria.setFaccritReferenceId(factor.getFaccritId());
        FacCrit tmp =  repository.save(criteria);
        Assert.assertTrue(repository.exists((Example.of(criteria))));
        tmp.setFaccritReferenceId(9999);
        repository.save(tmp);

    }
}
