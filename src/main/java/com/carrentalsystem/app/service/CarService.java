// CarService.java
package com.carrentalsystem.app.service;

import com.carrentalsystem.app.entity.Car;
import com.carrentalsystem.app.entity.CarImage;
import com.carrentalsystem.app.repository.CarImageRepository;
import com.carrentalsystem.app.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private CarImageRepository carImageRepository;

    public void saveCar(Car car) {
        carRepository.save(car);
    }

    public void saveCarImage(Integer carId, String imagePath) {
        Optional<Car> carOpt = carRepository.findById(carId);
        if (carOpt.isPresent()) {
            CarImage carImage = new CarImage();
            carImage.setCar(carOpt.get());
            carImage.setImageUrl(imagePath);
            carImageRepository.save(carImage);
        } else {
            throw new IllegalArgumentException("Car not found with id: " + carId);
        }
    }

        public Car findCarById(Integer id) {
        return carRepository.findById(id).orElse(null);
                 }
        public void deleteCar(Integer id) {
        carRepository.deleteById(id);}


}