package com.example.project_version_init;

import org.springframework.stereotype.Controller;

@Controller
public class HomeController {
    public String home() {
        return "index";
    }
}
