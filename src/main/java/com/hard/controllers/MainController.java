package com.hard.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {
    @GetMapping(value = "")
    public String main() {
        return "main/main";
    }

    @GetMapping(value = "/admin")
    public String admin() {
        return "main/admin";
    }

    @GetMapping(value = "/db")
    public String db() {
        return "main/db";
    }
}
