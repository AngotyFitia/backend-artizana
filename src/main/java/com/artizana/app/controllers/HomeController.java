package com.artizana.app.controllers;

// public class HomeController {

// }
// package com.artizana.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        // Renvoie le nom du JSP sans extension
        return "index"; // correspond à /WEB-INF/index.jsp
    }
}
