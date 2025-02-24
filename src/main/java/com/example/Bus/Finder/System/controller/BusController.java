package com.example.Bus.Finder.System.controller;

import com.example.Bus.Finder.System.dto.BusDto;
import com.example.Bus.Finder.System.entity.Bus;
import com.example.Bus.Finder.System.entity.User;
import com.example.Bus.Finder.System.enums.Status;
import com.example.Bus.Finder.System.service.Bus.BusService;
import com.example.Bus.Finder.System.wrapper.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/bus")
public class BusController {
    @Autowired
    private BusService busService;

    @PostMapping("/add")
    public ResponseEntity<?> createBus(@RequestParam("busName") String busName,
                                       @RequestParam("busNumber") String busNumber,
                                       @RequestParam("capacity") int capacity,
                                       @RequestParam("userId") Long userId,
                                       @RequestParam("status") String status,
                                       @RequestParam("img") MultipartFile img) {
        try {
            BusDto busDto = new BusDto();
            busDto.setBusName(busName);
            busDto.setBusNumber(busNumber);
            busDto.setCapacity(capacity);
            busDto.setUserId(userId);
            busDto.setStatus(Status.valueOf(status));
            if (img != null && !img.isEmpty()) {
                byte[] imageBytes = img.getBytes();
                busDto.setImg(imageBytes);
            }
            Bus bus = busService.addBuses(busDto);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Bus created successfully");
            response.put("busId", bus.getBusId());
            response.put("userId",bus.getUser().getId());

            return ResponseEntity.ok(response);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Error processing image."));
        }
    }



    @PutMapping("/update/{busId}")
    public ResponseEntity<?> updatedBus(@PathVariable Long busId,
                                        @RequestParam("busName") String busName,
                                        @RequestParam("busNumber") String busNumber,
                                        @RequestParam("capacity") int capacity,
                                        @RequestParam("userId") Long userId,
                                        @RequestParam("status") String status,
                                        @RequestParam("img") MultipartFile img) {
        try {
            BusDto busDto = new BusDto();
            busDto.setBusId(busId);
            busDto.setBusName(busName);
            busDto.setBusNumber(busNumber);
            busDto.setCapacity(capacity);
            busDto.setUserId(userId);
            busDto.setStatus(Status.valueOf(status));
            if (img != null && !img.isEmpty()) {
                byte[] imageBytes = img.getBytes();
                busDto.setImg(imageBytes);
            }
            Bus bus = busService.updateBus(busId, busDto);
            return ResponseEntity.ok(new ApiResponse("success", "Updated bus successfully"));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing image.");
        }
    }

    @DeleteMapping("/delete/{busId}")
    public ResponseEntity<?> deleteBus(@PathVariable Long busId) {
        try {
            busService.deleteBus(busId);
            return ResponseEntity.noContent().build();  // HTTP 204 No Content
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());  // HTTP 404 if bus not found
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
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Bus>> getBusesByUserId(@PathVariable Long userId) {
        List<Bus> buses = busService.getBusesByUserId(userId);
        return ResponseEntity.ok(buses);
    }
}
