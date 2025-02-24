package com.example.Bus.Finder.System.service.Booking;

import com.example.Bus.Finder.System.dto.BookingDto;
import com.example.Bus.Finder.System.entity.*;
import com.example.Bus.Finder.System.enums.TransactionType;
import com.example.Bus.Finder.System.exceptionHandling.VehicleAlreadyBookedException;
import com.example.Bus.Finder.System.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private WalletTransactionRepository walletTransactionRepository;

    public Booking createBooking(BookingDto bookingDto) {
        User user = userRepository.findById(bookingDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Vehicle vehicle = vehicleRepository.findById(bookingDto.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        boolean isVehicleBooked = bookingRepository.existsByVehiclesAndEndDateAfter(vehicle, LocalDateTime.now());
        if (isVehicleBooked) {
            throw new VehicleAlreadyBookedException("This vehicle is already booked and cannot be booked again until its current booking ends.");
        }
        double distance = bookingDto.getDistance();
        double pricePerKm = vehicle.getPrice();
        double totalPrice = distance * pricePerKm;

        Wallet wallet = walletRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Wallet not found for user ID: " + user.getId()));

        if (wallet.getBalance().compareTo(BigDecimal.valueOf(totalPrice)) < 0) {
            throw new RuntimeException("Insufficient balance in wallet");
        }

        wallet.setBalance(wallet.getBalance().subtract(BigDecimal.valueOf(totalPrice)));
        walletRepository.save(wallet);

        WalletTransaction transaction = new WalletTransaction();
        transaction.setWallet(wallet);
        transaction.setAmount(BigDecimal.valueOf(totalPrice));
        transaction.setTransactionType(TransactionType.DEBIT);
        transaction.setDescription("Bus booking payment");
        walletTransactionRepository.save(transaction);
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setVehicles(vehicle);
        vehicle.setBooked(true);
        vehicleRepository.save(vehicle);
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
        // Fetch the booking details
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // Retrieve the associated vehicle
        Vehicle vehicle = booking.getVehicles();

        // Update the vehicle's status to available
        vehicle.setBooked(false);
        vehicleRepository.save(vehicle);

        // Fetch the user's wallet
        Wallet wallet = walletRepository.findByUserId(booking.getUser().getId())
                .orElseThrow(() -> new RuntimeException("Wallet not found for user ID: " +
                        booking.getUser().getId()));

        // Calculate the refund amount
        BigDecimal refundAmount = BigDecimal.valueOf(booking.getTotalPrice());

        // Update the wallet balance
        wallet.setBalance(wallet.getBalance().add(refundAmount));
        walletRepository.save(wallet);

        // Record the refund transaction
        WalletTransaction transaction = new WalletTransaction();
        transaction.setWallet(wallet);
        transaction.setAmount(refundAmount);
        transaction.setTransactionType(TransactionType.CREDIT);
        transaction.setDescription("Refund for canceled vehicle booking");
        walletTransactionRepository.save(transaction);

        // Delete the booking
        bookingRepository.deleteById(bookingId);
    }
    public List<BookingDto> getAllBookings() {
        List<Booking> bookingDtos = bookingRepository.findAll();
        return bookingDtos.stream()
                .map(Booking::getAllBooking)
                .toList();
    }


    @Scheduled(fixedRate = 60000)
    public void expireBookings() {
        List<Booking> expiredBookings = bookingRepository.findByEndDateBefore(LocalDateTime.now());
        for (Booking booking : expiredBookings) {
            Vehicle vehicle = booking.getVehicles();
            vehicle.setBooked(false);
            vehicleRepository.save(vehicle);
        }
    }

    public List<BookingDto> getBookingsByUserId(Long userId) {
        List<Booking> bookings = bookingRepository.findByUserId(userId);

        return bookings.stream()
                .map(Booking::getAllBooking)
                .toList();
    }


}
