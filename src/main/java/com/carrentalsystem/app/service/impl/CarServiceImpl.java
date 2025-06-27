package com.carrentalsystem.app.service.impl;

import com.carrentalsystem.app.dto.CarDTO;
import com.carrentalsystem.app.dto.CarUploadDTO;
import com.carrentalsystem.app.entity.Car;
import com.carrentalsystem.app.entity.CarImage;
import com.carrentalsystem.app.exception.ResourceNotFoundException;
import com.carrentalsystem.app.repository.CarImageRepository;
import com.carrentalsystem.app.repository.CarRepository;
import com.carrentalsystem.app.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CarImageRepository carImageRepository;

    @Value("${car.upload.dir}")
    private String uploadDir;

    @Override
    public List<CarDTO> getAllAvailableCars() {
        return carRepository.findAll()
                .stream()
                .filter(Car::isAvailable)
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CarDTO> getAllCars() {
        return carRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CarDTO getCarById(Integer id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with ID: " + id));
        return mapToDTO(car);
    }

    @Override
    public CarDTO addCar(CarUploadDTO carUploadDTO) {
        Car car = new Car();
        mapUploadDTOToEntity(carUploadDTO, car);
        car.setAvailable(true);
        Car savedCar = carRepository.save(car);
        saveImages(carUploadDTO.getImages(), savedCar);
        return mapToDTO(savedCar);
    }

    @Override
    public CarDTO updateCar(Integer id, CarUploadDTO carUploadDTO) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found for update with ID: " + id));
        mapUploadDTOToEntity(carUploadDTO, car);
        Car updated = carRepository.save(car);
        return mapToDTO(updated);
    }

    @Override
    public void deleteCar(Integer id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found for delete with ID: " + id));
        carRepository.delete(car);
    }

    // Helper Methods
    private void mapUploadDTOToEntity(CarUploadDTO dto, Car car) {
        car.setBrand(dto.getBrand());
        car.setModel(dto.getModel());
        car.setColor(dto.getColor());
        car.setType(dto.getType());
        car.setFuelType(dto.getFuelType());
        car.setDescription(dto.getDescription());
        car.setPricePerHour(dto.getPricePerHour());
    }

    private void saveImages(List<MultipartFile> files, Car car) {
        List<CarImage> imageList = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                File dest = new File(uploadDir + File.separator + fileName);
                dest.getParentFile().mkdirs();
                file.transferTo(dest);

                CarImage image = new CarImage();
                image.setImageUrl("/uploads/" + fileName);
                image.setCar(car);
                imageList.add(image);
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload car image: " + file.getOriginalFilename(), e);
            }
        }
        carImageRepository.saveAll(imageList);
    }

    private CarDTO mapToDTO(Car car) {
        CarDTO dto = new CarDTO();
        dto.setId(car.getId());
        dto.setBrand(car.getBrand());
        dto.setModel(car.getModel());
        dto.setColor(car.getColor());
        dto.setType(car.getType());
        dto.setFuelType(car.getFuelType());
        dto.setAvailable(car.isAvailable());
        dto.setDescription(car.getDescription());
        dto.setPricePerHour(car.getPricePerHour());

        if (car.getImages() != null) {
            List<String> urls = car.getImages().stream()
                    .map(CarImage::getImageUrl)
                    .collect(Collectors.toList());
            dto.setImageUrls(urls);
        }
        return dto;
    }
}
