package com.example.Bus.Finder.System.service.BusBooking;

import com.example.Bus.Finder.System.dto.BusBookingDto;
import com.example.Bus.Finder.System.entity.Bus;
import com.example.Bus.Finder.System.entity.BusBooking;
import com.example.Bus.Finder.System.entity.BusStop;
import com.example.Bus.Finder.System.entity.User;
import com.example.Bus.Finder.System.repository.BusBookingRepository;
import com.example.Bus.Finder.System.repository.BusRepository;
import com.example.Bus.Finder.System.repository.BusStopRepository;
import com.example.Bus.Finder.System.repository.UserRepository;
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

        // Create a new Bus Booking
        public BusBooking createBusBooking(BusBookingDto busBookingDto) {
            User user = userRepository.findById(busBookingDto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Bus bus = busRepository.findById(busBookingDto.getBusId())
                    .orElseThrow(() -> new RuntimeException("Bus not found"));

            if (bus.getCapacity() <= 0) {
                throw new RuntimeException("Bus is fully booked");
            }

            BusStop sourceStop = busStopRepository.findById(busBookingDto.getSourceStopId())
                    .orElseThrow(() -> new RuntimeException("Source stop not found"));
            BusStop destinationStop = busStopRepository.findById(busBookingDto.getDestinationStopId())
                    .orElseThrow(() -> new RuntimeException("Destination stop not found"));

            BusBooking busBooking = new BusBooking();
            busBooking.setUser(user);
            busBooking.setBus(bus);
            busBooking.setSourceStop(sourceStop);
            busBooking.setDestinationStop(destinationStop);
            busBooking.setBookingTime(busBookingDto.getBookingTime());
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

            if (busBookingDto.getBusId() != null) {
                Bus updatedBus = busRepository.findById(busBookingDto.getBusId())
                        .orElseThrow(() -> new RuntimeException("Bus not found"));
                existingBooking.setBus(updatedBus);
            }

            if (busBookingDto.getSourceStopId() != null) {
                BusStop updatedSourceStop = busStopRepository.findById(busBookingDto.getSourceStopId())
                        .orElseThrow(() -> new RuntimeException("Source stop not found"));
                existingBooking.setSourceStop(updatedSourceStop);
            }

            if (busBookingDto.getDestinationStopId() != null) {
                BusStop updatedDestinationStop = busStopRepository.findById(busBookingDto.getDestinationStopId())
                        .orElseThrow(() -> new RuntimeException("Destination stop not found"));
                existingBooking.setDestinationStop(updatedDestinationStop);
            }

            if (busBookingDto.getStatus() != null) {
                existingBooking.setStatus(busBookingDto.getStatus());
            }

            return busBookingRepository.save(existingBooking);
        }

        public void deleteBusBooking(Long bookingId) {
            BusBooking existingBooking = busBookingRepository.findById(bookingId)
                    .orElseThrow(() -> new RuntimeException("Bus Booking not found"));
            Bus bus = busRepository.findById(existingBooking.getBus().getBusId())
                    .orElseThrow(() -> new RuntimeException("Bus not found"));

            if (bus.getCapacity() <= 0) {
                throw new RuntimeException("Bus is fully booked");
            }
            bus.setCapacity(bus.getCapacity() + 1);
            busRepository.save(bus);
            busBookingRepository.delete(existingBooking);
        }
}

