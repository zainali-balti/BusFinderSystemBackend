package com.example.Bus.Finder.System.service.Fare;

import com.example.Bus.Finder.System.dto.FareDto;
import com.example.Bus.Finder.System.entity.*;
import com.example.Bus.Finder.System.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Service
public class FareServiceImplementation implements FareService{
    @Autowired
    private FareRepository fareRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private BusStopRepository busStopRepository;
    @Autowired
    private BusRepository busRepository;

    public Fare createFare(FareDto fareDto) {
        Route route = routeRepository.findById(fareDto.getRouteId())
                .orElseThrow(() -> new RuntimeException("Route not found"));
        BusStop sourceStop = busStopRepository.findById(fareDto.getSourceStopId())
                .orElseThrow(() -> new RuntimeException("Source stop not found"));
        BusStop destinationStop = busStopRepository.findById(fareDto.getDestinationStopId())
                .orElseThrow(() -> new RuntimeException("Destination stop not found"));
        Bus bus = busRepository.findById(fareDto.getBusId())
                .orElseThrow(() -> new RuntimeException("Bus  not found"));

        Fare fare = new Fare();
        fare.setRoute(route);
        fare.setSourceStop(sourceStop);
        fare.setDestinationStop(destinationStop);
        fare.setBus(bus);
        fare.setFare(fareDto.getFare());

        return fareRepository.save(fare);
    }

    public List<Fare> getAllFares() {
        return fareRepository.findAll();
    }

    public Fare getFareById(Long fareId) {
        return fareRepository.findById(fareId)
                .orElseThrow(() -> new RuntimeException("Fare not found"));
    }

    public Fare updateFare(Long fareId, FareDto fareDto) {
        Fare existingFare = fareRepository.findById(fareId)
                .orElseThrow(() -> new RuntimeException("Fare not found"));

        if (fareDto.getRouteId() != null) {
            Route updatedRoute = routeRepository.findById(fareDto.getRouteId())
                    .orElseThrow(() -> new RuntimeException("Route not found"));
            existingFare.setRoute(updatedRoute);
        }

        if (fareDto.getSourceStopId() != null) {
            BusStop updatedSourceStop = busStopRepository.findById(fareDto.getSourceStopId())
                    .orElseThrow(() -> new RuntimeException("Source stop not found"));
            existingFare.setSourceStop(updatedSourceStop);
        }

        if (fareDto.getDestinationStopId() != null) {
            BusStop updatedDestinationStop = busStopRepository.findById(fareDto.getDestinationStopId())
                    .orElseThrow(() -> new RuntimeException("Destination stop not found"));
            existingFare.setDestinationStop(updatedDestinationStop);
        }
        if (fareDto.getBusId() != null) {
            Bus bus = busRepository.findById(fareDto.getBusId())
                    .orElseThrow(() -> new RuntimeException("Bus not found"));
            existingFare.setBus(bus);
        }

        if (fareDto.getFare() != null) {
            existingFare.setFare(fareDto.getFare());
        }

        return fareRepository.save(existingFare);
    }

    public void deleteFare(Long fareId) {
        Fare fare = fareRepository.findById(fareId)
                .orElseThrow(() -> new RuntimeException("Fare not found"));
        fareRepository.delete(fare);
    }
    public List<Fare> getFaresByStops(Long sourceStopId, Long destinationStopId) {
        BusStop sourceStop = busStopRepository.findById(sourceStopId)
                .orElseThrow(() -> new RuntimeException("Source stop not found"));
        BusStop destinationStop = busStopRepository.findById(destinationStopId)
                .orElseThrow(() -> new RuntimeException("Destination stop not found"));

        return fareRepository.findBySourceStopAndDestinationStop(sourceStop, destinationStop);
    }
}
