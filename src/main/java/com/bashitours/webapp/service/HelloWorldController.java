package com.bashitours.webapp.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @RequestMapping(path = "hello")
    public String helloWorld(){
        return "hello world";
    }
}
