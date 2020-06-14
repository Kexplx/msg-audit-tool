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

    public List<ContactPerson> getAllByIds(List<Integer> contactPersonIds) throws NotFoundException {
        List<ContactPerson> contactPeople = repository.findAllById(contactPersonIds);
        if (contactPeople.size() != contactPersonIds.size()) {
            throw new NotFoundException("Not all contact person ids are valid");
        }

        return contactPeople;
    }

    public List<ContactPerson> getAll() {
        return repository.findAll();
    }

    public ContactPerson getContactPersonById(int contactPersonId) throws NotFoundException {
        Optional<ContactPerson> contactPerson = repository.findById(contactPersonId);
        if (!contactPerson.isPresent()) {
            throw new NotFoundException("No audit found with id " + contactPersonId);
        }
        return contactPerson.get();
    }

    public ContactPerson updateContactPerson(ContactPerson contactPerson) {
        return repository.save(contactPerson);
    }

}

