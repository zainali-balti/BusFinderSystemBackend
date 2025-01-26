package com.example.Bus.Finder.System.entity;



import com.example.Bus.Finder.System.dto.BusDto;
import com.example.Bus.Finder.System.enums.Status;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "buses")
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long busId;

    @Column(nullable = false)
    private String busName;


    @Column(unique = true, nullable = false)
    private String busNumber;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    public BusDto getAll(){
        BusDto busDto = new BusDto();
        busDto.setBusId(busId);
        busDto.setBusNumber(busNumber);
        busDto.setBusName(busName);
        busDto.setCapacity(capacity);
        busDto.setStatus(status);
        return busDto;
    }



}
