package com.carrentalsystem.app.controller;

import com.carrentalsystem.app.dto.BookingRequestDTO;
import com.carrentalsystem.app.dto.BookingResponseDTO;
import com.carrentalsystem.app.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;
    // ✅ ADMIN: View all bookings
    @GetMapping
    public String getAllBookings(Model model) {
        List<BookingResponseDTO> bookings = bookingService.getAllBookings();
        model.addAttribute("bookings", bookings);
        return "admin/allbookings";
    }
    // ✅ USER: Book a car
    @PostMapping("/create")
    public String createBooking(@ModelAttribute @Valid BookingRequestDTO bookingRequestDTO,
                                RedirectAttributes redirectAttributes) {
        try {
            bookingService.createBooking(bookingRequestDTO);
            redirectAttributes.addFlashAttribute("success", "Booking successful!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to book: " + e.getMessage());
        }

        return "redirect:/user/bookings";
    }

    // ✅ USER: View my bookings
    @GetMapping("/user/{userId}")
    public String getUserBookings(@PathVariable("userId") Integer userId, Model model) {
        List<BookingResponseDTO> bookings = bookingService.getBookingsByUserId(userId);
        model.addAttribute("bookings", bookings);
        return "user/mybookings";
    }

    // ✅ USER: Cancel booking
    @GetMapping("/cancel/{id}")
    public String cancelBooking(@PathVariable("id") Integer bookingId,
                                RedirectAttributes redirectAttributes) {
        try {
            bookingService.cancelBooking(bookingId);
            redirectAttributes.addFlashAttribute("success", "Booking cancelled successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to cancel booking: " + e.getMessage());
        }

        return "redirect:/user/bookings";
    }



    // ✅ ADMIN: View booking details
    @GetMapping("/admin/view/{id}")
    public String viewBookingDetails(@PathVariable("id") Integer id, Model model) {
        BookingResponseDTO booking = bookingService.getBookingById(id);
        model.addAttribute("booking", booking);
        return "admin/booking-details";
    }
}
