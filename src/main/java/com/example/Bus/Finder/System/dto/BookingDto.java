package com.example.Bus.Finder.System.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BookingDto {
    private Long id;
    private Long userId;
    private Long vehicleId;
    private LocalDateTime bookingDate;
    private Double totalPrice;
    private Double distance;
    private LocalDateTime endDate;
}
