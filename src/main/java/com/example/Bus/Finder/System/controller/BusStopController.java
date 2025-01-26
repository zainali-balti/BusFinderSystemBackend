package com.example.Bus.Finder.System.controller;

import com.example.Bus.Finder.System.dto.BusStopDto;
import com.example.Bus.Finder.System.entity.BusStop;
import com.example.Bus.Finder.System.service.BusStop.BusStopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stop")
public class BusStopController {
    @Autowired
    private BusStopService busStopService;

    @PostMapping("/add/{busId}")
    public ResponseEntity<?> createBusStops(@PathVariable Long busId, @RequestBody BusStopDto busStopDto){
        BusStop busStop = busStopService.addBusStops(busId,busStopDto);
        return ResponseEntity.ok("BusStop Created Successfully "+busStop);
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
