package com.amos2020.javabackend.service;

import com.amos2020.javabackend.repository.ContactPersonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContactPersonServiceTest {
    @Autowired
    ContactPersonRepository repository;

    @Test
    public void test() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(1);
        ContactPersonService.getInstance(repository).getAllByIds(list);
    }
}
