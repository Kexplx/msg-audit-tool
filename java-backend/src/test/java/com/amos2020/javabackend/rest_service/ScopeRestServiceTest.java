package com.amos2020.javabackend.rest_service;

import com.amos2020.javabackend.entity.Scope;
import com.amos2020.javabackend.rest_service.controller.ScopeController;
import com.amos2020.javabackend.rest_service.response.BasicScopeResponse;
import javassist.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ScopeRestService.class)
public class ScopeRestServiceTest {

    @Autowired
    MockMvc restService;

    @MockBean
    private ScopeController scopeController;

    @Test
    public void getScopeByValidRequest_returnOk() throws Exception {
        int auditId = 1;
        int faccritId = 1;
        Scope scope = new Scope();
        scope.setAuditId(auditId);
        scope.setFaccritId(faccritId);
        given(scopeController.getScopeByIds(auditId, faccritId)).willReturn(new BasicScopeResponse(scope));
        restService.perform(get("/scopes/audit/1/faccrit/1")).andExpect(status().isOk());
    }

    @Test
    public void getScopeByInvalidIds_returns404() throws Exception {
        int auditId = -1;
        int faccritId = -1;
        given(scopeController.getScopeByIds(auditId, faccritId)).willThrow(new NotFoundException(""));
        restService.perform(get("/scopes/audit/-1/faccrit/-1")).andExpect(status().isNotFound());
    }
    
    @Test
    public void getAllScopes_returnsOk() throws Exception{
        given(scopeController.getAllScopes()).willReturn(new ArrayList<>());
        restService.perform(get("/scopes")).andExpect(status().isOk());
    }

}
