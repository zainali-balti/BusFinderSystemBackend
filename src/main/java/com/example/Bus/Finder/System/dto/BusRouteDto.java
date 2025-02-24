package com.example.Bus.Finder.System.dto;

import lombok.Data;

@Data
public class BusRouteDto {
    private Long busRouteId;
    private Long routeId;
    private Long sourceStopId;
    private Long destinationStopId;
    private int sequence;
}
