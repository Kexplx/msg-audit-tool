package com.amos2020.javabackend.controller;


import com.amos2020.javabackend.controller.request.CreateContactPersonRequest;
import com.amos2020.javabackend.controller.response.BasicContactPersonResponse;
import com.amos2020.javabackend.entity.ContactPerson;
import com.amos2020.javabackend.service.ContactPersonService;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    /**
     * GET endpoint for fetching a specific contact person by id
     *
     * @param contactPersonId int
     * @return BasicAuditResponse
     */
    @GetMapping("/contactperson/{id}")
    public ResponseEntity<BasicContactPersonResponse> getAuditById(@PathVariable("id") int contactPersonId) {
        BasicContactPersonResponse response;
        try {
            ContactPerson contactPerson = contactPersonService.getContactPersonById(contactPersonId);
            response = new BasicContactPersonResponse(contactPerson);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }
}
