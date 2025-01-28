package com.example.Bus.Finder.System.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FareDto {
    private Long fareId;
    private Long routeId;
    private Long sourceStopId;
    private Long destinationStopId;
    private Long busId;
    private BigDecimal fare;
}
