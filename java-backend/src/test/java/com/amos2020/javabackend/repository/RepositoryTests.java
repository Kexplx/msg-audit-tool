package com.amos2020.javabackend.repository;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({AnswerRepositoryTest.class, AuditRepositoryTest.class, ContactPersonRepositoryTest.class, InterviewRepositoryTest.class, ScopeRepositoryTest.class})
public class RepositoryTests {
}
