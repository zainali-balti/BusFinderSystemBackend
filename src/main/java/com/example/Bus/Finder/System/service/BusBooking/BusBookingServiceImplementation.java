package com.example.Bus.Finder.System.service.BusBooking;

import com.example.Bus.Finder.System.dto.BusBookingDto;
import com.example.Bus.Finder.System.entity.*;
import com.example.Bus.Finder.System.enums.TransactionType;
import com.example.Bus.Finder.System.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BusBookingServiceImplementation implements BusBookingService{
        @Autowired
        private BusBookingRepository busBookingRepository;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private BusRepository busRepository;

        @Autowired
        private FareRepository fareRepository;
        @Autowired
        private WalletRepository walletRepository;
        @Autowired
        private WalletTransactionRepository walletTransactionRepository;

        public BusBooking createBusBooking(BusBookingDto busBookingDto) {

        User user = userRepository.findById(busBookingDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Fare fare = fareRepository.findByIdWithBus(busBookingDto.getFareId())
                .orElseThrow(() -> new RuntimeException("Fare not found"));

        Bus bus = fare.getBus();
        if (bus == null) {
            throw new RuntimeException("Bus information is missing in the Fare entity");
        }

        if (bus.getCapacity() <= 0) {
            throw new RuntimeException("Bus is fully booked");
        }

        Wallet wallet = walletRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Wallet not found for user ID: " + user.getId()));

        if (wallet.getBalance().compareTo(fare.getFare()) < 0) {
            throw new RuntimeException("Insufficient balance in wallet");
        }

        wallet.setBalance(wallet.getBalance().subtract(fare.getFare()));
        walletRepository.save(wallet);

        WalletTransaction transaction = new WalletTransaction();
        transaction.setWallet(wallet);
        transaction.setAmount(fare.getFare());
        transaction.setTransactionType(TransactionType.DEBIT);
        transaction.setDescription("Bus booking payment");
        walletTransactionRepository.save(transaction);

        BusBooking busBooking = new BusBooking();
        busBooking.setUser(user);
        busBooking.setFare(fare);
        busBooking.setBookingTime(busBookingDto.getBookingTime());
        busBooking.setTotalFare(fare.getFare());
        busBooking.setStatus(busBookingDto.getStatus());

        bus.setCapacity(bus.getCapacity() - 1);
        busRepository.save(bus);

        return busBookingRepository.save(busBooking);
    }

        public List<BusBooking> getAllBusBookings() {
            return busBookingRepository.findAll();
        }

        public BusBooking getBusBookingById(Long bookingId) {
            return busBookingRepository.findById(bookingId)
                    .orElseThrow(() -> new RuntimeException("Bus Booking not found"));
        }

        public BusBooking updateBusBooking(Long bookingId, BusBookingDto busBookingDto) {
            BusBooking existingBooking = busBookingRepository.findById(bookingId)
                    .orElseThrow(() -> new RuntimeException("Bus Booking not found"));

            if (busBookingDto.getUserId() != null) {
                User updatedUser = userRepository.findById(busBookingDto.getUserId())
                        .orElseThrow(() -> new RuntimeException("User not found"));
                existingBooking.setUser(updatedUser);
            }
            if (busBookingDto.getFareId() != null) {
                Fare fare = fareRepository.findByIdWithBus(busBookingDto.getFareId())
                        .orElseThrow(() -> new RuntimeException("Fare not found"));
                existingBooking.setFare(fare);
            }

            if (busBookingDto.getStatus() != null) {
                existingBooking.setStatus(busBookingDto.getStatus());
            }

            return busBookingRepository.save(existingBooking);
        }

        public void deleteBusBooking(Long bookingId) {

        BusBooking existingBooking = busBookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Bus Booking not found"));

        Bus bus = busRepository.findById(existingBooking.getFare().getBus().getBusId())
                .orElseThrow(() -> new RuntimeException("Bus not found"));

        bus.setCapacity(bus.getCapacity() + 1);
        busRepository.save(bus);

        Wallet wallet = walletRepository.findByUserId(existingBooking.getUser().getId())
                .orElseThrow(() -> new RuntimeException("Wallet not found for user ID: " +
                        existingBooking.getUser().getId()));

        BigDecimal refundAmount = existingBooking.getTotalFare();
        wallet.setBalance(wallet.getBalance().add(refundAmount));
        walletRepository.save(wallet);

        WalletTransaction transaction = new WalletTransaction();
        transaction.setWallet(wallet);
        transaction.setAmount(refundAmount);
        transaction.setTransactionType(TransactionType.CREDIT);
        transaction.setDescription("Refund for canceled bus booking");
        walletTransactionRepository.save(transaction);

        busBookingRepository.delete(existingBooking);
    }
    public List<BusBookingDto> getBookingsByUserId(Long userId) {
        List<BusBooking> bookings = busBookingRepository.findByUserId(userId);
        return bookings.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private BusBookingDto convertToDto(BusBooking booking) {
        BusBookingDto dto = new BusBookingDto();
        dto.setBookingId(booking.getBookingId());
        dto.setUserId(booking.getUser().getId());
        dto.setFareId(booking.getFare().getFareId());
        dto.setBookingTime(booking.getBookingTime());
        dto.setStatus(booking.getStatus());
        dto.setTotalFare(booking.getTotalFare());
        return dto;
    }

}

