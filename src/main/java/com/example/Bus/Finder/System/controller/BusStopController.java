package com.example.Bus.Finder.System.controller;

import com.example.Bus.Finder.System.dto.BusStopDto;
import com.example.Bus.Finder.System.entity.BusStop;
import com.example.Bus.Finder.System.service.BusStop.BusStopService;
import com.example.Bus.Finder.System.wrapper.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/stop")
public class BusStopController {
    @Autowired
    private BusStopService busStopService;

    @PostMapping("/add")
    public ResponseEntity<?> createBusStops(@RequestBody BusStopDto busStopDto) {
        try {
            BusStop busStop = busStopService.addBusStops(busStopDto);
            return ResponseEntity.ok(new ApiResponse("BusStop Created Successfully", busStop));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Failed to add BusStop", null));
        }
    }
    @PutMapping("/update/{busStopId}")
    public ResponseEntity<BusStop> updateBusStop(@PathVariable Long busStopId, @RequestBody BusStopDto busStopDto) {
        BusStop updatedBusStop = busStopService.updateBusStop(busStopId, busStopDto);
        return ResponseEntity.ok(updatedBusStop);
    }

    @DeleteMapping("/delete/{busStopId}")
    public ResponseEntity<String> deleteBusStop(@PathVariable Long busStopId) {
        busStopService.deleteBusStop(busStopId);
        return ResponseEntity.ok("BusStop with ID " + busStopId + " deleted successfully.");
    }

    @GetMapping("/all")
    public ResponseEntity<List<BusStop>> getAllBusStops() {
        List<BusStop> busStops = busStopService.getAllBusStops();
        return ResponseEntity.ok(busStops);
    }
    @GetMapping("/{busStopId}")
    public  ResponseEntity<BusStop> getBusStopById(@PathVariable Long busStopId) {
        BusStop busStop = busStopService.getBusStopById(busStopId);
        return ResponseEntity.ok(busStop);
    }
}
