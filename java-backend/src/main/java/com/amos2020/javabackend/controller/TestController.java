package com.amos2020.javabackend.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {




    @RequestMapping(
            method = RequestMethod.GET,
            path = "/test",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void saveProject() {


    }
}
