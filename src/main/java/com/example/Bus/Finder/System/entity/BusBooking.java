package com.example.Bus.Finder.System.entity;

import com.example.Bus.Finder.System.enums.Status;
import jakarta.persistence.*;
import lombok.Data;

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
    @JoinColumn(name = "bus_id", nullable = false)
    private Bus bus;

    @ManyToOne
    @JoinColumn(name = "source_stop_id", nullable = false)
    private BusStop sourceStop;

    @ManyToOne
    @JoinColumn(name = "destination_stop_id", nullable = false)
    private BusStop destinationStop;

    @Column(nullable = false)
    private LocalDateTime bookingTime = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

}
