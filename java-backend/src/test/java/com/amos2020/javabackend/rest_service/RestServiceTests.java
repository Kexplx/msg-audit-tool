package com.amos2020.javabackend.rest_service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({AnswerRestServiceTest.class, AuditRestServiceTest.class, ContactPersonRestServiceTest.class,
        InterviewRestServiceTest.class, ScopeRestServiceTest.class
})
public class RestServiceTests {
}
