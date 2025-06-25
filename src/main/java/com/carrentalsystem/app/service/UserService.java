package com.carrentalsystem.app.service;

import com.carrentalsystem.app.enitity.User;
import com.carrentalsystem.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public void saveUser(User user){
        userRepository.save(user);
    }
    public User findByName(String name){
        return userRepository.findByName(name);
    }
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

}
