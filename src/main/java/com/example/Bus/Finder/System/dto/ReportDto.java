package com.example.Bus.Finder.System.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReportDto {
        private Long id;
        private Long customerId;
        private String customerName;
        private Long vehicleId;
        private String vehicleName;
        private LocalDate bookingDate;
        private LocalDate endDate;
        private Double price;
        private Double distance;
}
