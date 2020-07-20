package com.amos2020.javabackend.integration_tests;

import com.amos2020.javabackend.JavaBackendApplication;
import com.amos2020.javabackend.rest_service.request.scope.AddScopeRequest;
import com.amos2020.javabackend.rest_service.request.scope.UpdateScopeRequest;
import com.amos2020.javabackend.rest_service.response.BasicScopeResponse;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql("/ScopeTest.sql")
@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = JavaBackendApplication.class
)
public class ScopeIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void getScopeById() {
        //ResponseEntity<BasicScopeResponse> response = testRestTemplate.getForEntity("/audits/1/scope", BasicScopeResponse.class);
        ResponseEntity<BasicScopeResponse> response = testRestTemplate.getForEntity("/audits/1001/scope/1003", BasicScopeResponse.class);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1001, response.getBody().getAuditId());
        assertEquals(1003, response.getBody().getFacCritId());
        assertNull(response.getBody().getChange_note());
        assertNull(response.getBody().getNote());
        assertEquals(false, response.getBody().isRemoved());
    }

    @Test
    public void getScopeById_FacCritIdNotExisting() {
        ResponseEntity<BasicScopeResponse> response = testRestTemplate.getForEntity("/audits/1001/scope/1001", BasicScopeResponse.class);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void getScopeById_AuditIdNotExisting() {
        ResponseEntity<BasicScopeResponse> response = testRestTemplate.getForEntity("/audits/1000/scope/1003", BasicScopeResponse.class);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void getAllScopesByAuditId() {
        ResponseEntity<List> response = testRestTemplate.getForEntity("/audits/1001/scope", List.class);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void getAllScopesByAuditId_AuditIdNotExisting() {
        ResponseEntity<List> response = testRestTemplate.getForEntity("/audits/1000/scope", List.class);

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    public void addScope() {
        AddScopeRequest addScopeRequest = new AddScopeRequest();
        List<Integer> scope = new ArrayList<>();
        scope.add(1004);
        scope.add(1005);
        addScopeRequest.setScope(scope);

        HttpEntity<AddScopeRequest> request = new HttpEntity<>(addScopeRequest);
        ResponseEntity<List> response = testRestTemplate.postForEntity("/audits/1001/scope", request, List.class);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(4, response.getBody().size());
    }

    @Test
    public void addScopeAlreadyExisting() {
        AddScopeRequest addScopeRequest = new AddScopeRequest();
        List<Integer> scope = new ArrayList<>();
        scope.add(1002);
        addScopeRequest.setScope(scope);

        HttpEntity<AddScopeRequest> request = new HttpEntity<>(addScopeRequest);
        ResponseEntity<List> response = testRestTemplate.postForEntity("/audits/1001/scope", request, List.class);
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

    @Test
    public void addScopeFacCritIdNegative() {
        AddScopeRequest addScopeRequest = new AddScopeRequest();
        List<Integer> scope = new ArrayList<>();
        scope.add(-1);
        addScopeRequest.setScope(scope);

        HttpEntity<AddScopeRequest> request = new HttpEntity<>(addScopeRequest);
        ResponseEntity<Object> response = testRestTemplate.postForEntity("/audits/1001/scope", request, Object.class);
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    public void changeScope() {
        UpdateScopeRequest updateScopeRequest = new UpdateScopeRequest();
        updateScopeRequest.setNote("TestNote");
        updateScopeRequest.setChange_note("TestChangeNote");
        updateScopeRequest.setRemoved(true);

        HttpEntity<UpdateScopeRequest> request = new HttpEntity<>(updateScopeRequest);

        ResponseEntity<BasicScopeResponse> response = testRestTemplate.exchange("/audits/1001/scope/1003", HttpMethod.PUT, request, BasicScopeResponse.class);
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

        ResponseEntity<BasicScopeResponse> response = testRestTemplate.exchange("/audits/1001/scope/1003", HttpMethod.PUT, request, BasicScopeResponse.class);
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

        ResponseEntity<BasicScopeResponse> response = testRestTemplate.exchange("/audits/1001/scope/1003", HttpMethod.PUT, request, BasicScopeResponse.class);
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    public void changeScopeFacCritIdNotExisting() {
        UpdateScopeRequest updateScopeRequest = new UpdateScopeRequest();
        updateScopeRequest.setNote("TestNote");
        updateScopeRequest.setChange_note("TestChangeNote");
        updateScopeRequest.setRemoved(true);

        HttpEntity<UpdateScopeRequest> request = new HttpEntity<>(updateScopeRequest);

        ResponseEntity<BasicScopeResponse> response = testRestTemplate.exchange("/audits/1/scope/1000", HttpMethod.PUT, request, BasicScopeResponse.class);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void changeScopeAuditIdNotExisting() {
        UpdateScopeRequest updateScopeRequest = new UpdateScopeRequest();
        updateScopeRequest.setNote("TestNote");
        updateScopeRequest.setChange_note("TestChangeNote");
        updateScopeRequest.setRemoved(true);

        HttpEntity<UpdateScopeRequest> request = new HttpEntity<>(updateScopeRequest);

        ResponseEntity<BasicScopeResponse> response = testRestTemplate.exchange("/audits/1000/scope/1003", HttpMethod.PUT, request, BasicScopeResponse.class);
        assertEquals(404, response.getStatusCodeValue());
    }
}
