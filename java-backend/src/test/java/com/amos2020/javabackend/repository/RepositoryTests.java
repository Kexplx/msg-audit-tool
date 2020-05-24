package com.amos2020.javabackend.repository;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({AnswerRepositoryTest.class, AuditContactPersonRepositoryTest.class, AuditRepositoryTest.class, ContactPersonRepositoryTest.class,
        FacCritRepositoryTest.class, InterviewRepositoryTest.class, ScopeRepositoryTest.class,
})
public class RepositoryTests {
}
