package com.example.Bus.Finder.System.entity;

import com.example.Bus.Finder.System.dto.BusStopDto;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "bus_stops")
public class BusStop{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stopId;

    @Column(nullable = false)
    private String name;
    public BusStopDto getAll(){
        BusStopDto busStopDto = new BusStopDto();
        busStopDto.setStopId(stopId);
        busStopDto.setName(name);
        return busStopDto;
    }

}
