package com.amos2020.javabackend.repository;

import com.amos2020.javabackend.entity.ContactPerson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactPersonRepository extends JpaRepository<ContactPerson, Integer> {
}

