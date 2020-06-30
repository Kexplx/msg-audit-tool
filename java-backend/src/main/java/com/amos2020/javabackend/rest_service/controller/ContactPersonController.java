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

    /**
     * Create a new contactPerson
     *
     * @param salutation         Salutation
     * @param title              String
     * @param forename           String
     * @param surname            String
     * @param companyName        String
     * @param department         String
     * @param sector             String
     * @param corporate_division String
     * @return Created ContactPerson
     */
    public BasicContactPersonResponse createContactPerson(Salutation salutation, String title, String forename, String surname, String companyName, String department, String sector, String corporate_division) {
        // Create contact person and save in database

        if (forename == null || surname == null || companyName == null || corporate_division == null)
            throw new IllegalArgumentException();

        ContactPerson contactPerson = contactPersonService.createContactPerson(salutation, title, forename, surname, companyName, department, sector, corporate_division);
        return new BasicContactPersonResponse(contactPerson);
    }

    /**
     * Get all existing contactPersons
     *
     * @return List of all ContactPerson objects
     */
    public List<BasicContactPersonResponse> getAllContactPersons() {
        List<BasicContactPersonResponse> response = new ArrayList<>();
        for (ContactPerson c : contactPersonService.getAll()) {
            response.add(new BasicContactPersonResponse(c));
        }
        return response;
    }

    /**
     * Get a ContactPerson by an id
     *
     * @param contactPersonId int
     * @return ContactPerson
     * @throws NotFoundException If contactPerson id is invalid and can not be found
     */
    public BasicContactPersonResponse getContactPersonById(int contactPersonId) throws NotFoundException {
        ContactPerson contactPerson = contactPersonService.getContactPersonById(contactPersonId);
        return new BasicContactPersonResponse(contactPerson);
    }

    /**
     * Update an existing contactPerson
     *
     * @param contactPersonId    int
     * @param salutation         Salutation
     * @param title              String
     * @param forename           String
     * @param surname            String
     * @param contactInformation String
     * @param companyName        String
     * @param department         String
     * @param sector             String
     * @param corporate_division String
     * @return Updated ContactPerson
     * @throws NotFoundException If ContactPerson can not be found
     */
    public BasicContactPersonResponse updateContactPerson(int contactPersonId, Salutation salutation, String title, String forename, String surname, String contactInformation, String companyName, String department, String sector, String corporate_division) throws NotFoundException {
        if (forename == null || surname == null || companyName == null || corporate_division == null)
            throw new IllegalArgumentException();

        ContactPerson contactPerson = contactPersonService.getContactPersonById(contactPersonId);
        contactPerson.setSalutation(salutation);
        contactPerson.setTitle(title);
        contactPerson.setForename(forename);
        contactPerson.setSurname(surname);
        contactPerson.setContactInformation(contactInformation);
        contactPerson.setCompanyName(companyName);
        contactPerson.setDepartment(department);
        contactPerson.setSector(sector);
        contactPerson.setCorporateDivision(corporate_division);
        return new BasicContactPersonResponse(contactPersonService.updateContactPerson(contactPerson));
    }
}
