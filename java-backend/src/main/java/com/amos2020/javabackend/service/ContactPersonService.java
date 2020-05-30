package com.amos2020.javabackend.service;

import com.amos2020.javabackend.entity.ContactPerson;
import com.amos2020.javabackend.repository.ContactPersonRepository;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactPersonService {

    final ContactPersonRepository repository;

    public ContactPersonService(ContactPersonRepository repository) {
        this.repository = repository;
    }

    public List<ContactPerson> getAllByIds(List<Integer> contactPersonIds) throws NotFoundException {
        List<ContactPerson> contactPeople = repository.findAllById(contactPersonIds);
        if (contactPeople.size() != contactPersonIds.size()) {
            throw new NotFoundException("Not all contact person ids are valid");
        }

        return contactPeople;
    }
}

