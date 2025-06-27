package com.carrentalsystem.app.service;

import com.carrentalsystem.app.dto.CarDTO;
import com.carrentalsystem.app.dto.CarUploadDTO;

import java.util.List;

public interface CarService {
    List<CarDTO> getAllAvailableCars();
    List<CarDTO> getAllCars();
    CarDTO getCarById(Integer id);
    CarDTO addCar(CarUploadDTO carUploadDTO);
    CarDTO updateCar(Integer id, CarUploadDTO carUploadDTO);
    void deleteCar(Integer id);
}
