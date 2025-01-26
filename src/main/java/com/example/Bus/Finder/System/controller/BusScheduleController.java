package com.example.Bus.Finder.System.controller;

import com.example.Bus.Finder.System.dto.BusScheduleDto;
import com.example.Bus.Finder.System.entity.BusSchedule;
import com.example.Bus.Finder.System.service.BusSchedule.BusScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedule")
public class BusScheduleController {
    @Autowired
    private BusScheduleService busScheduleService;

    @PostMapping("/add/{busId}/{busStopId}/{routeId}")
    public ResponseEntity<String> createBusSchedule(
            @PathVariable Long busId,
            @PathVariable Long busStopId,
            @PathVariable Long routeId,
            @RequestBody BusScheduleDto busScheduleDto){

        BusSchedule busSchedule = busScheduleService.createBusSchedule(busId,busStopId,routeId,busScheduleDto);
        return ResponseEntity.ok("Bus Schedule Created Successfully "+busSchedule);
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
    @PutMapping("/update/{scheduleId}/{busId}/{busStopId}/{routeId}")
    public ResponseEntity<BusSchedule> updateBusSchedule(
            @PathVariable Long scheduleId,
            @PathVariable Long busId,
            @PathVariable Long busStopId,
            @PathVariable Long routeId,
            @RequestBody BusScheduleDto busScheduleDto) {
        BusSchedule updatedBusSchedule = busScheduleService.updateBusSchedule(scheduleId,busId,busStopId,routeId,busScheduleDto);
        return ResponseEntity.ok(updatedBusSchedule);
    }

    // Delete Bus Schedule
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBusSchedule(@PathVariable Long id) {
        busScheduleService.deleteBusSchedule(id);
        return ResponseEntity.noContent().build();
    }
}
