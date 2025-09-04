package com.carrentalsystem.app.restcontroller;

import com.carrentalsystem.app.dto.UserDTO;
import com.carrentalsystem.app.dto.UserLoginDTO;
import com.carrentalsystem.app.entity.User;
import com.carrentalsystem.app.exception.ResourceNotFoundException;
import com.carrentalsystem.app.helper.Role;
import com.carrentalsystem.app.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthRestController {
    @Autowired
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser( @RequestBody User user) {
        if (userService.isEmailExists(user.getEmail())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Email already exists!");
        }

        user.setRole(Role.USER);
        UserDTO savedUser = userService.registerUser(user); // Handles password encoding
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser( @RequestBody UserLoginDTO user) {
        UserDTO loggedIn = userService.authenticateUser(user);
        if(loggedIn==null) throw new RuntimeException("User Not found!");
        else
            return ResponseEntity.status(HttpStatus.OK).body(loggedIn);

    }


    @PostMapping("/admin/login")
    public ResponseEntity<UserDTO> loginAdmin( @RequestBody UserLoginDTO user) {
        UserDTO loggedIn = userService.authenticateAdmin(user);
        if(loggedIn==null) throw new ResourceNotFoundException("User Not found!");
        else
            return ResponseEntity.status(HttpStatus.OK).body(loggedIn);

    }






}
