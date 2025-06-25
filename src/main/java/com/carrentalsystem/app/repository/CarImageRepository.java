package com.carrentalsystem.app.repository;

import com.carrentalsystem.app.enitity.CarImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarImageRepository extends JpaRepository<CarImage,Integer> {
}
