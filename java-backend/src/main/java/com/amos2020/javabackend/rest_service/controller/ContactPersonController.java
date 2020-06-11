package com.amos2020.javabackend.rest_service.controller;

import com.amos2020.javabackend.entity.ContactPerson;
import com.amos2020.javabackend.entity.Salutation;
import com.amos2020.javabackend.rest_service.response.BasicContactPersonResponse;
import com.amos2020.javabackend.service.ContactPersonService;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactPersonController {

    final ContactPersonService contactPersonService;

    public ContactPersonController(ContactPersonService contactPersonService) {
        this.contactPersonService = contactPersonService;
    }

    public BasicContactPersonResponse createContactPerson(Salutation salutation, String title, String forename, String surname, String companyName, String department, String sector, String corporate_division) {
        // Create audit and save in database
        ContactPerson contactPerson = contactPersonService.createContactPerson(salutation, title, forename, surname, companyName, department, sector, corporate_division);
        return new BasicContactPersonResponse(contactPerson);
    }

    public List<BasicContactPersonResponse> getAllAudits() {
        List<BasicContactPersonResponse> response = new ArrayList<>();
        for (ContactPerson c : contactPersonService.getAll()) {
            response.add(new BasicContactPersonResponse(c));
        }
        return response;
    }

    public BasicContactPersonResponse getContactPersonById(int contactPersonId) throws NotFoundException {
        ContactPerson contactPerson = contactPersonService.getContactPersonById(contactPersonId);
        return new BasicContactPersonResponse(contactPerson);
    }
}
