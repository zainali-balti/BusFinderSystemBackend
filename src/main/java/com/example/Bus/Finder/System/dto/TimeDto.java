package com.example.Bus.Finder.System.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TimeDto {
    private LocalDate startDate;
    private LocalDate endDate;
}
