package com.carrentalsystem.app.controller;

import com.carrentalsystem.app.dto.UserDTO;
import com.carrentalsystem.app.dto.UserLoginDTO;
import com.carrentalsystem.app.entity.User;
import com.carrentalsystem.app.service.UserService;
import com.carrentalsystem.app.util.UserMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    // GET request for registration form
    // Base URL -> redirect to login page
    @GetMapping({"/","/login"})
    public String showLoginPage(Model model) {
        model.addAttribute("userLoginDTO", new UserLoginDTO());
        return "login"; // loads login.html from templates/
    }


    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new UserDTO());
        return "register";
    }

    // POST request to process the form
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") UserDTO userDTO,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        User user = UserMapper.toEntity(userDTO);
        userService.saveUser(user);
        return "redirect:/login";
    }
}
