package com.carrentalsystem.app.controller;

import com.carrentalsystem.app.dto.CarDTO;
import com.carrentalsystem.app.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/cars")
public class UserCarController {
    private final CarService carService;

    @GetMapping
    public String listAvailableCars(Model model) {
        model.addAttribute("cars", carService.getAllAvailableCars());
        return "user/cars";
    }
    @GetMapping("/all")
    public String listAllCars(Model model) {
        List<CarDTO> allCars = carService.getAllCars();
        model.addAttribute("cars", allCars);
        return "user/allcars";
    }
    @GetMapping("/view/{id}")
    public String viewCarDetails(@PathVariable Integer id, Model model) {
        CarDTO car = carService.getCarById(id);
        model.addAttribute("car", car);
        return "user/view";
    }
}
