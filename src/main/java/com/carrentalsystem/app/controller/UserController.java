package com.carrentalsystem.app.controller;

import com.carrentalsystem.app.dto.*;
import com.carrentalsystem.app.entity.User;
import com.carrentalsystem.app.helper.BookingStatus;
import com.carrentalsystem.app.helper.CarType;
import com.carrentalsystem.app.service.BookingService;
import com.carrentalsystem.app.service.CarService;
import com.carrentalsystem.app.service.PaymentService;
import com.carrentalsystem.app.service.UserService;
import com.carrentalsystem.app.util.FineCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final BookingService bookingService;
    private final CarService carService;
    private final PaymentService paymentService;

    @GetMapping("/user/dashboard")
    public String showUserDashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        // Get logged-in user's email from security context
        String email = userDetails.getUsername();
        UserDTO user = userService.getUserByEmail(email);

        // Get username
        model.addAttribute("userName", user.getName());

        // Get upcoming bookings
        List<BookingResponseDTO> upcomingBookings = bookingService.getUpcomingBookingsByUser(user.getId());

        System.out.println(upcomingBookings.toString()+ "  Upcoming Bookings");
        model.addAttribute("upcomingBookings", upcomingBookings);

        // Get car listings
        model.addAttribute("availableCars", carService.getAllAvailableCars());
        model.addAttribute("suvCars", carService.getCarByType(CarType.SUV));
        model.addAttribute("hatchbackCars", carService.getCarByType(CarType.HATCHBACK));
        model.addAttribute("sedanCars", carService.getCarByType(CarType.SEDAN));

        return "user/dashboard";
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
