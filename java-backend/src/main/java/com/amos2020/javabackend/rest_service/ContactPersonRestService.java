package com.amos2020.javabackend.rest_service;


import com.amos2020.javabackend.rest_service.controller.ContactPersonController;
import com.amos2020.javabackend.rest_service.request.contactPerson.CreateContactPersonRequest;
import com.amos2020.javabackend.rest_service.request.contactPerson.UpdateContactPersonRequest;
import com.amos2020.javabackend.rest_service.response.BasicContactPersonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
/**
 * Provides endpoints for the contactPerson resource under /contactpersons
 */
@RestController
@Validated
@Tag(name = "ContactPerson", description = "The endpoints for the contactPerson resource")
@CrossOrigin
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
    @Operation(summary = "Create a new contact person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created a new contact person"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
    })
    @PostMapping("/contactpersons")
    public ResponseEntity<BasicContactPersonResponse> createAudit(@RequestBody @Valid CreateContactPersonRequest request) {
        BasicContactPersonResponse response;
        try {
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
    @Operation(summary = "Get all existing contact persons")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Received all existing contact persons"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
    })
    @GetMapping("/contactpersons")
    public ResponseEntity<List<BasicContactPersonResponse>> getAuditAll() {
        List<BasicContactPersonResponse> response;
        try {
            response = contactPersonController.getAllContactPersons();
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
    @Operation(summary = "Get contact person by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Receive contact person with specific id"),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
    })
    @GetMapping("/contactpersons/{id}")
    public ResponseEntity<BasicContactPersonResponse> getAuditById(@PathVariable("id") @Parameter(name = "id", example = "1") @Min(1) int contactPersonId) {
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
    @Operation(summary = "Update an existing contact person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the contact person"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
    })
    @PutMapping("/contactpersons/{id}")
    public ResponseEntity<BasicContactPersonResponse> updateInterview(@PathVariable("id") @Parameter(name = "id", example = "1") @Min(1) int contactPersonId, @RequestBody @Valid UpdateContactPersonRequest request) {
        BasicContactPersonResponse response;
        try {
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
