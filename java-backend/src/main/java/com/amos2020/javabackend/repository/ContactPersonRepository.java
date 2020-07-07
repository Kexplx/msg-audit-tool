package com.amos2020.javabackend.repository;

import com.amos2020.javabackend.entity.ContactPerson;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Interface to access the contact_person table in the database
 */
public interface ContactPersonRepository extends JpaRepository<ContactPerson, Integer> {
}

