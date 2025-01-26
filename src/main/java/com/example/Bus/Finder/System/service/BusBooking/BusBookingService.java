package com.example.Bus.Finder.System.service.BusBooking;

import com.example.Bus.Finder.System.dto.BusBookingDto;
import com.example.Bus.Finder.System.entity.BusBooking;

import java.util.List;

public interface BusBookingService {
    BusBooking createBusBooking(BusBookingDto busBookingDto);
    void deleteBusBooking(Long bookingId);
    BusBooking updateBusBooking(Long bookingId, BusBookingDto busBookingDto);
    BusBooking getBusBookingById(Long bookingId);
    List<BusBooking> getAllBusBookings();
}
