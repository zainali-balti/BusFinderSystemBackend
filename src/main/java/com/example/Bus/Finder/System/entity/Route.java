package com.example.Bus.Finder.System.entity;

import com.example.Bus.Finder.System.dto.RouteDto;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "routes")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long routeId;

    @ManyToOne
    @JoinColumn(name = "bus_id", nullable = false)
    private Bus bus;

    @ManyToOne
    @JoinColumn(name = "stop_id", nullable = false)
    private BusStop stop;

    @Column(nullable = false)
    private int stopSequence;
    public RouteDto getAllRoute(){
        RouteDto routeDto = new RouteDto();
        routeDto.setRouteId(routeId);
        routeDto.setBusName(bus.getBusName());
        routeDto.setStopName(stop.getName());
        routeDto.setStopSequence(stopSequence);
        return routeDto;
    }
}
