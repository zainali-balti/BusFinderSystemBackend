package com.example.Bus.Finder.System.controller;

import com.example.Bus.Finder.System.dto.BookingDto;
import com.example.Bus.Finder.System.entity.Booking;
import com.example.Bus.Finder.System.service.Booking.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/booking")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping("/add")
    public ResponseEntity<?> createBooking(@RequestBody BookingDto bookingDto) {
        Booking booking = bookingService.createBooking(bookingDto);
        return ResponseEntity.ok("Booking created successfully with ID: " + booking.getId());
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateBooking(@PathVariable Long id, @RequestBody BookingDto bookingDto) {
        Booking updatedBooking = bookingService.updateBooking(id, bookingDto);
        return ResponseEntity.ok("Booking updated successfully with ID: " + updatedBooking.getId());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.ok("Booking deleted successfully with ID: " + id);
    }

    @GetMapping("/all")
    public ResponseEntity<List<BookingDto>> getAllBookings() {
        List<BookingDto> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }
}
