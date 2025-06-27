package com.carrentalsystem.app.service.impl;

import com.carrentalsystem.app.dto.BookingRequestDTO;
import com.carrentalsystem.app.dto.BookingResponseDTO;
import com.carrentalsystem.app.entity.Booking;
import com.carrentalsystem.app.entity.Car;
import com.carrentalsystem.app.entity.User;
import com.carrentalsystem.app.exception.ResourceNotFoundException;
import com.carrentalsystem.app.helper.BookingStatus;

import com.carrentalsystem.app.repository.BookingRepository;
import com.carrentalsystem.app.repository.CarRepository;
import com.carrentalsystem.app.repository.UserRepository;
import com.carrentalsystem.app.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingServiceImpl implements BookingService {
    @Autowired
    private final BookingRepository bookingRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final CarRepository carRepository;

    @Override
    public BookingResponseDTO createBooking(BookingRequestDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + dto.getUserId()));

        Car car = carRepository.findById(dto.getCarId())
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with ID: " + dto.getCarId()));

        if (!car.isAvailable()) {
            throw new IllegalStateException("Car is not available for booking");
        }

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setCar(car);
        booking.setStartTime(dto.getStartTime());
        booking.setEndTime(dto.getEndTime());
        booking.setStatus(BookingStatus.CONFIRMED);

        long hours = Duration.between(dto.getStartTime(), dto.getEndTime()).toHours();
        double total = hours * car.getPricePerHour();

        booking.setTotalPrice(total);

        car.setAvailable(false); // Block car until return
        Booking saved = bookingRepository.save(booking);
        return mapToDTO(saved);
    }

    @Override
    public List<BookingResponseDTO> getBookingsByUserId(Integer userId) {
        return bookingRepository.findByUser_Id(userId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingResponseDTO> getAllBookings() {
        return bookingRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BookingResponseDTO getBookingById(Integer bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + bookingId));
        return mapToDTO(booking);
    }

    @Override
    public BookingResponseDTO updateBookingStatus(Integer bookingId, String newStatus) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + bookingId));

        try {
            BookingStatus status = BookingStatus.valueOf(newStatus.toUpperCase());
            booking.setStatus(status);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid booking status: " + newStatus);
        }

        Booking updated = bookingRepository.save(booking);
        return mapToDTO(updated);
    }

    @Override
    public void deleteBooking(Integer bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + bookingId));
        bookingRepository.delete(booking);
    }

    private BookingResponseDTO mapToDTO(Booking booking) {
        BookingResponseDTO dto = new BookingResponseDTO();
        dto.setId(booking.getId());
        dto.setStartTime(booking.getStartTime());
        dto.setEndTime(booking.getEndTime());
        dto.setTotalPrice(booking.getTotalPrice());
        dto.setStatus(booking.getStatus());
        dto.setCarName(booking.getCar().getBrand() + " " + booking.getCar().getModel());
        dto.setUserName(booking.getUser().getName());
        return dto;
    }
}
