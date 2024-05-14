package com.program.basicspring.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/dev")
public class MainController {
    
    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello World!";
    }
    
    @PostMapping("/user")
    public Object postUser(@RequestBody Object reqBody) {    
        return new ResponseEntity<>(reqBody, HttpStatus.valueOf(200));
    }
    
}
