package com.example.Bus.Finder.System.service.Booking;

import com.example.Bus.Finder.System.dto.BookingDto;
import com.example.Bus.Finder.System.entity.Booking;
import com.example.Bus.Finder.System.entity.User;
import com.example.Bus.Finder.System.entity.Vehicle;
import com.example.Bus.Finder.System.exceptionHandling.VehicleAlreadyBookedException;
import com.example.Bus.Finder.System.repository.BookingRepository;
import com.example.Bus.Finder.System.repository.UserRepository;
import com.example.Bus.Finder.System.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImplementation implements BookingService{

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    public Booking createBooking(BookingDto bookingDto) {
        User user = userRepository.findById(bookingDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Vehicle vehicle = vehicleRepository.findById(bookingDto.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        boolean isVehicleBooked = bookingRepository.existsByVehiclesAndEndDateAfter(vehicle, LocalDateTime.now());
        if (isVehicleBooked) {
            throw new VehicleAlreadyBookedException("This vehicle is already booked and cannot be booked again until its current booking ends.");
        }
        double distance = bookingDto.getDistance(); // Distance entered by the user
        double pricePerKm = vehicle.getPrice();    // Price per km set by the owner
        double totalPrice = distance * pricePerKm;

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setVehicles(vehicle);
        booking.setBookingDate(LocalDateTime.now());
        booking.setTotalPrice(totalPrice);
        booking.setDistance(distance);
        booking.setEndDate(bookingDto.getEndDate());
        return bookingRepository.save(booking);
    }

    public Booking updateBooking(Long bookingId, BookingDto bookingDto) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        User user = userRepository.findById(bookingDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Optional<Vehicle> vehicles = vehicleRepository.findById(bookingDto.getVehicleId());
        if (vehicles.isEmpty()) {
            throw new RuntimeException("No vehicles found for the given IDs");
        }
        double totalPrice = vehicles.stream()
                .mapToDouble(vehicle -> vehicle.getDistance() * 4)
                .sum();
        double totalDistance = vehicles.stream()
                .mapToDouble(Vehicle::getDistance)
                .sum();

        booking.setUser(user);
        booking.setVehicles(vehicles.get());
        booking.setTotalPrice(totalPrice);
        booking.setDistance(totalDistance);
        booking.setEndDate(bookingDto.getEndDate());
        return bookingRepository.save(booking);
    }
    public void deleteBooking(Long bookingId) {
        if (!bookingRepository.existsById(bookingId)) {
            throw new RuntimeException("Booking not found");
        }
        bookingRepository.deleteById(bookingId);
    }
    public List<BookingDto> getAllBookings() {
        List<Booking> bookingDtos = bookingRepository.findAll();
        return bookingDtos.stream()
                .map(Booking::getAllBooking)
                .toList();
    }


}
