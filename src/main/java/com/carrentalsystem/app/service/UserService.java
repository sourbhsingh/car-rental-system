package com.carrentalsystem.app.service;

import com.carrentalsystem.app.dto.UserDTO;
import com.carrentalsystem.app.dto.UserLoginDTO;
import com.carrentalsystem.app.entity.User;
import jakarta.validation.constraints.Email;

import java.util.List;

public interface UserService {
    UserDTO getUserById(Integer id);
    UserDTO getUserByEmail(String email);
    List<UserDTO> getAllUsers();
    UserDTO registerUser(User user);
    UserDTO authenticateAdmin(UserLoginDTO loginDTO);
    UserDTO authenticateUser(UserLoginDTO loginDTO);
    void deleteUser(Integer userId);
    boolean isEmailExists(@Email(message = "Invalid email format") String email);
}
