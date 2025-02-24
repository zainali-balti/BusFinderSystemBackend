package com.example.Bus.Finder.System.dto;

import com.example.Bus.Finder.System.entity.Bus;
import com.example.Bus.Finder.System.entity.BusStop;
import com.example.Bus.Finder.System.entity.Fare;
import com.example.Bus.Finder.System.entity.User;
import com.example.Bus.Finder.System.enums.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class BusBookingDto {
    private Long bookingId;
    private Long userId;
    private Long fareId;
    private LocalDateTime bookingTime = LocalDateTime.now();
    private Status status = Status.CONFIRMED;
    private BigDecimal totalFare;
}
