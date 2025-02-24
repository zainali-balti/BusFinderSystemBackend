package com.example.Bus.Finder.System.controller;

import com.example.Bus.Finder.System.dto.VehicleDto;
import com.example.Bus.Finder.System.service.Company.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public ResponseEntity<List<VehicleDto>> findAllVehicles() {
    List<VehicleDto> vehicleDtos = vehicleService.FindAllVehicles();
    if (!vehicleDtos.isEmpty()) {
        return ResponseEntity.ok(vehicleDtos); // Return the list of vehicles directly
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList()); // Empty list if no data
    }
}

    @PutMapping("/update/{vehicleId}/{userId}")
    public ResponseEntity<?> updateVehicle(
            @PathVariable Long vehicleId,
            @PathVariable Long userId,
            @ModelAttribute VehicleDto updatedVehicleDto,
            @RequestParam(required = false) MultipartFile img) {
        boolean success = vehicleService.updateVehicle(vehicleId, userId, updatedVehicleDto, img);
        if (success) {
            return ResponseEntity.status(HttpStatus.OK).body("Vehicle updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vehicle not found or unauthorized update attempt.");
        }
    }

    @DeleteMapping("/delete/{vehicleId}/{userId}")
    public ResponseEntity<Map<String, String>> deleteVehicle(@PathVariable Long vehicleId, @PathVariable Long userId) {
        boolean success = vehicleService.deleteVehicle(vehicleId, userId);
        Map<String, String> response = new HashMap<>();

        if (success) {
            response.put("message", "Vehicle deleted successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Vehicle not found or unauthorized delete attempt");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/{vehicleId}")
    public ResponseEntity<VehicleDto> getVehicleById(@PathVariable Long vehicleId) {
        VehicleDto vehicleDto = vehicleService.findVehicleById(vehicleId);
        return ResponseEntity.ok(vehicleDto);
    }




}
