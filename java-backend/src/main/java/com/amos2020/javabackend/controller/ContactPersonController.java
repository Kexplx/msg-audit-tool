package com.amos2020.javabackend.controller;


import com.amos2020.javabackend.controller.request.CreateContactPersonRequest;
import com.amos2020.javabackend.controller.response.BasicContactPersonResponse;
import com.amos2020.javabackend.entity.ContactPerson;
import com.amos2020.javabackend.service.ContactPersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ContactPersonController {
    private final ContactPersonService contactPersonService;

    public ContactPersonController(ContactPersonService contactPersonService) {
        this.contactPersonService =  contactPersonService;
    }

    /**
     * POST endpoint for creating a contact person
     *
     * @param request CreateContactPersonRequest
     * @return BasicContactPersonResponse
     */
    @PostMapping("/contactperson")
    public ResponseEntity<BasicContactPersonResponse> createAudit(@RequestBody CreateContactPersonRequest request) {
        BasicContactPersonResponse response;
        try {
            // Validate parameters for creating a contact Person
            request.isValid();
            // Create audit and save in database
            ContactPerson contactPerson = contactPersonService.createContactPerson(request.getSalutation(), request.getTitle(), request.getForename(), request.getSurname(),
                    request.getCompanyName(), request.getDepartment(), request.getSector(), request.getCorporateDivision());
            response = new BasicContactPersonResponse(contactPerson);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(response);
    }


    /**
     * GET endpoint for fetching a list of all contact persons
     *
     * @return List<BasicAuditResponse>
     */
    @GetMapping("/contactperson/all")
    public ResponseEntity<List<BasicContactPersonResponse>> getAuditAll() {
        List<BasicContactPersonResponse> response = new ArrayList<>();
        try {
            for (ContactPerson c : contactPersonService.getAll()) {
                response.add(new BasicContactPersonResponse(c));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(response);
    }
}
