package com.example.Bus.Finder.System.entity;

import com.example.Bus.Finder.System.dto.BusScheduleDto;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;

@Data
@Entity
@Table(name = "bus_schedules")
public class BusSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;
    @ManyToOne
    @JoinColumn(name = "bus_id", nullable = false)
    private Bus bus;
    @ManyToOne
    @JoinColumn(name = "bus_route_id", nullable = false)
    private BusRoute route;

    @Column(nullable = false)
    private LocalTime arrivalTime;

    @Column(nullable = false)
    private LocalTime departureTime;
}
