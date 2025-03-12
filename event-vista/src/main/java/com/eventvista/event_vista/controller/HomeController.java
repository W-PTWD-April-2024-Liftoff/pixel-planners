package com.eventvista.event_vista.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Corresponds to http://localhost:8080
    @GetMapping("/")
    public String redirectToHomePage() {
        return "redirect:/welcome";
    }

    @GetMapping("/welcome")
    public String renderHomePage(Model model, HttpSession session) {
        model.addAttribute("loggedIn", session.getAttribute("user") != null);
        return "index";
    }
}
