package com.carrentalsystem.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String redirectToLogin() {
        return "redirect:/auth/login";
    }

    @GetMapping("/register")
    public String redirectToRegister() {
        return "redirect:/auth/register";
    }

    @GetMapping("/admin/login")
    public String redirectToAdminLogin() {
        return "redirect:/auth/admin/login";
    }
    // Optional: custom 403 access denied page
    @GetMapping("/403")
    public String accessDenied() {
        return "error/403";
    }

    // Optional: default error page
    @GetMapping("/error")
    public String error() {
        return "error/error";
    }
}
