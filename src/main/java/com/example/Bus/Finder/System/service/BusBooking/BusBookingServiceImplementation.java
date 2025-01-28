package com.example.Bus.Finder.System.service.BusBooking;

import com.example.Bus.Finder.System.dto.BusBookingDto;
import com.example.Bus.Finder.System.entity.*;
import com.example.Bus.Finder.System.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusBookingServiceImplementation implements BusBookingService{
        @Autowired
        private BusBookingRepository busBookingRepository;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private BusRepository busRepository;

        @Autowired
        private BusStopRepository busStopRepository;
        @Autowired
        private FareRepository fareRepository;

        public BusBooking createBusBooking(BusBookingDto busBookingDto) {
            User user = userRepository.findById(busBookingDto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            Fare fare = fareRepository.findByIdWithBus(busBookingDto.getFareId())
                    .orElseThrow(() -> new RuntimeException("Fare  not found"));
            Bus bus = fare.getBus();
            if (bus == null) {
                throw new RuntimeException("Bus information is missing in the Fare entity");
            }

            if (bus.getCapacity() <= 0) {
                throw new RuntimeException("Bus is fully booked");
            }

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

            if (bus.getCapacity() <= 0) {
                throw new RuntimeException("Bus is fully booked");
            }
            bus.setCapacity(bus.getCapacity() + 1);
            busRepository.save(bus);
            busBookingRepository.delete(existingBooking);
        }
}

