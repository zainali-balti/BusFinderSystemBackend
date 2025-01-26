package com.example.Bus.Finder.System.service.Fare;

import com.example.Bus.Finder.System.dto.FareDto;
import com.example.Bus.Finder.System.entity.BusStop;
import com.example.Bus.Finder.System.entity.Fare;
import com.example.Bus.Finder.System.entity.Route;
import com.example.Bus.Finder.System.repository.BusStopRepository;
import com.example.Bus.Finder.System.repository.FareRepository;
import com.example.Bus.Finder.System.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FareServiceImplementation implements FareService{
    @Autowired
    private FareRepository fareRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private BusStopRepository busStopRepository;

    // Create a new Fare
    public Fare createFare(FareDto fareDto) {
        Route route = routeRepository.findById(fareDto.getRouteId())
                .orElseThrow(() -> new RuntimeException("Route not found"));
        BusStop sourceStop = busStopRepository.findById(fareDto.getSourceStopId())
                .orElseThrow(() -> new RuntimeException("Source stop not found"));
        BusStop destinationStop = busStopRepository.findById(fareDto.getDestinationStopId())
                .orElseThrow(() -> new RuntimeException("Destination stop not found"));

        Fare fare = new Fare();
        fare.setRoute(route);
        fare.setSourceStop(sourceStop);
        fare.setDestinationStop(destinationStop);
        fare.setFare(fareDto.getFare());

        return fareRepository.save(fare);
    }

    // Get all Fares
    public List<Fare> getAllFares() {
        return fareRepository.findAll();
    }

    // Get Fare by ID
    public Fare getFareById(Long fareId) {
        return fareRepository.findById(fareId)
                .orElseThrow(() -> new RuntimeException("Fare not found"));
    }

    // Update a Fare
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

        if (fareDto.getFare() != null) {
            existingFare.setFare(fareDto.getFare());
        }

        return fareRepository.save(existingFare);
    }

    // Delete a Fare
    public void deleteFare(Long fareId) {
        Fare fare = fareRepository.findById(fareId)
                .orElseThrow(() -> new RuntimeException("Fare not found"));
        fareRepository.delete(fare);
    }
}
