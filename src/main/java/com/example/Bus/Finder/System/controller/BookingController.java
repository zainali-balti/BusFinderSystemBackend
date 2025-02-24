package com.example.Bus.Finder.System.controller;

import com.example.Bus.Finder.System.dto.BookingDto;
import com.example.Bus.Finder.System.entity.Booking;
import com.example.Bus.Finder.System.service.Booking.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/booking")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> createBooking(@RequestBody BookingDto bookingDto) {
    Booking booking = bookingService.createBooking(bookingDto);

    Map<String, Object> response = new HashMap<>();
    response.put("id", booking.getId());
    response.put("message", "Booking created successfully");

    return ResponseEntity.ok(response);
}

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateBooking(@PathVariable Long id, @RequestBody BookingDto bookingDto) {
        Booking updatedBooking = bookingService.updateBooking(id, bookingDto);
        return ResponseEntity.ok("Booking updated successfully with ID: " + updatedBooking.getId());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, String>> deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Booking deleted successfully with ID: " + id);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/all")
    public ResponseEntity<List<BookingDto>> getAllBookings() {
        List<BookingDto> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingDto>> getBookingsByUserId(@PathVariable Long userId) {
        List<BookingDto> bookings = bookingService.getBookingsByUserId(userId);
        return ResponseEntity.ok(bookings);
    }
}
