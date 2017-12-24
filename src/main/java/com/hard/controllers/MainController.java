package com.hard.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MainController {
    @GetMapping(value = "", produces = MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8")
    public String main() {
        return "Hello World";
    }
}
