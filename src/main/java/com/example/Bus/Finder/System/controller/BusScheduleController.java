package com.example.Bus.Finder.System.controller;

import com.example.Bus.Finder.System.dto.BusScheduleDto;
import com.example.Bus.Finder.System.entity.BusSchedule;
import com.example.Bus.Finder.System.service.BusSchedule.BusScheduleService;
import com.example.Bus.Finder.System.wrapper.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/schedule")
public class BusScheduleController {
    @Autowired
    private BusScheduleService busScheduleService;

    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> createBusSchedule(
            @RequestBody BusScheduleDto busScheduleDto) {

        try {
            BusSchedule busSchedule = busScheduleService.createBusSchedule(busScheduleDto);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Bus Schedule Created Successfully");

            Map<String, Object> data = new HashMap<>();
            data.put("busSchedule", busSchedule);
            data.put("routeId", busSchedule.getRoute().getId());

            response.put("data", data);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error creating bus schedule: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


    // Get all Bus Schedules
    @GetMapping("/all")
    public ResponseEntity<List<BusSchedule>> getAllBusSchedules() {
        List<BusSchedule> busSchedules = busScheduleService.getAllBusSchedules();
        return ResponseEntity.ok(busSchedules);
    }

    // Get Bus Schedule by ID
    @GetMapping("/{id}")
    public ResponseEntity<BusSchedule> getBusScheduleById(@PathVariable Long id) {
        BusSchedule busSchedule = busScheduleService.getBusScheduleById(id);
        return ResponseEntity.ok(busSchedule);
    }

    // Update Bus Schedule
    @PutMapping("/update/{scheduleId}")
    public ResponseEntity<BusSchedule> updateBusSchedule(
            @PathVariable Long scheduleId,
            @RequestBody BusScheduleDto busScheduleDto) {
        BusSchedule updatedBusSchedule = busScheduleService.updateBusSchedule(scheduleId,busScheduleDto);
        return ResponseEntity.ok(updatedBusSchedule);
    }

    // Delete Bus Schedule
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBusSchedule(@PathVariable Long id) {
        busScheduleService.deleteBusSchedule(id);
        return ResponseEntity.noContent().build();
    }
}
