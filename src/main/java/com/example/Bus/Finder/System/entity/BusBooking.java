package com.example.Bus.Finder.System.entity;

import com.example.Bus.Finder.System.enums.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "bus_bookings")
public class BusBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "fare_id", nullable = false)
    private Fare fare;

    @Column(nullable = false)
    private LocalDateTime bookingTime = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    @Column(nullable = false)
    private BigDecimal totalFare;
}
