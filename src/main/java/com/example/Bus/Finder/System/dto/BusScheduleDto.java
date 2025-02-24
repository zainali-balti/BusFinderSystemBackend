package com.example.Bus.Finder.System.dto;

import lombok.Data;

import java.time.LocalTime;

@Data
public class BusScheduleDto {
    private Long scheduleId;
    private Long busId;
    private Long busRouteId;
    private LocalTime arrivalTime;
    private LocalTime departureTime;
}
