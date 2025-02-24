package com.example.Bus.Finder.System.dto;

import com.example.Bus.Finder.System.enums.Status;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class BusDto {
    private Long busId;
    private String busName;
    private String busNumber;
    private int capacity;
    private byte[] img;
    private Long userId;
    private Status status;
}
