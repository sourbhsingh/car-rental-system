package com.carrentalsystem.app.repository;

import com.carrentalsystem.app.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Integer> {
    // Additional query methods can be defined here if needed

}
