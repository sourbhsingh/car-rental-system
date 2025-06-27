package com.carrentalsystem.app.dto;

import com.carrentalsystem.app.helper.BookingStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingResponseDTO {
    private Integer id;
    private String userName;
    private String carName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double totalPrice;
    private BookingStatus status;
}
