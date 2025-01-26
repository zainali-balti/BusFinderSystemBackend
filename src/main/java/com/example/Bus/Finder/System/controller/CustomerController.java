package com.example.Bus.Finder.System.controller;

import com.example.Bus.Finder.System.dto.VehicleDto;
import com.example.Bus.Finder.System.service.Customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("all")
    public ResponseEntity<?> FindAllVehicles() {
        List<VehicleDto> vehicleDtos = customerService.FindAllVehicles();
        if (!vehicleDtos.isEmpty()) {
            return ResponseEntity.ok(vehicleDtos);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No vehicles found");
        }
    }
    @GetMapping("/byName")
    public ResponseEntity<?> FindAllVehiclesByName(@RequestParam String vehicleName) {
        List<VehicleDto> vehicleDtos = customerService.FindVehicleName(vehicleName);
        if (!vehicleDtos.isEmpty()) {
            return ResponseEntity.ok(vehicleDtos);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No vehicles found for the user.");
        }
    }
}
