package com.example.Bus.Finder.System.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class FareDto {
    private Long fareId;
    private Long busRouteId;
    private Long busId;
    private BigDecimal fare;
}
