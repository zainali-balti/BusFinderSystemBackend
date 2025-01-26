package com.example.Bus.Finder.System.controller;

import com.example.Bus.Finder.System.dto.BusDto;
import com.example.Bus.Finder.System.entity.Bus;
import com.example.Bus.Finder.System.service.Bus.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bus")
public class BusController {
    @Autowired
    private BusService busService;

    @PostMapping("/add/{userId}")
    public ResponseEntity<?> createBus(@PathVariable Long userId, @RequestBody BusDto busDto){
        Bus bus = busService.addBuses(userId,busDto);
        return ResponseEntity.ok("Bus created Successfully"+bus);
    }
    @PutMapping("/update/{busId}")
    public ResponseEntity<?> updatedBus(@PathVariable Long busId,@RequestBody BusDto busDto){
        Bus bus = busService.updateBus(busId,busDto);
        return ResponseEntity.ok("Updated Bus Successfully "+bus);
    }
    @DeleteMapping("/delete/{busId}")
    public ResponseEntity<?> deleteBus(@PathVariable Long busId) {
        try {
            busService.deleteBus(busId);
            return ResponseEntity.ok("Bus Deleted Successfully with ID: " + busId);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @GetMapping("/all")
    public ResponseEntity<?> getAllBus(){
        List<Bus> busList = busService.getAllBuses();
        return ResponseEntity.ok(busList);
    }
    @GetMapping("/all/{busId}")
    public ResponseEntity<?> getBusById(@PathVariable Long busId){
        Bus bus = busService.getBusById(busId);
        return  ResponseEntity.ok(bus);
    }
}
