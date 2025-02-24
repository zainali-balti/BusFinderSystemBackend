package com.example.Bus.Finder.System.entity;

import com.example.Bus.Finder.System.dto.BusRouteDto;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "bus_route")
public class BusRoute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    @ManyToOne
    @JoinColumn(name = "destination_stop_id", nullable = false)
    private BusStop stop;

    @ManyToOne
    @JoinColumn(name = "source_start_id", nullable = false)
    private BusStop start;

    @Column(nullable = false)
    private int sequence;

    public BusRouteDto getAllRoute(){
        BusRouteDto routeDto = new BusRouteDto();
        routeDto.setBusRouteId(id);
        routeDto.setRouteId(route.getId());
        routeDto.setSourceStopId(start.getStopId());
        routeDto.setDestinationStopId(stop.getStopId());
        routeDto.setSequence(sequence);
        return routeDto;
    }
}
