package com.amos2020.javabackend.integration_tests.scope;

import com.amos2020.javabackend.JavaBackendApplication;
import com.amos2020.javabackend.entity.Scope;
import com.amos2020.javabackend.rest_service.request.scope.AddScopeRequest;
import com.amos2020.javabackend.rest_service.request.scope.UpdateScopeRequest;
import com.amos2020.javabackend.rest_service.response.BasicContactPersonResponse;
import com.amos2020.javabackend.rest_service.response.BasicScopeResponse;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = JavaBackendApplication.class
)
@Sql("/ScopeTest.sql")
public class ScopeRestServiceIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;


    @Test
    public void getScopeById() {
        //ResponseEntity<BasicScopeResponse> response = testRestTemplate.getForEntity("/audits/1/scope", BasicScopeResponse.class);
        ResponseEntity<BasicScopeResponse> response = testRestTemplate.getForEntity("/audits/1/scope/3", BasicScopeResponse.class);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1,response.getBody().getAuditId());
        assertEquals(3,response.getBody().getFacCritId());
        assertNull(response.getBody().getChange_note());
        assertNull(response.getBody().getNote());
        assertEquals(false,response.getBody().isRemoved());
    }

    @Test
    public void getScopeById_FacCritIdNotExisting() {
        ResponseEntity<BasicScopeResponse> response = testRestTemplate.getForEntity("/audits/1/scope/1", BasicScopeResponse.class);
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    public void getScopeById_AuditIdNotExisting() {
        ResponseEntity<BasicScopeResponse> response = testRestTemplate.getForEntity("/audits/1000/scope/3", BasicScopeResponse.class);
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    public void getAllScopesByAuditId() {
        ResponseEntity<List> response = testRestTemplate.getForEntity("/audits/1/scope", List.class);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
    }

    /*
    @Test
    public void getAllScopesByAuditId_AuditIdNotExisting() {
        ResponseEntity<List> response = testRestTemplate.getForEntity("/audits/1000/scope", List.class);

        assertEquals(400, response.getStatusCodeValue());

    }
    */

    @Test
    public void addScope() {
        AddScopeRequest addScopeRequest = new AddScopeRequest();
        List<Integer> scope = new ArrayList<>();
        scope.add(4);
        scope.add(5);
        addScopeRequest.setScope(scope);

        HttpEntity<AddScopeRequest> request = new HttpEntity<>(addScopeRequest);
        ResponseEntity<List> response = testRestTemplate.postForEntity("/audits/1/scope", request, List.class);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(4, response.getBody().size());
    }

    @Test
    public void addScopeAlreadyExisting() {
        AddScopeRequest addScopeRequest = new AddScopeRequest();
        List<Integer> scope = new ArrayList<>();
        scope.add(2);
        addScopeRequest.setScope(scope);

        HttpEntity<AddScopeRequest> request = new HttpEntity<>(addScopeRequest);
        ResponseEntity<List> response = testRestTemplate.postForEntity("/audits/1/scope", request, List.class);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void addScopeAuditIdNotExisting() {
        AddScopeRequest addScopeRequest = new AddScopeRequest();
        List<Integer> scope = new ArrayList<>();
        scope.add(4);
        scope.add(5);
        addScopeRequest.setScope(scope);

        HttpEntity<AddScopeRequest> request = new HttpEntity<>(addScopeRequest);
        ResponseEntity<List> response = testRestTemplate.postForEntity("/audits/1000/scope", request, List.class);
        assertEquals(404, response.getStatusCodeValue());
    }

    /*
    @Test
    public void addScopeFacCritIdNotExisting() {
        AddScopeRequest addScopeRequest = new AddScopeRequest();
        List<Integer> scope = new ArrayList<>();
        scope.add(1000);
        addScopeRequest.setScope(scope);

        HttpEntity<AddScopeRequest> request = new HttpEntity<>(addScopeRequest);
        ResponseEntity<List> response = testRestTemplate.postForEntity("/audits/1/scope", request, List.class);
        assertEquals(404, response.getStatusCodeValue());
    }
    */

    @Test
    public void addScopeFacCritIdNegative() {
        AddScopeRequest addScopeRequest = new AddScopeRequest();
        List<Integer> scope = new ArrayList<>();
        scope.add(-1);
        addScopeRequest.setScope(scope);

        HttpEntity<AddScopeRequest> request = new HttpEntity<>(addScopeRequest);
        ResponseEntity<List> response = testRestTemplate.postForEntity("/audits/1/scope", request, List.class);
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    public void changeScope() {
        UpdateScopeRequest updateScopeRequest = new UpdateScopeRequest();
        updateScopeRequest.setNote("TestNote");
        updateScopeRequest.setChange_note("TestChangeNote");
        updateScopeRequest.setRemoved(true);

        HttpEntity<UpdateScopeRequest> request = new HttpEntity<>(updateScopeRequest);

        ResponseEntity<BasicScopeResponse> response = testRestTemplate.exchange("/audits/1/scope/3", HttpMethod.PUT, request, BasicScopeResponse.class);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(true, request.getBody().isRemoved());
        assertEquals("TestNote", request.getBody().getNote());
        assertEquals("TestChangeNote", request.getBody().getChange_note());
    }

    @Test
    public void changeScopeNoteTooLong() {
        String note = StringUtils.repeat("a", 8097);
        UpdateScopeRequest updateScopeRequest = new UpdateScopeRequest();
        updateScopeRequest.setNote(note);
        updateScopeRequest.setChange_note("TestChangeNote");
        updateScopeRequest.setRemoved(true);

        HttpEntity<UpdateScopeRequest> request = new HttpEntity<>(updateScopeRequest);

        ResponseEntity<BasicScopeResponse> response = testRestTemplate.exchange("/audits/1/scope/3", HttpMethod.PUT, request, BasicScopeResponse.class);
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    public void changeScopeChangeNoteTooLong() {
        String note = StringUtils.repeat("a", 1025);
        UpdateScopeRequest updateScopeRequest = new UpdateScopeRequest();
        updateScopeRequest.setNote("TestNote");
        updateScopeRequest.setChange_note(note);
        updateScopeRequest.setRemoved(true);

        HttpEntity<UpdateScopeRequest> request = new HttpEntity<>(updateScopeRequest);

        ResponseEntity<BasicScopeResponse> response = testRestTemplate.exchange("/audits/1/scope/3", HttpMethod.PUT, request, BasicScopeResponse.class);
        assertEquals(400, response.getStatusCodeValue());
    }

    /*
    @Test
    public void changeScopeFacCritIdNotExisting() {
        UpdateScopeRequest updateScopeRequest = new UpdateScopeRequest();
        updateScopeRequest.setNote("TestNote");
        updateScopeRequest.setChange_note("TestChangeNote");
        updateScopeRequest.setRemoved(true);

        HttpEntity<UpdateScopeRequest> request = new HttpEntity<>(updateScopeRequest);

        ResponseEntity<BasicScopeResponse> response = testRestTemplate.exchange("/audits/1/scope/1000", HttpMethod.PUT, request, BasicScopeResponse.class);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(true, request.getBody().isRemoved());
        assertEquals("TestNote", request.getBody().getNote());
        assertEquals("TestChangeNote", request.getBody().getChange_note());
    }
    */

    @Test
    public void changeScopeAuditIdNotExisting() {
        UpdateScopeRequest updateScopeRequest = new UpdateScopeRequest();
        updateScopeRequest.setNote("TestNote");
        updateScopeRequest.setChange_note("TestChangeNote");
        updateScopeRequest.setRemoved(true);

        HttpEntity<UpdateScopeRequest> request = new HttpEntity<>(updateScopeRequest);

        ResponseEntity<BasicScopeResponse> response = testRestTemplate.exchange("/audits/1000/scope/3", HttpMethod.PUT, request, BasicScopeResponse.class);
        assertEquals(404, response.getStatusCodeValue());
    }
}
