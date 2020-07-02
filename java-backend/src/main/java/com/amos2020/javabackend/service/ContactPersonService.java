package com.amos2020.javabackend.service;

import com.amos2020.javabackend.entity.ContactPerson;
import com.amos2020.javabackend.entity.Salutation;
import com.amos2020.javabackend.repository.ContactPersonRepository;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactPersonService {

    final ContactPersonRepository repository;

    public ContactPersonService(ContactPersonRepository repository) {
        this.repository = repository;
    }

    /**
     * Create a new ContactPerson
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
    public ContactPerson createContactPerson(Salutation salutation, String title, String forename, String surname,
                                             String companyName, String department, String sector, String corporate_division) {

        ContactPerson contactPerson = new ContactPerson();
        contactPerson.setSalutation(salutation);
        contactPerson.setTitle(title);
        contactPerson.setForename(forename);
        contactPerson.setSurname(surname);
        contactPerson.setCompanyName(companyName);
        contactPerson.setDepartment(department);
        contactPerson.setSector(sector);
        contactPerson.setCorporateDivision(corporate_division);

        return repository.save(contactPerson);
    }

    /**
     * Get all ContactPersons for a given list of contactPerson ids
     *
     * @param contactPersonIds List<Integer>
     * @return List of ContactPersons
     * @throws NotFoundException If a contactPerson id is invalid and can not be found
     */
    public List<ContactPerson> getAllByIds(List<Integer> contactPersonIds) throws NotFoundException {
        List<ContactPerson> contactPersons = repository.findAllById(contactPersonIds);
        if (contactPersons.size() != contactPersonIds.size()) {
            throw new NotFoundException("Not all contact person ids are valid");
        }

        return contactPersons;
    }

    /**
     * Get all existing contactPersons
     *
     * @return List<ContactPerson>
     */
    public List<ContactPerson> getAll() {
        return repository.findAll();
    }

    /**
     * Get a specific contactPerson by id
     *
     * @param contactPersonId int
     * @return ContactPerson
     * @throws NotFoundException If the contactPerson id is invalid and can not be found
     */
    public ContactPerson getContactPersonById(int contactPersonId) throws NotFoundException {
        Optional<ContactPerson> contactPerson = repository.findById(contactPersonId);
        if (!contactPerson.isPresent()) {
            throw new NotFoundException("No audit found with id " + contactPersonId);
        }
        return contactPerson.get();
    }

    /**
     * Update an existing contactPerson
     *
     * @param contactPerson ContactPerson
     * @return Updated ContactPerson
     */
    public ContactPerson updateContactPerson(ContactPerson contactPerson) {
        return repository.save(contactPerson);
    }

}

