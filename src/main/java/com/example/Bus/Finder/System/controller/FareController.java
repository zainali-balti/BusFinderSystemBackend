package com.example.Bus.Finder.System.controller;


import com.example.Bus.Finder.System.dto.FareDto;
import com.example.Bus.Finder.System.entity.Bus;
import com.example.Bus.Finder.System.entity.Fare;
import com.example.Bus.Finder.System.service.Fare.FareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/fare")
public class FareController {

    @Autowired
    private FareService fareService;

    // Create a new Fare
    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> createFare(@RequestBody FareDto fareDto) {
        Fare fare = fareService.createFare(fareDto);

        // Construct response including busId
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Fare added successfully");
        response.put("busId", fare.getBus().getBusId());

        return ResponseEntity.ok(response);
    }

    // Get all Fares
    @GetMapping("/all")
    public ResponseEntity<List<Fare>> getAllFares() {
        List<Fare> fares = fareService.getAllFares();
        return ResponseEntity.ok(fares);
    }

    // Get Fare by ID
    @GetMapping("/{id}")
    public ResponseEntity<Fare> getFareById(@PathVariable Long id) {
        Fare fare = fareService.getFareById(id);
        return ResponseEntity.ok(fare);
    }

    // Update a Fare
    @PutMapping("/update/{id}")
    public ResponseEntity<Fare> updateFare(@PathVariable Long id, @RequestBody FareDto fareDto) {
        Fare updatedFare = fareService.updateFare(id, fareDto);
        return ResponseEntity.ok(updatedFare);
    }

    // Delete a Fare
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteFare(@PathVariable Long id) {
        fareService.deleteFare(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/bus/{busId}")
    public ResponseEntity<List<FareDto>> getAllByBusId(@PathVariable Long busId){
        List<FareDto> fare = fareService.getFareByBusId(busId);
        return ResponseEntity.ok(fare);
    }
    @GetMapping("/buses")
    public ResponseEntity<List<Map<String, Object>>> getBusesBySourceAndDestination(
            @RequestParam Long sourceId,
            @RequestParam Long destinationId) {

        List<Map<String, Object>> busDetails = fareService.getBusesWithFareAndSchedule(sourceId, destinationId);
        return ResponseEntity.ok(busDetails);
    }
}
