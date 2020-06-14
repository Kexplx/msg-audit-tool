package com.amos2020.javabackend.rest_service;


import com.amos2020.javabackend.rest_service.controller.ContactPersonController;
import com.amos2020.javabackend.rest_service.request.contactPerson.CreateContactPersonRequest;
import com.amos2020.javabackend.rest_service.request.contactPerson.UpdateContactPersonRequest;
import com.amos2020.javabackend.rest_service.response.BasicContactPersonResponse;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ContactPersonRestService {
    private final ContactPersonController contactPersonController;

    public ContactPersonRestService(ContactPersonController contactPersonController) {
        this.contactPersonController = contactPersonController;
    }

    /**
     * POST endpoint for creating a contact person
     *
     * @param request CreateContactPersonRequest
     * @return BasicContactPersonResponse
     */
    @PostMapping("/contactpersons")
    public ResponseEntity<BasicContactPersonResponse> createAudit(@RequestBody CreateContactPersonRequest request) {
        BasicContactPersonResponse response;
        try {
            // Validate parameters for creating a contact Person
            request.isValid();
            response = contactPersonController.createContactPerson(request.getSalutation(), request.getTitle(), request.getForename(), request.getSurname(),
                    request.getCompanyName(), request.getDepartment(), request.getSector(), request.getCorporateDivision());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(response);
    }


    /**
     * GET endpoint for fetching a list of all contact persons
     *
     * @return List<BasicContactPersonResponse>
     */
    @GetMapping("/contactpersons")
    public ResponseEntity<List<BasicContactPersonResponse>> getAuditAll() {
        List<BasicContactPersonResponse> response;
        try {
            response = contactPersonController.getAllAudits();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(response);
    }


    /**
     * GET endpoint for fetching a specific contact person by id
     *
     * @param contactPersonId int
     * @return BasicContactPersonResponse
     */
    @GetMapping("/contactpersons/{id}")
    public ResponseEntity<BasicContactPersonResponse> getAuditById(@PathVariable("id") int contactPersonId) {
        BasicContactPersonResponse response;
        try {
            response = contactPersonController.getContactPersonById(contactPersonId);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    /**
     * PUT Endpoint for updating a contact person
     *
     * @return BasicContactPersonResponse
     */
    @PutMapping("/contactpersons/{id}")
    public ResponseEntity<BasicContactPersonResponse> updateInterview(@PathVariable("id") int contactPersonId, @RequestBody UpdateContactPersonRequest request) {
        BasicContactPersonResponse response;

        try {
            request.isValid();
            request.assertIdIsValid(contactPersonId);
            response = contactPersonController.updateContactPerson(contactPersonId, request.getSalutation(), request.getTitle(), request.getForename(), request.getSurname(), request.getContactInformation(), request.getCompanyName(), request.getDepartment(), request.getSector(), request.getCorporateDivision());
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(response);
    }
}
