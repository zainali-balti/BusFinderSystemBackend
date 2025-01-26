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
    @JoinColumn(name = "stop_id", nullable = false)
    private BusStop stop;

    @ManyToOne
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    @Column(nullable = false)
    private LocalTime arrivalTime;

    @Column(nullable = false)
    private LocalTime departureTime;

    @Column(nullable = false)
    private int sequence;

    public BusScheduleDto getAllBusSchedule(){
        BusScheduleDto busScheduleDto = new BusScheduleDto();
        busScheduleDto.setScheduleId(scheduleId);
        busScheduleDto.setBusName(bus.getBusName());
        busScheduleDto.setStopName(stop.getName());
        busScheduleDto.setRouteSequence(route.getStopSequence());
        busScheduleDto.setSequence(sequence);
        return busScheduleDto;
    }
}
