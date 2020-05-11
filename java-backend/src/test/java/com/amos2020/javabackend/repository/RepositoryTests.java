package com.amos2020.javabackend.repository;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ AnswerRepositoryTest.class, AuditProjectRepositoryTest.class, ContactPersonRepositoryTest.class,
CriteriaRepositoryTest.class, CustomerRepositoryTest.class, FactorRepositoryTest.class, QuestionRepositoryTest.class, })
public class RepositoryTests {
}
