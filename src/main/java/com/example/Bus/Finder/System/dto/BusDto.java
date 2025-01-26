package com.example.Bus.Finder.System.dto;

import com.example.Bus.Finder.System.enums.Status;
import lombok.Data;

@Data
public class BusDto {
    private Long busId;
    private String busName;
    private String busNumber;
    private int capacity;
    private Status status = Status.ACTIVE;
}
