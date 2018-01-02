package com.hard.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/security")
public class SecurityController {
    @GetMapping(value = "/login")
    public String login() {
        return "security/login";
    }

    @GetMapping(value = "/access_denied")
    public String accessDenied() {
        return "security/access_denied";
    }

    @GetMapping(value = "/technical_error")
    public String technicalError() {
        return "security/technical_error";
    }
}
