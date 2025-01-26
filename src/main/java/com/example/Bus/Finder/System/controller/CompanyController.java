package com.example.Bus.Finder.System.controller;

import com.example.Bus.Finder.System.dto.VehicleDto;
import com.example.Bus.Finder.System.service.Company.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company")
public class CompanyController {

    @Autowired
    private VehicleService vehicleService;

    @PostMapping("/add/{userId}")
    public ResponseEntity<?> addVehicle(@PathVariable Long userId, @ModelAttribute VehicleDto vehicleDto){
        boolean success = vehicleService.addVehicle(userId,vehicleDto);
        if (success){
            return ResponseEntity.status(HttpStatus.OK).build();
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @GetMapping("/all/{userId}")
    public ResponseEntity<?> FindAllVehiclesByOwnerId(@PathVariable Long userId) {
        List<VehicleDto> vehicleDtos = vehicleService.FindOwnerVehicle(userId);
        if (!vehicleDtos.isEmpty()) {
            return ResponseEntity.ok(vehicleDtos);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No vehicles found for the user.");
        }
    }
    @GetMapping("/all")
    public ResponseEntity<?> FindAllVehicles() {
        List<VehicleDto> vehicleDtos = vehicleService.FindAllVehicles();
        if (!vehicleDtos.isEmpty()) {
            return ResponseEntity.ok(vehicleDtos);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No vehicles found");
        }
    }
    @PutMapping("/update/{vehicleId}/{userId}")
    public ResponseEntity<?> updateVehicle(
            @PathVariable Long vehicleId,
            @PathVariable Long userId,
            @ModelAttribute VehicleDto updatedVehicleDto) {
        boolean success = vehicleService.updateVehicle(vehicleId, userId, updatedVehicleDto);
        if (success) {
            return ResponseEntity.status(HttpStatus.OK).body("Vehicle updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vehicle not found or unauthorized update attempt.");
        }
    }
    @DeleteMapping("/delete/{vehicleId}/{userId}")
    public ResponseEntity<?> deleteVehicle(@PathVariable Long vehicleId, @PathVariable Long userId) {
        boolean success = vehicleService.deleteVehicle(vehicleId, userId);
        if (success) {
            return ResponseEntity.status(HttpStatus.OK).body("Vehicle deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vehicle not found or unauthorized delete attempt.");
        }
    }



}
