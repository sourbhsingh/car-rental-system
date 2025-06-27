package com.carrentalsystem.app.repository;

import com.carrentalsystem.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByName(String name);
    // Additional query methods can be defined here if needed

}
