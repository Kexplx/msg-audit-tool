package com.amos2020.javabackend.integration_tests.contactperson;

import com.amos2020.javabackend.JavaBackendApplication;
import com.amos2020.javabackend.entity.ContactPerson;
import com.amos2020.javabackend.entity.Salutation;
import com.amos2020.javabackend.repository.ContactPersonRepository;
import com.amos2020.javabackend.rest_service.request.contactPerson.CreateContactPersonRequest;
import com.amos2020.javabackend.rest_service.request.contactPerson.UpdateContactPersonRequest;
import com.amos2020.javabackend.rest_service.response.BasicContactPersonResponse;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;
import javax.validation.constraints.Null;

import java.nio.charset.Charset;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql("/ContactPersonTest.sql")
@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = JavaBackendApplication.class
)
public class ContactPersonRestServiceIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;


    @Test
    public void getContactPersonById() {
        ResponseEntity<BasicContactPersonResponse> response = testRestTemplate.getForEntity("/contactpersons/1001", BasicContactPersonResponse.class);
        System.out.println(response.getStatusCode());
        assertEquals(1001, response.getBody().getId());
        assertEquals(Salutation.MANN, response.getBody().getSalutation());
        assertEquals("John", response.getBody().getForename());
        assertEquals("Doe", response.getBody().getSurname());
        //assertEquals("john@doe.com", response.getBody().getContactInformation());
        assertEquals("msg", response.getBody().getCompanyName());
        assertEquals("testSector", response.getBody().getSector());
        assertEquals("testDepartment", response.getBody().getDepartment());
        assertEquals("testDivision", response.getBody().getCorporateDivision());
    }


    @Test
    public void getContactPersonByIdNotExisting() {
        ResponseEntity<BasicContactPersonResponse> response = testRestTemplate.getForEntity("/contactpersons/1000", BasicContactPersonResponse.class);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void addContactPersonMinimalDetails() {
        CreateContactPersonRequest contactPersonRequest = new CreateContactPersonRequest();
        contactPersonRequest.setForename("Foo");
        contactPersonRequest.setSurname("Bar");
        contactPersonRequest.setCompanyName("FooBarCompany");
        contactPersonRequest.setCorporateDivision("FooBarCorporateDivision");

        HttpEntity<CreateContactPersonRequest> request = new HttpEntity<>(contactPersonRequest);

        ResponseEntity<BasicContactPersonResponse>response = testRestTemplate.postForEntity("/contactpersons", request, BasicContactPersonResponse.class);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody().getId());
        assertNull(response.getBody().getSalutation());
        //assertEquals("Foo", response.getBody().getForename());
        assertEquals("Bar", response.getBody().getSurname());
        assertEquals("FooBarCompany", response.getBody().getCompanyName());
        assertNull(response.getBody().getSector());
        assertNull(response.getBody().getDepartment());
        assertEquals("FooBarCorporateDivision", response.getBody().getCorporateDivision());
    }

    @Test
    public void addContactPersonFullDetails() {
        CreateContactPersonRequest contactPersonRequest = new CreateContactPersonRequest();
        contactPersonRequest.setSalutation(Salutation.MANN);
        contactPersonRequest.setTitle("Dr.");
        contactPersonRequest.setForename("Foo");
        contactPersonRequest.setSurname("Bar");
        contactPersonRequest.setCompanyName("FooBarCompany");
        contactPersonRequest.setDepartment("FooBarDepartment");
        contactPersonRequest.setContactInformation("+00123");
        contactPersonRequest.setSector("FooBarSector");
        contactPersonRequest.setCorporateDivision("FooBarCorporateDivision");

        HttpEntity<CreateContactPersonRequest> request = new HttpEntity<>(contactPersonRequest);

        ResponseEntity<BasicContactPersonResponse>response = testRestTemplate.postForEntity("/contactpersons", request, BasicContactPersonResponse.class);

        assertNotNull(response.getBody().getId());
        assertEquals(Salutation.MANN, response.getBody().getSalutation());
        assertEquals("Foo", response.getBody().getForename());
        assertEquals("Bar", response.getBody().getSurname());
        //assertEquals("+00123", response.getBody().getContactInformation());
        assertEquals("FooBarCompany", response.getBody().getCompanyName());
        assertEquals("FooBarSector", response.getBody().getSector());
        assertEquals("FooBarDepartment", response.getBody().getDepartment());
        assertEquals("FooBarCorporateDivision", response.getBody().getCorporateDivision());
    }


    @Test
    public void updateContactPersonWithValidId() {
        UpdateContactPersonRequest contactPersonRequest = new UpdateContactPersonRequest();
        contactPersonRequest.setSalutation(Salutation.FRAU);
        contactPersonRequest.setTitle("Dr.");
        contactPersonRequest.setForename("Updated_John");
        contactPersonRequest.setSurname("Updated_Doe");
        contactPersonRequest.setCompanyName("Updated_msg");
        contactPersonRequest.setDepartment("Updated_testDepartment");
        contactPersonRequest.setContactInformation("Updated_john@doe.com");
        contactPersonRequest.setSector("Updated_testSector");
        contactPersonRequest.setCorporateDivision("Updated_testDivision");

        HttpEntity<UpdateContactPersonRequest> request = new HttpEntity<>(contactPersonRequest);
        ResponseEntity<BasicContactPersonResponse> response = testRestTemplate.exchange("/contactpersons/1001", HttpMethod.PUT, request, BasicContactPersonResponse.class);

        assertEquals(1001, response.getBody().getId());
        assertEquals(Salutation.FRAU, response.getBody().getSalutation());
        assertEquals("Updated_John", response.getBody().getForename());
        assertEquals("Updated_Doe", response.getBody().getSurname());
        //assertEquals("john@doe.com", response.getBody().getContactInformation());
        assertEquals("Updated_msg", response.getBody().getCompanyName());
        assertEquals("Updated_testSector", response.getBody().getSector());
        assertEquals("Updated_testDepartment", response.getBody().getDepartment());
        assertEquals("Updated_testDivision", response.getBody().getCorporateDivision());

    }

    @Test
    public void updateContactPersonWithIdNotExisting() {
        UpdateContactPersonRequest contactPersonRequest = new UpdateContactPersonRequest();
        contactPersonRequest.setSalutation(Salutation.FRAU);
        contactPersonRequest.setTitle("Dr.");
        contactPersonRequest.setForename("Updated_John");
        contactPersonRequest.setSurname("Updated_Doe");
        contactPersonRequest.setCompanyName("Updated_msg");
        contactPersonRequest.setDepartment("Updated_testDepartment");
        contactPersonRequest.setContactInformation("Updated_john@doe.com");
        contactPersonRequest.setSector("Updated_testSector");
        contactPersonRequest.setCorporateDivision("Updated_testDivision");

        HttpEntity<UpdateContactPersonRequest> request = new HttpEntity<>(contactPersonRequest);
        ResponseEntity<BasicContactPersonResponse> response = testRestTemplate.exchange("/contactpersons/1000", HttpMethod.PUT, request, BasicContactPersonResponse.class);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void changeForenameTooLong() {
        String name = StringUtils.repeat("a", 257);
        UpdateContactPersonRequest contactPersonRequest = new UpdateContactPersonRequest();
        contactPersonRequest.setSalutation(Salutation.FRAU);
        contactPersonRequest.setTitle("Dr.");
        contactPersonRequest.setForename(name);
        contactPersonRequest.setSurname("Updated_Doe");
        contactPersonRequest.setCompanyName("Updated_msg");
        contactPersonRequest.setDepartment("Updated_testDepartment");
        contactPersonRequest.setContactInformation("Updated_john@doe.com");
        contactPersonRequest.setSector("Updated_testSector");
        contactPersonRequest.setCorporateDivision("Updated_testDivision");

        HttpEntity<UpdateContactPersonRequest> request = new HttpEntity<>(contactPersonRequest);
        ResponseEntity<BasicContactPersonResponse> response = testRestTemplate.exchange("/contactpersons/1001", HttpMethod.PUT, request, BasicContactPersonResponse.class);
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    public void changeSurnameMaxLength() {
        String name = StringUtils.repeat("a", 255);
        UpdateContactPersonRequest contactPersonRequest = new UpdateContactPersonRequest();
        contactPersonRequest.setSalutation(Salutation.FRAU);
        contactPersonRequest.setTitle("Dr.");
        contactPersonRequest.setForename("Updated_John");
        contactPersonRequest.setSurname(name);
        contactPersonRequest.setCompanyName("Updated_msg");
        contactPersonRequest.setDepartment("Updated_testDepartment");
        contactPersonRequest.setContactInformation("Updated_john@doe.com");
        contactPersonRequest.setSector("Updated_testSector");
        contactPersonRequest.setCorporateDivision("Updated_testDivision");

        HttpEntity<UpdateContactPersonRequest> request = new HttpEntity<>(contactPersonRequest);
        ResponseEntity<BasicContactPersonResponse> response = testRestTemplate.exchange("/contactpersons/1001", HttpMethod.PUT, request, BasicContactPersonResponse.class);

        System.out.println(response.getStatusCode());

        assertEquals(name, response.getBody().getSurname());
    }

    @Test
    public void changeSurnameTooLong() {
        String name = StringUtils.repeat("a", 257);
        UpdateContactPersonRequest contactPersonRequest = new UpdateContactPersonRequest();
        contactPersonRequest.setSalutation(Salutation.FRAU);
        contactPersonRequest.setTitle("Dr.");
        contactPersonRequest.setForename("Updated_John");
        contactPersonRequest.setSurname(name);
        contactPersonRequest.setCompanyName("Updated_msg");
        contactPersonRequest.setDepartment("Updated_testDepartment");
        contactPersonRequest.setContactInformation("Updated_john@doe.com");
        contactPersonRequest.setSector("Updated_testSector");
        contactPersonRequest.setCorporateDivision("Updated_testDivision");

        HttpEntity<UpdateContactPersonRequest> request = new HttpEntity<>(contactPersonRequest);
        ResponseEntity<BasicContactPersonResponse> response = testRestTemplate.exchange("/contactpersons/1001", HttpMethod.PUT, request, BasicContactPersonResponse.class);
        assertEquals(400, response.getStatusCodeValue());
    }



}