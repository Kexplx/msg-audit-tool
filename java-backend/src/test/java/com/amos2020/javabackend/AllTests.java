package com.amos2020.javabackend;

import com.amos2020.javabackend.integration_tests.IntegrationTests;
import com.amos2020.javabackend.repository.RepositoryTests;
import com.amos2020.javabackend.rest_service.RestServiceTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({RestServiceTests.class, RepositoryTests.class, IntegrationTests.class
})
public class AllTests {
}
