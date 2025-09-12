package com.artizana.app.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.ui.Model;
import java.util.Date;
import org.springframework.stereotype.Controller;

@CrossOrigin(origins="*", allowedHeaders="*")
@Controller
@RequestMapping("api/project")
public class BasicController {
  @GetMapping("/welcome")
  public String welcomePage(Model model) {
    model.addAttribute("date", new Date().toString());
    return "index";
  }
}
