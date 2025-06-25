package com.carrentalsystem.app.dto;
import com.carrentalsystem.app.helper.BookingStatus;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BookingDTO {
    private Integer id;
    private Integer carId;
    private Integer userId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double totalPrice;
    private BookingStatus status;
}
