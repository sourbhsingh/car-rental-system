package com.carrentalsystem.app.dto;


import com.carrentalsystem.app.helper.FuelType;
import lombok.Data;

@Data
public class CarDTO {
    private Integer id;
    private String brand;
    private String model;
    private String color;
    private String type;
    private FuelType fuelType;
    private boolean isAvailable;
    private String description;
    private Double pricePerHour;
}
