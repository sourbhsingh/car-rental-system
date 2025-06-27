package com.carrentalsystem.app.controller;

import com.carrentalsystem.app.dto.BookingResponseDTO;
import com.carrentalsystem.app.dto.CarDTO;
//imporCarResponseDTOt com.carrentalsystem.app.dto.;
import com.carrentalsystem.app.dto.CarUploadDTO;
import com.carrentalsystem.app.dto.UserDTO;
import com.carrentalsystem.app.helper.FuelType;
import com.carrentalsystem.app.service.BookingService;
import com.carrentalsystem.app.service.CarService;
import com.carrentalsystem.app.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final CarService carService;

    private final UserService userService;

    private final BookingService bookingService;

    @GetMapping("/dashboard")
    public String adminDashboard() {
        return "admin/dashboard";
    }

    @GetMapping("/addcar")
    public String showAddCarForm(Model model) {
        model.addAttribute("car", new CarUploadDTO());
        model.addAttribute("fuelTypes", FuelType.values());
        return "admin/addcar";
    }

    @PostMapping("/addcar")
    public String addCar(@Valid @ModelAttribute("car") CarUploadDTO carDTO,
                         BindingResult result,
                         @RequestParam("imageFiles") List<MultipartFile> imageFiles,
                         Model model) {
        if (result.hasErrors()) {
            model.addAttribute("fuelTypes", FuelType.values());
            return "admin/addcar";
        }

        carService.addCarWithImages(carDTO, imageFiles);
        return "redirect:/admin/cars";
    }

    @GetMapping("/cars")
    public String listCars(Model model) {
        List<CarDTO> cars = carService.getAllCars();
        model.addAttribute("cars", cars);
        return "admin/cars";
    }

    @GetMapping("/deletecar/{id}")
    public String deleteCar(@PathVariable Integer id) {
        carService.deleteCar(id);
        return "redirect:/admin/cars";
    }

    @GetMapping("/users")
    public String viewUsers(Model model) {
        List<UserDTO> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/users";
    }

    @GetMapping("/allbookings")
    public String viewAllBookings(Model model) {
        List<BookingResponseDTO> bookings = bookingService.getAllBookings();
        model.addAttribute("bookings", bookings);
        return "admin/allbookings";
    }
}
