package com.amos2020.javabackend.integration_tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({AnswerIntegrationTest.class, AuditIntegrationTest.class, ContactPersonIntegrationTest.class, FacCritIntegrationTest.class,
        InterviewIntegrationTest.class, ScopeIntegrationTest.class,
})
public class IntegrationTests {
}
