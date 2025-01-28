package com.example.Bus.Finder.System.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SearchForBusDto {
    private Long id;
    private Long userId;
    private Long sourceStopId;
    private Long destinationStopId;
}
