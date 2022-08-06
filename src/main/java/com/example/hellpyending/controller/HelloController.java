package com.example.hellpyending.controller;

import org.springframework.stereotype.Controller;

@Controller
public class HelloController {

    public String home() {
        return "index";
    }
}
