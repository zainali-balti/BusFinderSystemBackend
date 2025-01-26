package com.example.Bus.Finder.System.dto;

import com.example.Bus.Finder.System.entity.Bus;
import com.example.Bus.Finder.System.entity.BusStop;
import com.example.Bus.Finder.System.entity.Route;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalTime;

@Data
public class BusScheduleDto {
    private Long scheduleId;
    private String busName;
    private String stopName;
    private int routeSequence;
    private LocalTime arrivalTime;
    private LocalTime departureTime;
    private int sequence;
}
