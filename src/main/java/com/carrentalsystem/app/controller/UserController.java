package com.carrentalsystem.app.controller;

import com.carrentalsystem.app.dto.BookingRequestDTO;
import com.carrentalsystem.app.dto.BookingResponseDTO;
import com.carrentalsystem.app.dto.PaymentRequestDTO;
import com.carrentalsystem.app.dto.UserDTO;
import com.carrentalsystem.app.entity.Booking;
import com.carrentalsystem.app.entity.Car;
import com.carrentalsystem.app.helper.BookingStatus;
import com.carrentalsystem.app.helper.CarType;
import com.carrentalsystem.app.service.BookingService;
import com.carrentalsystem.app.service.CarService;
import com.carrentalsystem.app.service.PaymentService;
import com.carrentalsystem.app.service.UserService;
import com.carrentalsystem.app.util.FineCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
   
    private final UserService userService;
   
    private final CarService carService;
   
    private final BookingService bookingService;
   
    private final PaymentService paymentService;

    @GetMapping("/dashboard")
    public String userDashboard(Model model) {
        UserDTO user = userService.getUserById(1);
        model.addAttribute("userName", user.getName());
        model.addAttribute("availableCars", carService.getAllAvailableCars());
        model.addAttribute("bookings", bookingService.getBookingsByUserId(user.getId()));// CURRENT USER SHOULD GET FROM SESSION
        model.addAttribute("suvCars", carService.getCarByType(CarType.SUV));
        model.addAttribute("hatchbackCars", carService.getCarByType(CarType.HATCHBACK));
        model.addAttribute("sedanCars", carService.getCarByType(CarType.SEDAN));
        return "user/dashboard";
    }



    @GetMapping("/book/{carId}")
    public String showBookingForm(@PathVariable Integer carId, Model model) {
        model.addAttribute("car", carService.getCarById(carId));
        model.addAttribute("bookingRequest", new BookingRequestDTO());
        return "user/book";
    }

    @PostMapping("/book")
    public String bookCar(@ModelAttribute BookingRequestDTO dto, Model model) {
        bookingService.createBooking(dto);
        return "redirect:/user/mybookings";
    }

    @GetMapping("/mybookings")
    public String showMyBookings(@RequestParam("userId") Integer userId, Model model) {
        model.addAttribute("bookings", bookingService.getBookingsByUserId(userId));
        return "user/mybookings";
    }
    @GetMapping("/return/{bookingId}")
    public String returnCarForm(@PathVariable Integer bookingId, Model model) {

        BookingResponseDTO bookingDto = bookingService.getBookingById(bookingId);
        double fine = FineCalculator.calculateFine(bookingDto.getEndTime(), LocalDateTime.now());

        model.addAttribute("booking", bookingDto);
        model.addAttribute("fine", fine);
        return "user/returncar";
    }


    @PostMapping("/return/{bookingId}")
    public String returnCar(@PathVariable Integer bookingId) {
        bookingService.updateBookingStatus(bookingId, BookingStatus.COMPLETED.name());
        return "redirect:/user/mybookings";
    }

    @GetMapping("/payment/{bookingId}")
    public String paymentForm(@PathVariable Integer bookingId, Model model) {
        model.addAttribute("bookingId", bookingId);
        model.addAttribute("paymentRequest", new PaymentRequestDTO());
        return "user/payment";
    }

    @PostMapping("/payment")
    public String processPayment(@ModelAttribute PaymentRequestDTO dto) {
        paymentService.makePayment(dto);
        return "redirect:/user/simulate";
    }

    @GetMapping("/simulate")
    public String paymentSuccess() {
        return "user/simulate-payment";
    }
}
