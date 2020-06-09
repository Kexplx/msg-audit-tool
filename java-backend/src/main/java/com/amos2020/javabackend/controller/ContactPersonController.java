package com.amos2020.javabackend.controller;


import com.amos2020.javabackend.controller.request.CreateContactPersonRequest;
import com.amos2020.javabackend.controller.response.BasicContactPersonResponse;
import com.amos2020.javabackend.entity.ContactPerson;
import com.amos2020.javabackend.service.ContactPersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
