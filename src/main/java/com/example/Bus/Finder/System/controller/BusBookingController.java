package com.example.Bus.Finder.System.controller;

import com.example.Bus.Finder.System.dto.BusBookingDto;
import com.example.Bus.Finder.System.entity.BusBooking;
import com.example.Bus.Finder.System.service.BusBooking.BusBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bus_booking")
public class BusBookingController {
    @Autowired
    private BusBookingService busBookingService;

    // Create a new Bus Booking
    @PostMapping("/add")
    public ResponseEntity<BusBooking> createBusBooking(@RequestBody BusBookingDto busBookingDto) {
        BusBooking busBooking = busBookingService.createBusBooking(busBookingDto);
        return ResponseEntity.ok(busBooking);
    }

    // Get all Bus Bookings
    @GetMapping("/all")
    public ResponseEntity<List<BusBooking>> getAllBusBookings() {
        List<BusBooking> busBookings = busBookingService.getAllBusBookings();
        return ResponseEntity.ok(busBookings);
    }

    // Get Bus Booking by ID
    @GetMapping("/{id}")
    public ResponseEntity<BusBooking> getBusBookingById(@PathVariable Long id) {
        BusBooking busBooking = busBookingService.getBusBookingById(id);
        return ResponseEntity.ok(busBooking);
    }

    // Update Bus Booking
    @PutMapping("/update/{id}")
    public ResponseEntity<BusBooking> updateBusBooking(
            @PathVariable Long id,
            @RequestBody BusBookingDto busBookingDto) {
        BusBooking updatedBusBooking = busBookingService.updateBusBooking(id, busBookingDto);
        return ResponseEntity.ok(updatedBusBooking);
    }

    // Delete Bus Booking
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBusBooking(@PathVariable Long id) {
        busBookingService.deleteBusBooking(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BusBookingDto>> getBookingsByUserId(@PathVariable Long userId) {
        List<BusBookingDto> bookings = busBookingService.getBookingsByUserId(userId);
        return ResponseEntity.ok(bookings);
    }
}
