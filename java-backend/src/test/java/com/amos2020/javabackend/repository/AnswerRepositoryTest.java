package com.amos2020.javabackend.repository;

import com.amos2020.javabackend.entity.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;

import java.sql.Date;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AnswerRepositoryTest {

    private static final String PROOF = "RandomProof";
    private static final Boolean ANSWER = true;

    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AuditProjectRepository auditProjectRepository;

    private AnswerEntity testEntity;
    private AnswerEntity testEntity1;
    private AnswerEntity testEntity2;
    private QuestionEntity questionEntity;
    private QuestionEntity questionEntity1;
    private CriteriaEntity criteriaEntity;
    private CriteriaEntity criteriaEntity1;
    private FactorEntity factorEntity;
    private FactorEntity factorEntity1;
    private AuditProjectEntity auditProjectEntity;
    private AuditProjectEntity auditProjectEntity1;
    private CustomerEntity customerEntity;
    private CustomerEntity customerEntity1;
    private ContactPersonEntity contactPersonEntity;
    private ContactPersonEntity contactPersonEntity1;

    @Before
    public void setup(){
        factorEntity = new FactorEntity();
        factorEntity.setName("RandomFactor");
        factorEntity.setIsoName("25010");

        factorEntity1 = new FactorEntity();
        factorEntity1.setName("RandomFactor1");
        factorEntity1.setIsoName("25010");

        criteriaEntity = new CriteriaEntity();
        criteriaEntity.setName("RandomCriteria");
        criteriaEntity.setFactorByFactorId(factorEntity);

        criteriaEntity1 = new CriteriaEntity();
        criteriaEntity1.setName("RandomCriteria1");
        criteriaEntity1.setFactorByFactorId(factorEntity1);

        questionEntity = new QuestionEntity();
        questionEntity.setQuestion("RandomQuestion");
        questionEntity.setCriteriaByCriteriaId(criteriaEntity);

        questionEntity1 = new QuestionEntity();
        questionEntity1.setQuestion("RandomQuestion2");
        questionEntity1.setCriteriaByCriteriaId(criteriaEntity1);

        questionRepository.save(questionEntity);
        questionRepository.save(questionEntity1);

        contactPersonEntity = new ContactPersonEntity();
        contactPersonEntity.setTitle("RandomTitle");
        contactPersonEntity.setName("RandomName");
        contactPersonEntity.setEmail("test@test.de");
        contactPersonEntity.setPhoneNumber("01761111111");

        contactPersonEntity1 = new ContactPersonEntity();
        contactPersonEntity1.setTitle("RandomTitle");
        contactPersonEntity1.setName("RandomName");
        contactPersonEntity1.setEmail("test@test.de");
        contactPersonEntity1.setPhoneNumber("01761111111");

        customerEntity = new CustomerEntity();
        customerEntity.setCompanyName("RandomCompanyName");
        customerEntity.setSector("RandomSector");
        customerEntity.setDepartment("RandomDepartment");
        customerEntity.setContactPersonByContactPersonId(contactPersonEntity);

        customerEntity1 = new CustomerEntity();
        customerEntity1.setCompanyName("RandomCompanyName");
        customerEntity1.setSector("RandomSector");
        customerEntity1.setDepartment("RandomDepartment");
        customerEntity1.setContactPersonByContactPersonId(contactPersonEntity1);

        auditProjectEntity = new AuditProjectEntity();
        auditProjectEntity.setName("RandomName");
        auditProjectEntity.setStartDate(Date.valueOf("2000-01-01"));
        auditProjectEntity.setEndDate(Date.valueOf("2001-01-01"));
        auditProjectEntity.setCustomerByCustomerId(customerEntity);

        auditProjectEntity1 = new AuditProjectEntity();
        auditProjectEntity1.setName("RandomName2");
        auditProjectEntity1.setStartDate(Date.valueOf("2000-01-01"));
        auditProjectEntity1.setEndDate(Date.valueOf("2001-01-01"));
        auditProjectEntity1.setCustomerByCustomerId(customerEntity1);

        auditProjectRepository.save(auditProjectEntity);
        auditProjectRepository.save(auditProjectEntity1);
    }

    @Test
    public void testInsert(){
        testEntity = new AnswerEntity();
        testEntity.setAnswer(true);
        testEntity.setProof(PROOF);
        testEntity.setAuditId(auditProjectEntity.getId());
        testEntity.setQuestionId(questionEntity.getId());

        AnswerEntity toTest = answerRepository.save(testEntity);
        Assert.assertTrue(answerRepository.exists(Example.of(toTest)));
    }

    @Test
    public void testInsertPrimaryKeyNull(){
        boolean threwException = false;
        boolean threwException1 = false;
        boolean threwException2 = false;
        testEntity = new AnswerEntity();

        try{
            testEntity.setAuditId(0);
            testEntity.setQuestionId(0);
            answerRepository.save(testEntity);
        }catch (TransactionSystemException tse){
            threwException = true;
        }
        try{
            testEntity.setAuditId(auditProjectEntity.getId());
            testEntity.setQuestionId(0);
            answerRepository.save(testEntity);
        }catch (TransactionSystemException tse){
            threwException1 = true;
        }
        try{
            testEntity.setAuditId(0);
            testEntity.setQuestionId(questionEntity.getId());
            answerRepository.save(testEntity);
        }catch (TransactionSystemException tse){
            threwException2 = true;
        }

        Assert.assertTrue(threwException);
        Assert.assertTrue(threwException1);
        Assert.assertTrue(threwException2);
    }

    @Test
    public void testInsertPrimaryKeyUnique(){
        int auditId = auditProjectEntity.getId();
        int questionId = questionEntity.getId();

        testEntity = new AnswerEntity();
        testEntity.setAnswer(ANSWER);
        testEntity.setProof(PROOF);
        testEntity.setAuditId(auditId);
        testEntity.setQuestionId(questionId);

        testEntity2 = new AnswerEntity();
        testEntity2.setAnswer(ANSWER);
        testEntity2.setProof(PROOF+"2");
        testEntity2.setAuditId(auditId);
        testEntity2.setQuestionId(questionEntity1.getId());

        answerRepository.save(testEntity);
        answerRepository.save(testEntity2);
    }

    /*
    @Test
    public void testUpdatePrimaryKeyNull(){
        boolean threwException = false;
        boolean threwException1 = false;
        boolean threwException2 = false;

        testEntity = new AnswerEntity();
        testEntity.setAnswer(ANSWER);
        testEntity.setProof(PROOF);
        testEntity.setAuditId(auditProjectEntity.getId());
        testEntity.setQuestionId(questionEntity.getId());

        AnswerEntity toTest = answerRepository.save(testEntity);
        try{
            toTest.setAuditId(-1);
            toTest.setQuestionId(-1);
            answerRepository.save(toTest);
        }catch (DataIntegrityViolationException dive){
            threwException = true;
        }
        try{
            toTest.setQuestionId(-1);
            answerRepository.save(toTest);
        }catch (DataIntegrityViolationException dive){
            threwException1 = true;
        }
        try{
            toTest.setAuditId(-1);
            answerRepository.save(testEntity);
        }catch (DataIntegrityViolationException dive){
            threwException2 = true;
        }

        Assert.assertTrue(threwException);
        Assert.assertTrue(threwException1);
        Assert.assertTrue(threwException2);
    }

     */

    @Test(expected = TransactionSystemException.class)
    public void testInsertAnswerNull(){
        testEntity = new AnswerEntity();
        testEntity.setAnswer(null);
        testEntity.setProof(PROOF);
        testEntity.setAuditId(auditProjectEntity.getId());
        testEntity.setQuestionId(questionEntity.getId());

        answerRepository.save(testEntity);
    }

    @Test(expected = TransactionSystemException.class)
    public void testUpdateAnswerNull(){
        testEntity = new AnswerEntity();
        testEntity.setAnswer(ANSWER);
        testEntity.setProof(PROOF);
        testEntity.setAuditId(auditProjectEntity.getId());
        testEntity.setQuestionId(questionEntity.getId());
        AnswerEntity toTest = answerRepository.save(testEntity);
        toTest.setAnswer(null);

        answerRepository.save(toTest);
    }

    @Test
    public void testUpdateAnswer(){
        testEntity = new AnswerEntity();
        testEntity.setAnswer(true);
        testEntity.setProof(PROOF);
        testEntity.setAuditId(auditProjectEntity.getId());
        testEntity.setQuestionId(questionEntity.getId());
        boolean answerOld = testEntity.getAnswer();

        AnswerEntity toTest = answerRepository.save(testEntity);
        toTest.setAnswer(false);
        answerRepository.save(toTest);
        boolean answerNew = toTest.getAnswer();

        Assert.assertFalse(answerOld == answerNew);
    }

    @Test
    public void testUpdateProof(){
        testEntity = new AnswerEntity();
        testEntity.setAnswer(true);
        testEntity.setProof(PROOF);
        testEntity.setAuditId(auditProjectEntity.getId());
        testEntity.setQuestionId(questionEntity.getId());
        String proofOld = testEntity.getProof();

        AnswerEntity toTest = answerRepository.save(testEntity);
        toTest.setProof("Random");
        answerRepository.save(toTest);
        String proofNew = toTest.getProof();

        Assert.assertFalse(proofOld.equals(proofNew));
    }

    @Test
    public void testDeleteEntity(){
        testEntity = new AnswerEntity();
        testEntity.setAnswer(true);
        testEntity.setProof(PROOF);
        testEntity.setAuditId(auditProjectEntity.getId());
        testEntity.setQuestionId(questionEntity.getId());

        answerRepository.save(testEntity);
        answerRepository.delete(testEntity);

        Assert.assertFalse(answerRepository.exists(Example.of(testEntity)));
    }

    @Test
    public void testHashCode(){
        testEntity = new AnswerEntity();
        testEntity.setAnswer(true);
        testEntity.setProof(PROOF);
        testEntity.setAuditId(auditProjectEntity.getId());
        testEntity.setQuestionId(questionEntity.getId());

        testEntity1 = new AnswerEntity();
        testEntity1.setAnswer(true);
        testEntity1.setProof(PROOF);
        testEntity1.setAuditId(auditProjectEntity1.getId());
        testEntity1.setQuestionId(questionEntity1.getId());

        testEntity2 = testEntity;

        Assert.assertTrue(testEntity.hashCode()!=testEntity1.hashCode());
        Assert.assertTrue(testEntity2.hashCode()==testEntity.hashCode());
    }

    @Test
    public void testEquals(){
        testEntity = new AnswerEntity();
        testEntity.setAnswer(true);
        testEntity.setProof(PROOF);
        testEntity.setAuditId(auditProjectEntity.getId());
        testEntity.setQuestionId(questionEntity.getId());

        testEntity1 = new AnswerEntity();
        testEntity1.setAnswer(true);
        testEntity1.setProof(PROOF);
        testEntity1.setAuditId(auditProjectEntity1.getId());
        testEntity1.setQuestionId(questionEntity1.getId());

        testEntity2 = testEntity;

        Assert.assertFalse(testEntity.equals(testEntity1));
        Assert.assertTrue(testEntity2.equals(testEntity));
    }

    @After
    public void tearDown() {
        answerRepository.delete(testEntity);
        auditProjectRepository.delete(auditProjectEntity);
        auditProjectRepository.delete(auditProjectEntity1);
        questionRepository.delete(questionEntity);
        questionRepository.delete(questionEntity1);

        if(testEntity1 != null){
            answerRepository.delete(testEntity1);
        }
        if(testEntity2 != null){
            answerRepository.delete(testEntity2);
        }
    }
}
