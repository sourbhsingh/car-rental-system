package com.carrentalsystem.app.service;

import com.carrentalsystem.app.dto.UserDTO;
import com.carrentalsystem.app.dto.UserLoginDTO;
import com.carrentalsystem.app.entity.User;

import java.util.List;

public interface UserService {
    UserDTO getUserById(Integer id);
    UserDTO getUserByEmail(String email);
    List<UserDTO> getAllUsers();
    UserDTO registerUser(User user);
    boolean authenticateUser(UserLoginDTO loginDTO);
    void deleteUser(Integer userId);
}
