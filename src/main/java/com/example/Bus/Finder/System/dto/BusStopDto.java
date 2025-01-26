package com.example.Bus.Finder.System.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class BusStopDto {
    private Long stopId;
    private String name;
}
