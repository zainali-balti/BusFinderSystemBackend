package com.example.Bus.Finder.System.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class VehicleDto {
    private Long id;
    private String vehicleName;
    private String color;
    private String engineNo;
    private String modelNo;
    private Long totalSeats;
    private String description;
    private Double price;
    private Double distance;
    private MultipartFile img;
    private String returnedImg;
    private Long ownerId;
    private String ownerName;
    private boolean isBooked;
}
