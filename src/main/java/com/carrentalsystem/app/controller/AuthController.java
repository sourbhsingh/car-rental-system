package com.carrentalsystem.app.controller;

import com.carrentalsystem.app.dto.UserLoginDTO;
import com.carrentalsystem.app.entity.User;
import com.carrentalsystem.app.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private final UserService userService;

    // GET - Registration page
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // POST - Register user
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user,
                               BindingResult result,
                               Model model) {
        if (result.hasErrors()) {
            return "register";
        }

        userService.registerUser(user);
        model.addAttribute("successMessage", "Registration successful! Please log in.");
        return "redirect:/auth/login";
    }

    // GET - Login page
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("userLoginDTO", new UserLoginDTO());
        return "login";
    }
    @GetMapping("/admin/login")
    public String showAdminLoginForm(Model model) {
        model.addAttribute("userLoginDTO", new UserLoginDTO());
        return "admin/login";
    }
    @PostMapping("/admin/login")
    public String loginAdmin(@ModelAttribute("userLoginDTO") UserLoginDTO loginDTO,
                            Model model) {

        boolean isAuthenticated = userService.authenticateUser(loginDTO);

        if (isAuthenticated) {
            // Normally, session logic goes here (Spring Security or manual)
            return "redirect:/admin/dashboard";
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "admin/login";
        }
    }
    // POST - Login process
    @PostMapping("/login")
    public String loginUser(@ModelAttribute("userLoginDTO") UserLoginDTO loginDTO,
                            Model model) {

        boolean isAuthenticated = userService.authenticateUser(loginDTO);

        if (isAuthenticated) {
            // Normally, session logic goes here (Spring Security or manual)
            return "redirect:/user/dashboard";
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }
    }

    // GET - Logout logic (optional placeholder)
    @GetMapping("/logout")
    public String logoutUser() {
        // Custom logout logic or clear session (if you maintain one manually)
        return "redirect:/auth/login";
    }
}
