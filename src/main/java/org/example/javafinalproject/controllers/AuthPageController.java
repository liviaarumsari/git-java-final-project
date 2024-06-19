package org.example.javafinalproject.controllers;

import org.example.javafinalproject.payloads.request.LoginRequest;
import org.example.javafinalproject.payloads.request.SignupRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class AuthPageController {
    private final RestTemplate restTemplate;

    public AuthPageController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("signupRequest", new SignupRequest());
        return "signup";
    }

    @PostMapping("/signup")
    public String signupSubmit(@ModelAttribute SignupRequest signupRequest) {
        restTemplate.postForObject("http://localhost:8080/api/auth/signup", signupRequest, String.class);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String loginSubmit(@ModelAttribute LoginRequest loginRequest, Model model) {
        String response = restTemplate.postForObject("http://localhost:8080/api/auth/login", loginRequest, String.class);
        if (response.contains("JWT")) {
            return "redirect:/";
        } else {
            model.addAttribute("loginError", true);
            return "login";
        }
    }
}
