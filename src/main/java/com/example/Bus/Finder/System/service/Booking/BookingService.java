package com.example.Bus.Finder.System.service.Booking;

import com.example.Bus.Finder.System.dto.BookingDto;
import com.example.Bus.Finder.System.entity.Booking;
import com.example.Bus.Finder.System.repository.BookingRepository;
import com.example.Bus.Finder.System.repository.VehicleRepository;

import java.util.List;

public interface BookingService {
    Booking createBooking(BookingDto bookingDto);
    List<BookingDto> getAllBookings();
    void deleteBooking(Long bookingId);
    Booking updateBooking(Long bookingId, BookingDto bookingDto);
    void expireBookings();
    List<BookingDto> getBookingsByUserId(Long userId);
}
