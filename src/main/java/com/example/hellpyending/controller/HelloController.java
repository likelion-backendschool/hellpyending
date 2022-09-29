package com.example.hellpyending.controller;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    public String home() {
        return "index";
    }

    public String home_new() {
        return "index_new";
    }

}
