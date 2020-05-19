package com.amos2020.javabackend.repository;

import com.amos2020.javabackend.entity.Audit;
import com.amos2020.javabackend.entity.FacCrit;
import com.amos2020.javabackend.entity.Scope;
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

import java.sql.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ScopeRepositoryTest {

    @Autowired
    private ScopeRepository repository;
    @Autowired
    private AuditRepository auditRepository;
    @Autowired
    private FacCritRepository facCritRepository;

    private Audit audit;
    private FacCrit facCrit;
    private Scope scope;

    @Before
    public void setUp(){
        Date startDate = Date.valueOf("2000-01-01");
        Date endDate = Date.valueOf("2000-01-02");

        audit = new Audit();
        audit.setAuditName("TestAudit");
        audit.setAuditStartDate(startDate);
        audit.setAuditEndDate(endDate);
        audit.setAuditExpectedEndDate(endDate);
        auditRepository.save(audit);

        facCrit = new FacCrit();
        facCrit.setFaccritName("TestFaccrit");
        facCritRepository.save(facCrit);
    }

    @Test
    public void insertValidScopeEntity(){
        scope = new Scope();
        scope.setScopeAuditId(audit.getAuditId());
        scope.setScopeFaccritId(facCrit.getFaccritId());
        scope.setScopeChangeNote("TestNote");
        scope.setScopeRemoved(false);

        Scope toTest = repository.save(scope);

        Assert.assertTrue(repository.exists(Example.of(toTest)));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void insertScopeWithAuditIdNull(){
        scope = new Scope();
        scope.setScopeFaccritId(facCrit.getFaccritId());
        scope.setScopeChangeNote("TestNote");
        scope.setScopeRemoved(false);

        repository.save(scope);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void insertScopeWithFaccritIdNull() {
        scope = new Scope();
        scope.setScopeAuditId(audit.getAuditId());
        scope.setScopeChangeNote("TestNote");
        scope.setScopeRemoved(false);

        repository.save(scope);
    }

    @Test
    public void insertScopeWithChangeNoteNull(){
        scope = new Scope();
        scope.setScopeAuditId(audit.getAuditId());
        scope.setScopeFaccritId(facCrit.getFaccritId());
        scope.setScopeChangeNote(null);
        scope.setScopeRemoved(false);

        Scope toTest = repository.save(scope);

        Assert.assertTrue(repository.exists(Example.of(toTest)));
    }

    @Test(expected = TransactionSystemException.class)
    public void insertScopeWithRemovedNull(){
        scope = new Scope();
        scope.setScopeAuditId(audit.getAuditId());
        scope.setScopeFaccritId(facCrit.getFaccritId());
        scope.setScopeChangeNote("TestNote");
        scope.setScopeRemoved(null);

        repository.save(scope);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void changeScopeWithAuditIdInvalid(){
        scope = new Scope();
        scope.setScopeAuditId(audit.getAuditId());
        scope.setScopeFaccritId(facCrit.getFaccritId());
        scope.setScopeChangeNote("TestNote");
        scope.setScopeRemoved(false);
        Scope toTest = repository.save(scope);

        toTest.setScopeAuditId(-1);
        repository.save(toTest);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void changeScopeWithFaccritIdInvalid() {
        scope = new Scope();
        scope.setScopeAuditId(audit.getAuditId());
        scope.setScopeFaccritId(facCrit.getFaccritId());
        scope.setScopeChangeNote("TestNote");
        scope.setScopeRemoved(false);
        Scope toTest = repository.save(scope);

        toTest.setScopeFaccritId(-1);
        repository.save(toTest);
    }

    @Test
    public void changeScopeChangeNote(){
        scope = new Scope();
        scope.setScopeAuditId(audit.getAuditId());
        scope.setScopeFaccritId(facCrit.getFaccritId());
        scope.setScopeChangeNote("TestNote");
        scope.setScopeRemoved(false);
        Scope scope1 = repository.save(scope);

        scope1.setScopeChangeNote("OtherTestNote");
        Scope toTest = repository.save(scope1);

        Assert.assertTrue(repository.exists(Example.of(toTest)));
    }

    @Test
    public void changeScopeWithChangeNoteNull(){
        scope = new Scope();
        scope.setScopeAuditId(audit.getAuditId());
        scope.setScopeFaccritId(facCrit.getFaccritId());
        scope.setScopeChangeNote("TestNote");
        scope.setScopeRemoved(false);
        Scope scope1 = repository.save(scope);

        scope1.setScopeChangeNote(null);
        Scope toTest = repository.save(scope1);

        Assert.assertTrue(repository.exists(Example.of(toTest)));
    }

    public void changeScopeRemoved(){
        scope = new Scope();
        scope.setScopeAuditId(audit.getAuditId());
        scope.setScopeFaccritId(facCrit.getFaccritId());
        scope.setScopeChangeNote("TestNote");
        scope.setScopeRemoved(false);
        Scope scope1 = repository.save(scope);

        scope1.setScopeRemoved(true);
        Scope toTest = repository.save(scope1);

        Assert.assertTrue(repository.exists(Example.of(toTest)));
    }

    @Test(expected = TransactionSystemException.class)
    public void changeScopeWithRemovedNull(){
        scope = new Scope();
        scope.setScopeAuditId(audit.getAuditId());
        scope.setScopeFaccritId(facCrit.getFaccritId());
        scope.setScopeChangeNote("TestNote");
        scope.setScopeRemoved(false);
        Scope scope1 = repository.save(scope);

        scope1.setScopeRemoved(null);
        repository.save(scope1);
    }

    @Test
    public void deleteScopeEntity(){
        scope = new Scope();
        scope.setScopeAuditId(audit.getAuditId());
        scope.setScopeFaccritId(facCrit.getFaccritId());
        scope.setScopeChangeNote("TestNote");
        scope.setScopeRemoved(false);

        Scope toTest = repository.save(scope);
        repository.delete(toTest);

        Assert.assertFalse(repository.exists(Example.of(toTest)));
    }

    @After
    public void tearDown(){
        repository.delete(scope);
        auditRepository.delete(audit);
        facCritRepository.delete(facCrit);
    }
}
