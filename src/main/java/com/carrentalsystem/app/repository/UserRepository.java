package com.carrentalsystem.app.repository;

import com.carrentalsystem.app.enitity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByName(String name);
    // Additional query methods can be defined here if needed

}
