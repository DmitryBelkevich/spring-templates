package com.hard.controllers;

import com.hard.models.User;
import com.hard.services.SecurityService;
import com.hard.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/security")
public class SecurityController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

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

    @GetMapping(value = "/registration")
    public String registration() {
        return "security/registration";
    }

    @PostMapping(value = "/registration")
    public String registration2(@ModelAttribute("user") User user) {
        // TODO - registration
        // validation

        // if errors
        // then return "security/registration";

        String password = user.getPassword();

        userService.add(user);

        securityService.login(user.getUsername(), password);

        return "redirect:/";
    }
}
