package com.amos2020.javabackend.rest_service;


import com.amos2020.javabackend.rest_service.request.*;
import com.amos2020.javabackend.entity.*;
import com.amos2020.javabackend.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ContactPersonRestService.class)
public class ContactPersonRestServiceTest {
    @Autowired
    MockMvc restService;

    @MockBean
    private ContactPersonService contactPersonService;

    @Test
    public void createContactPersonWithValidRequest_returnsOK() throws Exception {
        CreateContactPersonRequest request = new CreateContactPersonRequest();
        request.setSalutation(Salutation.FRAU);
        request.setTitle("Dr");
        request.setForename("Test");
        request.setSurname("Test");
        request.setCompanyName("Test");
        request.setDepartment("Test");
        request.setCorporateDivision("Test");
        request.setSector("Test");
        String requestAsJson = buildJson(request);

        ContactPerson cp = new ContactPerson();
        cp.setSalutation(Salutation.FRAU);
        cp.setTitle("Dr");
        cp.setForename("Test");
        cp.setSurname("Test");
        cp.setCompanyName("Test");
        cp.setDepartment("Test");
        cp.setCorporateDivision("Test");
        cp.setSector("Test");


        given(contactPersonService.createContactPerson(request.getSalutation(), request.getTitle(), request.getForename(), request.getSurname(), request.getCompanyName(), request.getDepartment(), request.getSector(),request.getCorporateDivision())).willReturn(cp);

        restService.perform(post("/contactperson")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isOk());
    }

    @Test
    public void createContactPersonWithForenameBlank_returns400() throws Exception {
        CreateContactPersonRequest request = new CreateContactPersonRequest();
        request.setSalutation(Salutation.FRAU);
        request.setTitle("Dr");
        request.setForename("");
        request.setSurname("Test");
        request.setCompanyName("Test");
        request.setDepartment("Test");
        request.setCorporateDivision("Test");
        request.setSector("Test");
        String requestAsJson = buildJson(request);

        restService.perform(post("/contactperson")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }



    @Test
    public void createContactPersonWithForenameNull_returns400() throws Exception{
        CreateContactPersonRequest request = new CreateContactPersonRequest();
        request.setSalutation(Salutation.FRAU);
        request.setTitle("Dr");
        request.setSurname("Test");
        request.setCompanyName("Test");
        request.setDepartment("Test");
        request.setCorporateDivision("Test");
        request.setSector("Test");
        String requestAsJson = buildJson(request);


        restService.perform(post("/contactperson")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }



    @Test
    public void createContactPersonWithSurnameBlank_returns400() throws Exception {
        CreateContactPersonRequest request = new CreateContactPersonRequest();
        request.setSalutation(Salutation.FRAU);
        request.setTitle("Dr");
        request.setForename("Test");
        request.setSurname("");
        request.setCompanyName("Test");
        request.setDepartment("Test");
        request.setCorporateDivision("Test");
        request.setSector("Test");
        String requestAsJson = buildJson(request);


        restService.perform(post("/contactperson")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createContactPersonWithSurnameNull_returns400() throws Exception{
        CreateContactPersonRequest request = new CreateContactPersonRequest();
        request.setSalutation(Salutation.FRAU);
        request.setTitle("Dr");
        request.setForename("Test");
        request.setCompanyName("Test");
        request.setDepartment("Test");
        request.setCorporateDivision("Test");
        request.setSector("Test");
        String requestAsJson = buildJson(request);

        restService.perform(post("/contactperson")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    private String buildJson(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        return mapper.writer().withDefaultPrettyPrinter().writeValueAsString(object);
    }
}
