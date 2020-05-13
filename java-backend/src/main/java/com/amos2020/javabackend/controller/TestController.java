package com.amos2020.javabackend.controller;

import com.amos2020.javabackend.entity.*;
import com.amos2020.javabackend.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;

@RestController
public class TestController {

    @Autowired
    private AnswerRepository repository;


    @RequestMapping(
            method = RequestMethod.GET,
            path = "/test",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void saveProject() {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(1);
        customerEntity.setSector("TestSector");
        customerEntity.setCompanyName("TestCompany");
        customerEntity.setDepartment("TestDepartment");
        ContactPersonEntity entity = new ContactPersonEntity();
        entity.setId(1);
        entity.setName("TestName");
        entity.setEmail("TestEmail");
        entity.setPhoneNumber("TestNumber");
        entity.setTitle("TestTitle");

        customerEntity.setContactPersonByContactPersonId(entity);

        FactorEntity factorEntity = new FactorEntity();
        factorEntity.setId(1);
        factorEntity.setIsoName("ISO25010");
        factorEntity.setName("Effektivität");
        CriteriaEntity criteriaEntity = new CriteriaEntity();
        criteriaEntity.setId(1);
        criteriaEntity.setName("Test");
        criteriaEntity.setFactorByFactorId(factorEntity);
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setId(1);
        questionEntity.setQuestion("TestFrage");
        questionEntity.setCriteriaByCriteriaId(criteriaEntity);

        AuditProjectEntity auditProjectEntity = new AuditProjectEntity();
        auditProjectEntity.setId(1);
        auditProjectEntity.setName("TestProject");
        auditProjectEntity.setStartDate(Date.valueOf("2019-10-10"));
        auditProjectEntity.setEndDate(Date.valueOf("2019-10-10"));
        auditProjectEntity.setCustomerByCustomerId(customerEntity);

        com.amos2020.javabackend.entity.AnswerEntity answerEntity = new com.amos2020.javabackend.entity.AnswerEntity();
        answerEntity.setProof("TestProof");
        answerEntity.setAnswer(false);
        answerEntity.setQuestionId(1);
        answerEntity.setAuditId(1);

        repository.save(answerEntity);


    }
}
