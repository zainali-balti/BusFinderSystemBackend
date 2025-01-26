package com.example.Bus.Finder.System.dto;

import com.example.Bus.Finder.System.entity.Bus;
import com.example.Bus.Finder.System.entity.BusStop;
import lombok.Data;

@Data
public class RouteDto {
    private Long routeId;
    private String busName;
    private String stopName;
    private int stopSequence;
}
