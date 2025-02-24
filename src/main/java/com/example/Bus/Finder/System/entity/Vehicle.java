package com.example.Bus.Finder.System.entity;

import com.example.Bus.Finder.System.dto.VehicleDto;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Base64;

@Data
@Entity
@Table(name = "vehicle")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String vehicleName;
    private String color;
    private String engineNo;
    private String modelNo;
    private Long totalSeats;
    private String description;
    private Double price;
    private Double distance;
    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] img;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    @OnDelete(action= OnDeleteAction.CASCADE)
    private User user;
    @Column(nullable = false)
    private boolean isBooked = false;

    public VehicleDto getAddVehicleDto(){
    VehicleDto addVehicleDto = new VehicleDto();
    addVehicleDto.setId(id);
    addVehicleDto.setColor(color);
    addVehicleDto.setVehicleName(vehicleName);
    if (img != null) {
        String base64Image = Base64.getEncoder().encodeToString(img);
        addVehicleDto.setReturnedImg(base64Image);
    }
    addVehicleDto.setDistance(distance);
    addVehicleDto.setPrice(price);
    addVehicleDto.setDescription(description);
    addVehicleDto.setEngineNo(engineNo);
    addVehicleDto.setOwnerId(user.getId());
    addVehicleDto.setOwnerName(user.getFirstName());
    addVehicleDto.setTotalSeats(totalSeats);
    addVehicleDto.setModelNo(modelNo);
    addVehicleDto.setBooked(isBooked);
    return addVehicleDto;
}
}


