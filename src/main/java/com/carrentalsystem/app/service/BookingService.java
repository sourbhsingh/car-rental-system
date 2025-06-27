package com.carrentalsystem.app.service;

import com.carrentalsystem.app.entity.Booking;
import com.carrentalsystem.app.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    public void saveBooking(Booking booking) {
        bookingRepository.save(booking);
    }
    public Booking getBookingById(Integer id) {
        return bookingRepository.findById(id).orElse(null);
    }

    public List<Booking> getAllBookings(Integer userId) {
        return bookingRepository.findAllByUser_Id(userId);
    }
}
